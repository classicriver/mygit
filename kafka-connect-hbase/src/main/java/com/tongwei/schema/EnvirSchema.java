package com.tongwei.schema;

import com.tongwei.hbase.config.HBaseSinkConfig;

/**
 * 
 * @author xiesc
 * @TODO 环境仪
 * @time 2018年11月21日
 * @version 1.0
 */
public class EnvirSchema extends TwAbstractSchema{

	public EnvirSchema(HBaseSinkConfig config){
		super(config);
	}
	
	@Override
	protected String getFileName() {
		// TODO Auto-generated method stub
		return "avro/Envir.avsc";
	}

	@Override
	protected String getTopic() {
		// TODO Auto-generated method stub
		return super.config.getPropertyValue(HBaseSinkConfig.KFKAVRO_COMBINER_TOPIC, "jw_envir");
	}

}
