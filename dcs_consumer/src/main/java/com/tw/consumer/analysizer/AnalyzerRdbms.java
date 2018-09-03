package com.tw.consumer.analysizer;

import com.tw.consumer.dao.core.DaoFactory;
import com.tw.consumer.model.MMessage;
/**
 * 
 * @author xiesc
 * @TODO mysql
 * @time 2018年8月31日
 * @version 1.0
 */
public class AnalyzerRdbms implements Analyzer{
	
	private final static DaoFactory dao = new DaoFactory();
	
	@Override
	public String analysizeAndSave(MMessage message) {
		// TODO Auto-generated method stub
		return null;
	}

}
