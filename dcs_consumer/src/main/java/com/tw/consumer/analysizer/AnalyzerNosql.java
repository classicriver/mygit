package com.tw.consumer.analysizer;

import com.tw.consumer.hbase.HbaseClientManager;
import com.tw.consumer.model.MMessage;
/**
 * 
 * @author xiesc
 * @TODO hbase
 * @time 2018年8月31日
 * @version 1.0
 */
public class AnalyzerNosql implements Analyzer{
	
	private final static HbaseClientManager manager = HbaseClientManager.getInstance();
	
	@Override
	public String analysizeAndSave(MMessage message) {
		// TODO Auto-generated method stub
		return null;
	}

}
