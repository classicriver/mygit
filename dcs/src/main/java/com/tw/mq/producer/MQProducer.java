package com.tw.mq.producer;

import java.util.List;


public interface MQProducer {
	
	public void send(String msg);
	
	public void send(List<?> list);
	
	public boolean mqServerIsUp();
	
	public void setIsDown(Boolean down);
	
	public void close();
	
}
