package com.tw.connect.flink;

import org.apache.flink.api.common.functions.ReduceFunction;
/**
 * 
 * @author xiesc
 * @TODO 
 * @time 2018年9月26日
 * @version 1.0
 */
public class KfkReduceFunction implements ReduceFunction<Order>{

	private static final long serialVersionUID = 1L;

	@Override
	public Order reduce(Order value1, Order value2) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
