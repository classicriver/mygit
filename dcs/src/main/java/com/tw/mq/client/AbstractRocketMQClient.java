package com.tw.mq.client;

import java.util.List;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import com.tw.config.Config;

public abstract class AbstractRocketMQClient implements MQClient {

	/**
	 * MQ生产者
	 */
	protected final static DefaultMQProducer producer;

	static {
		producer = new DefaultMQProducer(Config.getProducerName());
		producer.setNamesrvAddr(Config.getNameServer());
		// producer.setInstanceName(UUID.randomUUID().toString());
		producer.setVipChannelEnabled(false);
		producer.setRetryTimesWhenSendFailed(3);
		try {
			producer.start();
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void close() {
		// TODO Auto-generated method stub
		producer.shutdown();
	}

	@Override
	public void send(String msg) {
		// TODO Auto-generated method stub
		send0(msg);
		// LogFactory.getLog(AbstractMQClient.class).info(msg);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void send(List<?> list) {
		// TODO Auto-generated method stub
		bathSend((List<Message>) list);
	}

	public abstract void send0(String msg);

	public abstract void bathSend(List<Message> msg);

}
