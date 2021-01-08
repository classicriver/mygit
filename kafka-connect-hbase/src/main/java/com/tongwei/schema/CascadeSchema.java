package com.tongwei.schema;

import com.tongwei.hbase.config.HBaseSinkConfig;

/**
 * 
 * @author xiesc
 * @TODO 组串式逆变器
 * @time 2018年11月21日
 * @version 1.0
 */
public class CascadeSchema extends TwAbstractSchema{
	
	public CascadeSchema(HBaseSinkConfig config){
		super(config);
	}
	
	@Override
	protected String getFileName() {
		// TODO Auto-generated method stub
		return "avro/Cascade.avsc";
	}

	@Override
	protected String getTopic() {
		// TODO Auto-generated method stub
		return super.config.getPropertyValue(HBaseSinkConfig.KFKAVRO_CASCADE_TOPIC, "jw_cascade");
	}
	
}
