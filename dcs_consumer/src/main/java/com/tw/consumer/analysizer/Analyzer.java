package com.tw.consumer.analysizer;

import com.tw.consumer.model.MMessage;

/**
 * 
 * @author xiesc
 * @TODO 协议解析器接口
 * @time 2018年5月14日
 * @version 1.0
 */
public interface Analyzer {

	public String analysizeAndSave(MMessage message);
	
}
