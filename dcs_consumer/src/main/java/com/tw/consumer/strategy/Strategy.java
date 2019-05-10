package com.tw.consumer.strategy;

import java.util.Map;

/**
 * 策略类
 * @author xiesc
 * @TODO
 * @time 2019年3月28日
 * @version 1.0
 */
public interface Strategy {
	/**
	 * 
	 * @param data 遥测
	 */
	public void policy(Map<String,Object> data);
	
}
