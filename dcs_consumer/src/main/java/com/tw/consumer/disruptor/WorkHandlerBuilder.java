package com.tw.consumer.disruptor;

import com.tw.consumer.analysizer.AnalyzerProxy;
import com.tw.consumer.config.Config;
/**
 * 
 * @author xiesc
 * @TODO  disruptor的handler建造器
 * @time 2018年5月31日
 * @version 1.0
 */
public class WorkHandlerBuilder {

	private final AnalyzerProxy analyzer = new AnalyzerProxy();
	/**
	 * 有几个handler就有几条线程，一个线程一个handler
	 * @return
	 */
	public MMessageWorkHandler[] build() {
		int count = Config.getInstance().getMaxHandlers();
		MMessageWorkHandler[] handlers = new MMessageWorkHandler[count];
		for (int i = 0; i < count; i++) {
			handlers[i] = new MMessageWorkHandler(analyzer);
		}
		return handlers;
	}

}
