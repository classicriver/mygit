package com.tw.ddcs.mqtt;

import org.eclipse.paho.client.mqttv3.MqttCallback;

import com.lmax.disruptor.RingBuffer;
import com.tw.ddcs.model.Message;

public class MqttService {
	
	private RingBuffer<Message> buf;
	private EmqttClient mqttClient;

	public MqttService(RingBuffer<Message> buf) {
		// TODO Auto-generated constructor stub
		this.buf = buf;
	}

	public void start(){
		initMqttClient(new PushCallback(buf,this));
	}
	
	public void restart(MqttCallback callback){
		initMqttClient(callback);
	}
	
	private void initMqttClient(MqttCallback callback){
		mqttClient = new EmqttClient();
		mqttClient.setCallback(callback);
		mqttClient.start();
	}
	
	public EmqttClient getMqttClient() {
		return mqttClient;
	}

}
