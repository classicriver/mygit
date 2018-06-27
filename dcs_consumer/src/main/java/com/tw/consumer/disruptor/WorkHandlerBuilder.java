package com.tw.consumer.disruptor;

import com.tw.consumer.hbase.HbaseClient;
/**
 * 
 * @author xiesc
 * @TODO  
 * @time 2018年5月31日
 * @version 1.0
 */
public class WorkHandlerBuilder {

	private HbaseClient[] clients;
	
	public MMessageWorkHandler[] build(int count) {
		clients = new HbaseClient[count];
		MMessageWorkHandler[] handlers = new MMessageWorkHandler[count];
		//the htable instance is not thread safe.
		for (int i = 0; i < count; i++) {
			clients[i] = new HbaseClient();
			handlers[i] = new MMessageWorkHandler(clients[i]);
		}
		return handlers;
	}
	
	public void shutdownHTables(){
		if(clients.length > 0){
			for(HbaseClient c : clients){
				c.shutdown();
			}
		}
	}
}
