package com.tongwei.schema;

import com.tongwei.hbase.config.HBaseSinkConfig;

/**
 * 
 * @author xiesc
 * @TODO  集中式逆变器
 * @time 2018年11月21日
 * @version 1.0
 */
public class CentralizedSchema extends TwAbstractSchema{

	public CentralizedSchema(HBaseSinkConfig config){
		super(config);
	}
	
	@Override
	protected String getFileName() {
		// TODO Auto-generated method stub
		return "avro/Centralized.avsc";
	}

	@Override
	protected String getTopic() {
		// TODO Auto-generated method stub
		return super.config.getPropertyValue(HBaseSinkConfig.KFKAVRO_CENTRALIZED_TOPIC, "jw_centralized");
	}

}
