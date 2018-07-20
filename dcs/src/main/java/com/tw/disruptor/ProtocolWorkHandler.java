package com.tw.disruptor;

import com.lmax.disruptor.WorkHandler;
import com.tw.analysizer.Analysizer;
import com.tw.model.Protocol;
import com.tw.mq.producer.MQProducer;

public class ProtocolWorkHandler implements WorkHandler<Protocol>{

	/**
	 * 协议分析
	 */
	private final Analysizer analysize;
	/**
	 * MQ客户端
	 */
	private final MQProducer producer;
	
	public ProtocolWorkHandler(Analysizer analysize,MQProducer client){
		this.analysize = analysize;
		this.producer = client;
	}
	
	@Override
	public void onEvent(Protocol event) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(Thread.currentThread().getName()+" ProtocolEventHandler: "+event.getMessage());
		producer.send(analysize.analysize(event));
	}
}
