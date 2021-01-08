package com.tw.disruptor;

import com.lmax.disruptor.EventFactory;
import com.tw.model.Protocol;

public class ProtocolEventFactory implements EventFactory<Protocol>{
	
	public Protocol newInstance() {
		return new Protocol();
	}
	
}
