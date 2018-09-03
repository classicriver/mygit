package com.tw.consumer.mq;

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
	public static final String HOST = "tcp://bsd.ygwl.net:1883";
	public static final String TOPIC = "kktest";
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
			for (int i = 0; i < 10; i++) {
				MqttMessage msg = new MqttMessage();
				// msg.setId(123);
				msg.setQos(1);
				msg.setRetained(false);
				msg.setPayload(("{\"sn\": \"TN001\",\"time\": \"2018-08-01 15:14:17\",\"data_yc\": {\"C1_D1\": [{\"desc\": \"ipv1\",\"value\": \"8.000\"}, {\"desc\": \"ipv2\",\"value\": \"8.000\"}, {\"desc\": \"ipv3\",\"value\": \"8.000\"}, {\"desc\": \"inverter_sn\",\"value\": \"8.000\"}],\"C1_D2\": [{\"desc\": \"ipv1\",\"value\": \"8.000\"}, {\"desc\": \"ipv2\",\"value\": \"8.000\"}, {\"desc\": \"ipv3\",\"value\": \"8.000\"}, {\"desc\": \"inverter_sn\",\"value\": \"8.000\"}]},\"data_yx\": {\"C1_D1\": [{\"desc\": \"yx1\",\"value\": \"0\"}, {\"desc\": \"yx2\",\"value\": \"0\"}, {\"desc\": \"yx3\",\"value\": \"0\"}, {\"desc\": \"yx4\",\"value\": \"0\"}],\"C1_D2\": [{\"desc\": \"yx1\",\"value\": \"0\"}, {\"desc\": \"yx2\",\"value\": \"0\"}, {\"desc\": \"yx3\",\"value\": \"0\"}, {\"desc\": \"yx4\",\"value\": \"0\"}]}}")
						.getBytes());
				MqttDeliveryToken token = topic.publish(msg);
				token.waitForCompletion();
				//Thread.sleep(10);
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
				Thread.sleep(10);
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
