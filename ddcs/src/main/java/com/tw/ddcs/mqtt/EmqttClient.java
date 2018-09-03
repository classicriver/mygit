package com.tw.ddcs.mqtt;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.tw.ddcs.config.DdcsConfig;
/**
 * 
 * @author xiesc
 * @TODO mqtt客户端类
 * @time 2018年8月2日
 * @version 1.0
 */
public class EmqttClient{

	private static final String HOST = DdcsConfig.getInstance().getMqttHost();
	private static final String TOPIC = DdcsConfig.getInstance().getMqttTopic();
	private static final String USERNAME = DdcsConfig.getInstance().getMqttUserName();
	private static final String PWD = DdcsConfig.getInstance().getMqttPwd();
	private static final int QOS  = DdcsConfig.getInstance().getMqttQos();
	
	private MqttCallback callback;
	private MqttClient client;
	private MqttConnectOptions options;

	/**
	 * 开启订阅
	 */
	public void start() {
		try {
			//MemoryPersistence设置clientid的保存形式，默认为以内存保存
			client = new MqttClient(HOST, DdcsConfig.getInstance().getMqttClientid(), new MemoryPersistence());
			options = new MqttConnectOptions();
			// 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
			options.setCleanSession(true);
			options.setUserName(USERNAME);
			options.setPassword(PWD.toCharArray());
			// 设置超时时间 单位为秒
			options.setConnectionTimeout(10);
			// 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
			options.setKeepAliveInterval(20);
			// 设置回调
			client.setCallback(getCallback());
			options.setWill(client.getTopic(TOPIC), "close".getBytes(), 2, true);
			client.connect(options);
			// 订阅消息
            client.subscribe(TOPIC, QOS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		// TODO Auto-generated method stub
		try {
			client.disconnect();
			client.close();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MqttCallback getCallback() {
		return callback;
	}
	
	public void setCallback(MqttCallback callback) {
		this.callback = callback;
	}

}
