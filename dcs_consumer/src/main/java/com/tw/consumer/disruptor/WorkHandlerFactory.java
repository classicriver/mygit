package com.tw.consumer.disruptor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import com.tw.consumer.analysizer.AnalyzerProxy;
import com.tw.consumer.config.Config;
import com.tw.consumer.model.GenericDeviceModel;
/**
 * 
 * @author xiesc
 * @TODO  disruptor的handler建造器
 * @time 2018年5月31日
 * @version 1.0
 */
public class WorkHandlerFactory {

	private final AnalyzerProxy analyzer;
	/**
	 * 有几个handler就有几条线程，一个线程一个handler
	 * @return
	 */
	public WorkHandlerFactory(final Semaphore semaphore,final LinkedBlockingQueue<GenericDeviceModel> queue){
		analyzer = new AnalyzerProxy(semaphore,queue);
	}
	
	public MMessageWorkHandler[] getHandlers() {
		int count = Config.getInstance().getMaxHandlers();
		MMessageWorkHandler[] handlers = new MMessageWorkHandler[count];
		for (int i = 0; i < count; i++) {
			handlers[i] = new MMessageWorkHandler(analyzer);
		}
		return handlers;
	}

}
