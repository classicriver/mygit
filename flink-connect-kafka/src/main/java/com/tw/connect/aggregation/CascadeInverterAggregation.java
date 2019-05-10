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
public class CascadeInverterAggregation extends AbstractAggregation{
	
	@Override
	protected AbstractSchema getSchema() {
		// TODO Auto-generated method stub
		return new CascadeInverterSchema();
	}
	
	@Override
	protected String getTopic() {
		// TODO Auto-generated method stub
		//组串式逆变器
		return "MQTT_CASCADE_INVERTER_STREAM";
		//集中式逆变器
		//return "MQTT_CENTRALIZED_INVERTER_STREAM";
	}

	@Override
	protected String getMysqlTableName() {
		// TODO Auto-generated method stub
		//组串式逆变器
		return "sc_sta_cascade_inverter_hour";
		//集中式逆变器
		//return "sc_sta_centralized_inverter_hour";
	}
	
	public static void main(String[] args) {
		new CascadeInverterAggregation().start();
	}
	
}
