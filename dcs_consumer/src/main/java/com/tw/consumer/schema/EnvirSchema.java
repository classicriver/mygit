package com.tw.consumer.schema;

/**
 * 
 * @author xiesc
 * @TODO 环境仪
 * @time 2018年11月21日
 * @version 1.0
 */
public class EnvirSchema extends TwAbstractSchema{

	@Override
	protected String getFileName() {
		// TODO Auto-generated method stub
		return "avro/Envir.avsc";
	}

	@Override
	protected String getTopic() {
		// TODO Auto-generated method stub
		return "jw_envir";
	}

}
