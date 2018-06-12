package com.tw.disruptor;

import com.tw.analysizer.DefaultAnalysizerImpl;
import com.tw.mq.producer.MQProducer;

/**
 * 
 * @author xiesc
 * @TODO  
 * @time 2018年5月31日
 * @version 1.0
 */
public class WorkHandlerBuilder {

	public static ProtocolWorkHandler[] build(int count, MQProducer cilent) {
		ProtocolWorkHandler[] handlers = new ProtocolWorkHandler[count];
		for (int i = 0; i < count; i++) {
			handlers[i] = new ProtocolWorkHandler(new DefaultAnalysizerImpl(),
					cilent);
		}
		return handlers;
	}
}
