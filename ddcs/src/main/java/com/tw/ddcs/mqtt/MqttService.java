package com.tw.ddcs.mqtt;

import org.eclipse.paho.client.mqttv3.MqttCallback;

import com.lmax.disruptor.RingBuffer;
import com.tw.ddcs.model.OriginMessage;

public class MqttService {
	
	private RingBuffer<OriginMessage> buf;
	private EmqttClient mqttClient;

	public MqttService(RingBuffer<OriginMessage> buf) {
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
