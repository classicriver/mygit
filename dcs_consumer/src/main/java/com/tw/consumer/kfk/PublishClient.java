package com.tw.consumer.kfk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PublishClient implements Runnable {
	public static final String HOST = "tcp://47.101.132.234:1883";
	public static final String TOPIC = "jw.kktest";
	private static final String userName = "ygwl";
	private static final String passWord = "tw2630";
	
	private CountDownLatch latch ;

	// private ScheduledExecutorService scheduler;
	PublishClient(CountDownLatch latch){
		this.latch = latch;
	}
	private void start() {
		MqttClient client;
		MqttConnectOptions options;
		try {
			// host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
			client = new MqttClient(HOST, MqttClient.generateClientId(),
					new MemoryPersistence());
			// MQTT的连接设置
			options = new MqttConnectOptions();
			options.setServerURIs(new String[]{"tcp://47.101.133.122:1883","tcp://47.101.132.234:1883"});
			// 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
			options.setCleanSession(true);
			// 设置连接的用户名
			options.setUserName(userName);
			// 设置连接的密码
			options.setPassword(passWord.toCharArray());
			// 设置超时时间 单位为秒
			options.setConnectionTimeout(1000);
			// 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
			options.setKeepAliveInterval(5);
			// 设置回调
			//client.setCallback(new PushCallback());
			MqttTopic topic = client.getTopic(TOPIC);
			// setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
			options.setWill(topic, "close".getBytes(), 2, true);
			client.connect(options);
			// 订阅消息
			// int[] Qos = {1};
			// String[] topic1 = {TOPIC};
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = f.format(new Date());
			Random r = new Random();
			for (int i = 0; i < 5; i++) {
				String sn = String.valueOf(r.nextInt(10));
				MqttMessage msg = new MqttMessage();
				String payload = "{\"esn\":\"nbq_"+sn+"\",\"time\":\""+date+"\",\"data_yc\":{\"output_power_day\":\"309.00\",\"active_power\":\"1.00\"},\"data_yx\":{\"yx_a\":\""+sn+"\",\"yx_b\":\"888\"}}";
				// msg.setId(123);
				msg.setQos(1);
				msg.setRetained(false);
				msg.setPayload(payload.getBytes());
				MqttDeliveryToken token = topic.publish(msg);
				token.waitForCompletion();
				Thread.sleep(10);
			}
			client.disconnect();
			// client.publish(TOPIC, msg);
			// client.subscribe(topic1, Qos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws MqttException {
		int count = 10;
		ExecutorService pool = Executors.newCachedThreadPool();
		CountDownLatch latch = new CountDownLatch(count);
		for(int i = 0 ; i < count;i++){
			pool.submit(new PublishClient(latch));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		start();
		latch.countDown();
	}

}
