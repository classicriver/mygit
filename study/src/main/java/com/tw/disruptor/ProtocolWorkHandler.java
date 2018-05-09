package com.tw.disruptor;

import com.lmax.disruptor.WorkHandler;
import com.tw.model.Protocol;

public class ProtocolWorkHandler implements WorkHandler<Protocol>{

	@Override
	public void onEvent(Protocol event) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("ProtocolWorkHandler: "+event.getValue());
	}

}
