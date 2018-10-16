package com.tw.connect.flink.deprecated;

import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
/**
 * 
 * @author xiesc
 * @TODO 自定义window关闭函数
 * @time 2018年9月26日
 * @version 1.0
 */
public class KfkWindowFunction implements WindowFunction<Order, Order, Tuple, TimeWindow>{

	private static final long serialVersionUID = 1L;

	/**
	 * window 关闭时会调用apply
	 */
	@Override
	public void apply(Tuple key, TimeWindow window, Iterable<Order> input,
			Collector<Order> out) throws Exception {
		// TODO Auto-generated method stub
		out.collect(input.iterator().next());
	}

}
