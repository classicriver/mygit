package com.tw.mq.client;

import org.apache.commons.logging.LogFactory;

public abstract class AbstractMQClient implements MQClient{

	@Override
	public void send(String msg) {
		// TODO Auto-generated method stub
		send0(msg);
		//LogFactory.getLog(AbstractMQClient.class).info(msg);
	}
	
	public abstract void send0(String msg);
	
}
