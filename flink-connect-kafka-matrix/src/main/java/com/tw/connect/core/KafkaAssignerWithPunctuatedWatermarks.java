package com.tw.connect.core;

import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import com.tw.connect.model.Inverter;
/**
 * 
 * @author xiesc
 * @TODO 自定义水位线
 * @time 2019年1月29日
 * @version 1.0
 */
public class KafkaAssignerWithPunctuatedWatermarks implements AssignerWithPunctuatedWatermarks<Inverter>{

	private static final long serialVersionUID = 1L;
	/**
	 * 水位线最大等待时间
	 */
	private static final long maxWaitTime = 10000;
	
	@Override
	public long extractTimestamp(Inverter element,
			long previousElementTimestamp) {
		// TODO Auto-generated method stub
		return element.getTimestamps();
	}

	@Override
	public Watermark checkAndGetNextWatermark(Inverter lastElement,
			long extractedTimestamp) {
		// TODO Auto-generated method stub
		return new Watermark(extractedTimestamp-maxWaitTime);
	}


}
