package com.tw.consumer.disruptor;

import com.lmax.disruptor.WorkHandler;
import com.tw.consumer.analysizer.AnalyzerProxy;
import com.tw.consumer.model.OriginMessage;

public class MMessageWorkHandler implements WorkHandler<OriginMessage>{
	
	private final AnalyzerProxy analyzer;
	
	public MMessageWorkHandler(AnalyzerProxy analyzer) {
		// TODO Auto-generated constructor stub
		this.analyzer = analyzer;
	}

	@Override
	public void onEvent(OriginMessage message) throws Exception {
		// TODO Auto-generated method stub
		analyzer.analysize(message);
		//System.out.println(Thread.currentThread().getName()+" ProtocolEventHandler: "+event.getMessage());
	}
}
