package com.tw.disruptor;

import com.lmax.disruptor.EventHandler;
import com.tw.model.Protocol;

public class ProtocolEventHandler implements EventHandler<Protocol>{

	@Override
	public void onEvent(Protocol event, long sequence, boolean endOfBatch)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ProtocolEventHandler: "+event.getValue());
	}
}
