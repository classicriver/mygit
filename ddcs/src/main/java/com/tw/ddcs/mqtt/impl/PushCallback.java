package com.tw.ddcs.mqtt.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.Gson;
import com.lmax.disruptor.RingBuffer;
import com.tw.ddcs.log.LogFactory;
import com.tw.ddcs.model.Message;
/**
 * 
 * @author xiesc
 * @TODO 客户端接收到订阅后的回调函数
 * @time 2018年8月2日
 * @version 1.0
 */
public class PushCallback implements MqttCallback {

	private RingBuffer<Message> buf;
	private final Gson gson = new Gson();
	
	PushCallback(RingBuffer<Message> buf){
		this.buf = buf;
	}
	
	@Override
	public void connectionLost(Throwable e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void messageArrived(String topic, MqttMessage msg) throws Exception {
		String body = new String(msg.getPayload());
		// TODO Auto-generated method stub
		LogFactory.getLogger().info(body);
		if(!"close".equals(body)){
			Message mg = gson.fromJson(body, Message.class);
			long sequence = buf.next();
			try {
				 // Get the entry in the Disruptor for the sequence
				Message event = buf.get(sequence);
				// Fill with data
				BeanUtils.copyProperties(event,mg);
				//help gc
				mg = null;
				body = null;
			} finally {
				buf.publish(sequence);
			}
		}
	}
}
