package com.tw.consumer.disruptor;

import com.lmax.disruptor.WorkHandler;
import com.tw.consumer.hbase.HbaseClient;
import com.tw.consumer.model.MMessage;

public class MMessageWorkHandler implements WorkHandler<MMessage>{
	
	private final HbaseClient client;
	
	public MMessageWorkHandler(HbaseClient client) {
		// TODO Auto-generated constructor stub
		this.client = client;
	}

	@Override
	public void onEvent(MMessage event) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(Thread.currentThread().getName()+" ProtocolEventHandler: "+event.getMessage());
	}
}
