package com.tw.consumer.schema;

/**
 * 
 * @author xiesc
 * @TODO 汇流箱
 * @time 2018年11月21日
 * @version 1.0
 */
public class CombinerdcSchema extends TwAbstractSchema{

	@Override
	protected String getFileName() {
		// TODO Auto-generated method stub
		return "avro/Combinerdc.avsc";
	}

	@Override
	protected String getTopic() {
		// TODO Auto-generated method stub
		return "jw_combinerdc";
	}

}
