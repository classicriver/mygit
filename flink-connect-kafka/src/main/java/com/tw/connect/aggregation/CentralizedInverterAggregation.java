package com.tw.connect.aggregation;

import com.tw.connect.core.AbstractAggregation;
import com.tw.connect.core.AbstractSchema;

/**
 * 
 * @author xiesc
 * @TODO 集中式逆变器
 * @time 2018年10月15日
 * @version 1.0
 */
public class CentralizedInverterAggregation extends AbstractAggregation{

	@Override
	protected AbstractSchema getSchema() {
		// TODO Auto-generated method stub
		return new CentralizedInverterSchema();
	}
	
	@Override
	protected String getTopic() {
		// TODO Auto-generated method stub
		return "MQTT_INVERTER_STREAM";
	}

	@Override
	protected String getFlinkTableSourceName() {
		// TODO Auto-generated method stub
		return CentralizedInverterAggregation.class.getName();
	}

	@Override
	protected String getMysqlTableName() {
		// TODO Auto-generated method stub
		return "sc_sta_centralized_inverter_hour";
	}

}
