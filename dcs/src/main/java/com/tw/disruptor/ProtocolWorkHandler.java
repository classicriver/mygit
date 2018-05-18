package com.tw.disruptor;

import com.lmax.disruptor.WorkHandler;
import com.tw.analysizer.Analysizer;
import com.tw.model.Protocol;

public class ProtocolWorkHandler implements WorkHandler<Protocol>{

	private final Analysizer analysize;
	
	@Override
	public void onEvent(Protocol event) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(Thread.currentThread().getName()+" ProtocolEventHandler: "+event.getMessage());
		analysize.analysize(event);
	}
	
	public ProtocolWorkHandler(Analysizer analysize){
		this.analysize = analysize;
	}

}
