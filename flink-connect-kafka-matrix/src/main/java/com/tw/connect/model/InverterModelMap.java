package com.tw.connect.model;

import org.apache.flink.api.common.functions.MapFunction;

import com.google.gson.Gson;

/**
 * 
 * @author xiesc
 * @TODO avro 转对象
 * @time 2019年1月18日
 * @version 1.0
 */
public class InverterModelMap implements MapFunction<String, Inverter> {

	private static final long serialVersionUID = 1L;

	@Override
	public Inverter map(String record) throws Exception {
		// TODO Auto-generated method stub
		final Gson gson = new Gson();
		// topic出来的数据全部转为小写
		Inverter matrix = gson.fromJson(record.toLowerCase(), Inverter.class);
		long timestamps = matrix.getTimestamps();
		if (String.valueOf(timestamps).length() == 10) {
			matrix.setTimestamps(timestamps * 1000);
		}
		return matrix;
	}
}
