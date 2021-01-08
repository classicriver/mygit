package com.tw.connect.model;

import java.math.BigDecimal;
/**
 * 
 * @author xiesc
 * @TODO 子阵
 * @time 2019年1月16日
 * @version 1.0
 */
public class Inverter {
	/**
	 * 重写hashcode，flink需要hashcode分组
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return esn.hashCode();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "esn:" +esn+" timestamps:"+timestamps+" matrix_id:"+matrix_id+" input_power:"+input_power
				+" active_power:"+active_power+" reactive_power:"+reactive_power+" output_power:"+output_power
				+" total_output_power:"+total_output_power;
	}
	
	private String esn;
	
	private long timestamps;
	/**
	 * 子阵ID
	 */
	private String matrix_id;
	/**
	 * 电站id
	 */
	private String station_id;
	/**
	 * 区域id
	 */
	private String region_id;
	/**
	 * 输入总功率
	 */
	private BigDecimal input_power;
	/**
	 * 输出有功功率
	 */
	private BigDecimal active_power;
	/**
	 * 输出无功功率
	 */
	private BigDecimal reactive_power;
	/**
	 * 日发电量
	 */
	private BigDecimal output_power;
	/**
	 * 累计发电量
	 */
	private BigDecimal total_output_power;
	/**
	 * 装机容量
	 */
	private BigDecimal installed_capacity;
	
	public String getEsn() {
		return esn;
	}
	public void setEsn(String esn) {
		this.esn = esn;
	}
	public long getTimestamps() {
		return timestamps;
	}
	public void setTimestamps(long timestamps) {
		this.timestamps = timestamps;
	}
	public BigDecimal getInput_power() {
		return input_power;
	}
	public void setInput_power(BigDecimal input_power) {
		this.input_power = input_power;
	}
	public BigDecimal getReactive_power() {
		return reactive_power;
	}
	public void setReactive_power(BigDecimal reactive_power) {
		this.reactive_power = reactive_power;
	}
	public BigDecimal getActive_power() {
		return active_power;
	}
	public void setActive_power(BigDecimal active_power) {
		this.active_power = active_power;
	}
	
	public BigDecimal getOutput_power() {
		return output_power;
	}

	public void setOutput_power(BigDecimal output_power) {
		this.output_power = output_power;
	}

	public BigDecimal getTotal_output_power() {
		return total_output_power;
	}
	public void setTotal_output_power(BigDecimal total_output_power) {
		this.total_output_power = total_output_power;
	}
	public BigDecimal getInstalled_capacity() {
		return installed_capacity;
	}
	public void setInstalled_capacity(BigDecimal installed_capacity) {
		this.installed_capacity = installed_capacity;
	}
	public String getMatrix_id() {
		return matrix_id;
	}
	public void setMatrix_id(String matrix_id) {
		this.matrix_id = matrix_id;
	}

	public String getStation_id() {
		return station_id;
	}

	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	
}
