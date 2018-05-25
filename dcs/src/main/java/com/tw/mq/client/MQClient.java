package com.tw.mq.client;

import java.util.List;


public interface MQClient {
	
	public void send(String msg);
	
	public void send(List<?> list);
	
}
