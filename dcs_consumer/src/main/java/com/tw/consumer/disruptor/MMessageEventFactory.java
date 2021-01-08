package com.tw.consumer.disruptor;

import com.lmax.disruptor.EventFactory;
import com.tw.consumer.model.OriginMessage;
/**
 * 
 * @author xiesc
 * @TODO disruptor对象工厂类
 * @time 2018年5月14日
 * @version 1.0
 */
public class MMessageEventFactory implements EventFactory<OriginMessage>{
	
	public OriginMessage newInstance() {
		return new OriginMessage();
	}
	
}
