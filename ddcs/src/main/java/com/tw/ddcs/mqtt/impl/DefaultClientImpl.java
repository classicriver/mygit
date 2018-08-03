package com.tw.ddcs.mqtt.impl;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.lmax.disruptor.RingBuffer;
import com.tw.ddcs.config.DdcsConfig;
import com.tw.ddcs.model.Message;
/**
 * 
 * @author xiesc
 * @TODO mqtt客户端类
 * @time 2018年8月2日
 * @version 1.0
 */
public class DefaultClientImpl implements com.tw.ddcs.mqtt.MqttClient {

	private RingBuffer<Message> buf;

	private static final String HOST = DdcsConfig.getInstance().getMqttHost();
	private static final String TOPIC = DdcsConfig.getInstance().getMqttTopic();
	private static final String CLIENTID = DdcsConfig.getInstance().getMqttClientid();
	private static final String USERNAME = DdcsConfig.getInstance().getMqttUserName();
	private static final String PWD = DdcsConfig.getInstance().getMqttPwd();

	private MqttClient client;
	private MqttConnectOptions options;
	
	public DefaultClientImpl(RingBuffer<Message> buf) {
		this.buf = buf;
	}
	/**
	 * 开启订阅
	 */
	@Override
	public void start() {
		try {
			//MemoryPersistence设置clientid的保存形式，默认为以内存保存
			client = new MqttClient(HOST, CLIENTID, new MemoryPersistence());
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
			client.setCallback(new PushCallback(buf));
			MqttTopic topic = client.getTopic(TOPIC);
			options.setWill(topic, "close".getBytes(), 2, true);
			client.connect(options);
			// 订阅消息
			int[] Qos  = {0};
            String[] topic1 = {TOPIC};
            client.subscribe(topic1, Qos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		try {
			client.disconnectForcibly();
			client.close();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
