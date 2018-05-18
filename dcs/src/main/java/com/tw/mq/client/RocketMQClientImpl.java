package com.tw.mq.client;

import java.io.UnsupportedEncodingException;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import com.tw.common.utils.Utils;
import com.tw.config.Config;

public class RocketMQClientImpl extends AbstractMQClient{
	
	private final static DefaultMQProducer producer;
	
	static{
		producer = new DefaultMQProducer(Config.getProducerName());
        producer.setNamesrvAddr(Config.getNameServer());
        //producer.setInstanceName(UUID.randomUUID().toString());
        producer.setVipChannelEnabled(false);
        try {
			producer.start();
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void send0(String msg) {
		// TODO Auto-generated method stub
		SendResult send;
		Message message;
		try {
			message = new Message("PushTopic",   
	                "push",   
	                Utils.getStringUniqueId(),   
	                msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
			send = producer.send(message);
		} catch (MQClientException | RemotingException | MQBrokerException
				| InterruptedException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//help gc
		message = null;
	}

	public static void close() {
		// TODO Auto-generated method stub
		producer.shutdown();
	}
	

}
