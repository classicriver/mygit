package com.tongwei.calculate;

import java.util.Map;

import com.tongwei.strategy.Strategy;

/**
 * 计算类
 * @author xiesc
 * @TODO
 * @time 2019年3月28日
 * @version 1.0
 */
public class Calculation {
	/**
	 * 
	 * @param data 遥测
	 * @param strategy 计算策略
	 */
	public void calculate(Map<String, Object> data,Strategy strategy) {
		strategy.policy(data);
	}

}
