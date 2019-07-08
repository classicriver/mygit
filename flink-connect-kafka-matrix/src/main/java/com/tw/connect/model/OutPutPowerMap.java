package com.tw.connect.model;

import org.apache.flink.api.common.functions.MapFunction;

import com.google.gson.Gson;
/**
 * 
 * @author xiesc
 * @TODO	日发电量
 * @time 2019年2月20日
 * @version 1.0
 */
public class OutPutPowerMap implements MapFunction<String, Inverter> {

	private static final long serialVersionUID = 1L;

	@Override
	public Inverter map(String value) throws Exception {
		// TODO Auto-generated method stub
		final Gson gson = new Gson();
		//topic出来的数据全部转为小写
		Inverter outPutPower = gson.fromJson(value.toLowerCase(), Inverter.class);
		outPutPower.setTimestamps(System.currentTimeMillis());
		return outPutPower;
	}

}
