package com.tw.disruptor;

import com.lmax.disruptor.WorkHandler;
import com.tw.analysizer.Analysizer;
import com.tw.model.Protocol;
import com.tw.mq.client.MQClient;

public class ProtocolWorkHandler implements WorkHandler<Protocol>{

	/**
	 * 协议分析
	 */
	private final Analysizer analysize;
	/**
	 * MQ客户端
	 */
	private final MQClient client;
	
	@Override
	public void onEvent(Protocol event) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(Thread.currentThread().getName()+" ProtocolEventHandler: "+event.getMessage());
		client.send(analysize.analysize(event));
	}
	
	public ProtocolWorkHandler(Analysizer analysize,MQClient client){
		this.analysize = analysize;
		this.client = client;
	}

}
