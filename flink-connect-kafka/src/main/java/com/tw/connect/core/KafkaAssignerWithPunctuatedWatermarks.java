package com.tw.connect.core;

import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import com.tw.connect.model.CascadeModel;

public class KafkaAssignerWithPunctuatedWatermarks implements AssignerWithPunctuatedWatermarks<CascadeModel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 水位线最大等待时间
	 */
	private static final long maxWaitTime = 5000;
	
	@Override
	public long extractTimestamp(CascadeModel element,
			long previousElementTimestamp) {
		// TODO Auto-generated method stub
		return element.getTimestamps();
	}

	@Override
	public Watermark checkAndGetNextWatermark(CascadeModel lastElement,
			long extractedTimestamp) {
		// TODO Auto-generated method stub
		return new Watermark(extractedTimestamp-maxWaitTime);
	}


}
