package com.evokly.kafka.connect.mqtt;

import java.util.concurrent.BlockingQueue;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class EmqListener implements IMqttMessageListener {
	
	private final BlockingQueue<MqttMessageProcessor> mQueue;
	private final MqttSourceConnectorConfig mConfig;
	
	public EmqListener(BlockingQueue<MqttMessageProcessor> mQueue,MqttSourceConnectorConfig mConfig) {
		this.mQueue = mQueue;
		this.mConfig = mConfig;
	}

	@Override
	public void messageArrived(String mqttTopic, MqttMessage message) throws Exception {
		this.mQueue.add(mConfig.getConfiguredInstance(
				MqttSourceConstant.MESSAGE_PROCESSOR,
				MqttMessageProcessor.class).process(mqttTopic,message));
	}
}
