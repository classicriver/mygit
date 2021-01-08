package com.tw.disruptor;

import com.lmax.disruptor.EventFactory;
import com.tw.model.Protocol;
/**
 * 
 * @author xiesc
 * @TODO disruptor对象工厂类
 * @time 2018年5月14日
 * @version 1.0
 */
public class ProtocolEventFactory implements EventFactory<Protocol>{
	
	public Protocol newInstance() {
		return new Protocol();
	}
	
}
