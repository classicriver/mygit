package com.tw.consumer.test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
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

import com.google.gson.Gson;
import com.tw.consumer.model.CascadeModel;
import com.tw.consumer.model.CombinerModel;
import com.tw.consumer.model.CopyOfYhMessage;
import com.tw.consumer.model.YhMessage;

public class PublishClient implements Runnable {
	public static final String HOST = "tcp://47.101.132.234:1883";
	public static final String TOPIC = "pubtest";
	private static final String userName = "ygwl";
	private static final String passWord = "tw2630";
	private static final Gson gson = new Gson();
	private CountDownLatch latch;
	private final double min = 0.1;
	private final double max = 1000; // 总和
	private final int scl =  6; // 小数最大位数
	private final int pow = (int) Math.pow(10, scl); // 用于提取指定小数位

	PublishClient(CountDownLatch latch) {
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
			options.setServerURIs(new String[] { "tcp://47.101.133.122:1883",
					"tcp://47.101.132.234:1883" });
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
			MqttTopic topic = client.getTopic(TOPIC);
			client.connect(options);
			for (;;) {
				MqttMessage msg = new MqttMessage();
				String payload = gson.toJson(getRandomData());
				//System.out.println(payload);
				msg.setQos(1);
				msg.setRetained(false);
				msg.setPayload(payload.getBytes());
				MqttDeliveryToken token = topic.publish(msg);
				token.waitForCompletion();
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws MqttException {
		int count = 50;
		long startTime = System.currentTimeMillis();
		ExecutorService pool = Executors.newCachedThreadPool();
		CountDownLatch latch = new CountDownLatch(count);
		for (int i = 0; i < count; i++) {
			pool.submit(new PublishClient(latch));
			try {
				Thread.sleep(5);
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
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
		System.exit(0);
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		start();
		latch.countDown();
	}

	public Object getRandomData() throws IllegalArgumentException, IllegalAccessException, Exception {
		Random r = new Random();
		int nextInt = r.nextInt(2);
		String esn = getRandomEsn(nextInt);
		//System.out.println(esn);
		CopyOfYhMessage msg = new CopyOfYhMessage();
		long time = System.currentTimeMillis();
		msg.setEsn(esn);
		msg.setTimestamps(time);
		switch (nextInt) {
		case 0:
			msg.setYc(initData(new CascadeModel(),esn,time));
			break;
		case 1:
			msg.setYc(initData(new CombinerModel(),esn,time));
			break;
		}
		return msg;
	}

	public Object initData(Object obj,String esn,long time) throws IllegalArgumentException,
			IllegalAccessException, Exception {
		//Class<? extends Object> class1 = obj.getClass();
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			Class<?> clazz = field.getType();
			if (clazz.getName().equals(BigDecimal.class.getName())) {
				field.setAccessible(true);
				field.set(obj, new BigDecimal(String.valueOf(generatingDoubleBounded())));
			}
		}
		//class1.getDeclaredMethod("setEsn",String.class).invoke(obj, esn);
		//class1.getDeclaredMethod("setTimestamps",long.class).invoke(obj, time);
		return obj;
	}

	public double generatingDoubleBounded() throws Exception {
		return Math.floor((Math.random() * (max - min) + min) * pow) / pow;
	}
	
	public String getRandomEsn(int type){
		Random r = new Random();
		//组串式逆变器
		if(0 == type ){
			return "TH-N1-0"+r.nextInt(10)+"000"+r.nextInt(10)+r.nextInt(10);
		}else{
			//直流汇流箱
			return "TH-H1-0"+r.nextInt(10)+"000"+r.nextInt(10)+r.nextInt(10);
		}
	}

}
