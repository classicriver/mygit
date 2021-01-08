package com.tw.mq.producer;

import java.util.List;

import com.tw.common.utils.MQStatus;


public interface MQProducer {
	
	public void send(String msg);
	
	public void send(List<?> list);
	
	public MQStatus getMQStatus();
	
	public void setMQStatus(MQStatus status);
	
	public void close();
	
}
