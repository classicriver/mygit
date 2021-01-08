package com.tongwei.schema;

import com.tongwei.hbase.config.HBaseSinkConfig;

/**
 * 
 * @author xiesc
 * @TODO 汇流箱
 * @time 2018年11月21日
 * @version 1.0
 */
public class CombinerdcSchema extends TwAbstractSchema{

	public CombinerdcSchema(HBaseSinkConfig config){
		super(config);
	}
	
	@Override
	protected String getFileName() {
		// TODO Auto-generated method stub
		return "avro/Combinerdc.avsc";
	}

	@Override
	protected String getTopic() {
		// TODO Auto-generated method stub
		return super.config.getPropertyValue(HBaseSinkConfig.KFKAVRO_COMBINER_TOPIC, "jw_combinerdc");
	}

}
