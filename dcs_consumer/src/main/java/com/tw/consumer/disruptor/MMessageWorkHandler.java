package com.tw.consumer.disruptor;

import com.lmax.disruptor.WorkHandler;
import com.tw.consumer.analysizer.AnalyzerProxy;
import com.tw.consumer.model.MMessage;

public class MMessageWorkHandler implements WorkHandler<MMessage>{
	
	private final AnalyzerProxy analyzer;
	
	public MMessageWorkHandler(AnalyzerProxy analyzer) {
		// TODO Auto-generated constructor stub
		this.analyzer = analyzer;
	}

	@Override
	public void onEvent(MMessage event) throws Exception {
		// TODO Auto-generated method stub
		//analyzer.analysizeAndSave(event);
		System.out.println(Thread.currentThread().getName()+" ProtocolEventHandler: "+event.getMessage());
	}
}
