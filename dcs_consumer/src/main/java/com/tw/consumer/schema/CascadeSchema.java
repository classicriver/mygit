package com.tw.consumer.schema;

/**
 * 
 * @author xiesc
 * @TODO 组串式逆变器
 * @time 2018年11月21日
 * @version 1.0
 */
public class CascadeSchema extends TwAbstractSchema{

	@Override
	protected String getFileName() {
		// TODO Auto-generated method stub
		return "avro/Cascade.avsc";
	}

	@Override
	protected String getTopic() {
		// TODO Auto-generated method stub
		return "jw_cascade";
	}
	
}
