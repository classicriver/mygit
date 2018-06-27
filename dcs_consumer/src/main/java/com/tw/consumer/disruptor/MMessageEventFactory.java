package com.tw.consumer.disruptor;

import com.lmax.disruptor.EventFactory;
import com.tw.consumer.model.MMessage;
/**
 * 
 * @author xiesc
 * @TODO disruptor对象工厂类
 * @time 2018年5月14日
 * @version 1.0
 */
public class MMessageEventFactory implements EventFactory<MMessage>{
	
	public MMessage newInstance() {
		return new MMessage();
	}
	
}
