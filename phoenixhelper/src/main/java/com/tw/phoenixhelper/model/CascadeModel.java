package com.tw.phoenixhelper.model;

import java.math.BigDecimal;
/**
 * 
 * @author xiesc
 * @TODO 组串式逆变器
 * @time 2019年1月16日
 * @version 1.0
 */
public class CascadeModel {
	private String esn;
	private BigDecimal power_factor;
	private long timestamps;
	private BigDecimal dc_i;
	private BigDecimal total_output_power;
	private BigDecimal output_power_month;
	private BigDecimal uca;
	private BigDecimal reactive_power;
	private BigDecimal uab;
	private BigDecimal ia;
	private BigDecimal temperature;
	private BigDecimal ib;
	private BigDecimal ic;
	private BigDecimal dc_u;
	private BigDecimal line_frequency;
	private BigDecimal total_run_time;
	private BigDecimal i1;
	private BigDecimal i2;
	private BigDecimal i3;
	private BigDecimal i4;
	private BigDecimal i5;
	private BigDecimal i6;
	private BigDecimal i7;
	private BigDecimal i8;
	private BigDecimal u1;
	private BigDecimal u2;
	private BigDecimal u3;
	private BigDecimal u4;
	private BigDecimal u5;
	private BigDecimal u6;
	private BigDecimal u7;
	private BigDecimal u8;
	private BigDecimal inverter_efficiency;
	private BigDecimal output_power_day;
	private BigDecimal ubc;
	private BigDecimal active_power;
	private BigDecimal dc_input_total_power;
	private BigDecimal run_minute_day;
	
	public String getEsn() {
		return esn;
	}
	public void setEsn(String esn) {
		this.esn = esn;
	}
	public BigDecimal getPower_factor() {
		return power_factor;
	}
	public void setPower_factor(BigDecimal power_factor) {
		this.power_factor = power_factor;
	}
	public long getTimestamps() {
		return timestamps;
	}
	public void setTimestamps(long timestamps) {
		this.timestamps = timestamps;
	}
	public BigDecimal getDc_i() {
		return dc_i;
	}
	public void setDc_i(BigDecimal dc_i) {
		this.dc_i = dc_i;
	}
	public BigDecimal getTotal_output_power() {
		return total_output_power;
	}
	public void setTotal_output_power(BigDecimal total_output_power) {
		this.total_output_power = total_output_power;
	}
	public BigDecimal getOutput_power_month() {
		return output_power_month;
	}
	public void setOutput_power_month(BigDecimal output_power_month) {
		this.output_power_month = output_power_month;
	}
	public BigDecimal getUca() {
		return uca;
	}
	public void setUca(BigDecimal uca) {
		this.uca = uca;
	}
	public BigDecimal getReactive_power() {
		return reactive_power;
	}
	public void setReactive_power(BigDecimal reactive_power) {
		this.reactive_power = reactive_power;
	}
	public BigDecimal getUab() {
		return uab;
	}
	public void setUab(BigDecimal uab) {
		this.uab = uab;
	}
	public BigDecimal getIa() {
		return ia;
	}
	public void setIa(BigDecimal ia) {
		this.ia = ia;
	}
	public BigDecimal getTemperature() {
		return temperature;
	}
	public void setTemperature(BigDecimal temperature) {
		this.temperature = temperature;
	}
	public BigDecimal getIb() {
		return ib;
	}
	public void setIb(BigDecimal ib) {
		this.ib = ib;
	}
	public BigDecimal getIc() {
		return ic;
	}
	public void setIc(BigDecimal ic) {
		this.ic = ic;
	}
	public BigDecimal getDc_u() {
		return dc_u;
	}
	public void setDc_u(BigDecimal dc_u) {
		this.dc_u = dc_u;
	}
	public BigDecimal getLine_frequency() {
		return line_frequency;
	}
	public void setLine_frequency(BigDecimal line_frequency) {
		this.line_frequency = line_frequency;
	}
	public BigDecimal getTotal_run_time() {
		return total_run_time;
	}
	public void setTotal_run_time(BigDecimal total_run_time) {
		this.total_run_time = total_run_time;
	}
	public BigDecimal getI1() {
		return i1;
	}
	public void setI1(BigDecimal i1) {
		this.i1 = i1;
	}
	public BigDecimal getI2() {
		return i2;
	}
	public void setI2(BigDecimal i2) {
		this.i2 = i2;
	}
	public BigDecimal getI3() {
		return i3;
	}
	public void setI3(BigDecimal i3) {
		this.i3 = i3;
	}
	public BigDecimal getI4() {
		return i4;
	}
	public void setI4(BigDecimal i4) {
		this.i4 = i4;
	}
	public BigDecimal getI5() {
		return i5;
	}
	public void setI5(BigDecimal i5) {
		this.i5 = i5;
	}
	public BigDecimal getI6() {
		return i6;
	}
	public void setI6(BigDecimal i6) {
		this.i6 = i6;
	}
	public BigDecimal getI7() {
		return i7;
	}
	public void setI7(BigDecimal i7) {
		this.i7 = i7;
	}
	public BigDecimal getI8() {
		return i8;
	}
	public void setI8(BigDecimal i8) {
		this.i8 = i8;
	}
	public BigDecimal getU1() {
		return u1;
	}
	public void setU1(BigDecimal u1) {
		this.u1 = u1;
	}
	public BigDecimal getU2() {
		return u2;
	}
	public void setU2(BigDecimal u2) {
		this.u2 = u2;
	}
	public BigDecimal getU3() {
		return u3;
	}
	public void setU3(BigDecimal u3) {
		this.u3 = u3;
	}
	public BigDecimal getU4() {
		return u4;
	}
	public void setU4(BigDecimal u4) {
		this.u4 = u4;
	}
	public BigDecimal getU5() {
		return u5;
	}
	public void setU5(BigDecimal u5) {
		this.u5 = u5;
	}
	public BigDecimal getU6() {
		return u6;
	}
	public void setU6(BigDecimal u6) {
		this.u6 = u6;
	}
	public BigDecimal getU7() {
		return u7;
	}
	public void setU7(BigDecimal u7) {
		this.u7 = u7;
	}
	public BigDecimal getU8() {
		return u8;
	}
	public void setU8(BigDecimal u8) {
		this.u8 = u8;
	}
	public BigDecimal getInverter_efficiency() {
		return inverter_efficiency;
	}
	public void setInverter_efficiency(BigDecimal inverter_efficiency) {
		this.inverter_efficiency = inverter_efficiency;
	}
	public BigDecimal getOutput_power_day() {
		return output_power_day;
	}
	public void setOutput_power_day(BigDecimal output_power_day) {
		this.output_power_day = output_power_day;
	}
	public BigDecimal getUbc() {
		return ubc;
	}
	public void setUbc(BigDecimal ubc) {
		this.ubc = ubc;
	}
	public BigDecimal getActive_power() {
		return active_power;
	}
	public void setActive_power(BigDecimal active_power) {
		this.active_power = active_power;
	}
	public BigDecimal getDc_input_total_power() {
		return dc_input_total_power;
	}
	public void setDc_input_total_power(BigDecimal dc_input_total_power) {
		this.dc_input_total_power = dc_input_total_power;
	}
	public BigDecimal getRun_minute_day() {
		return run_minute_day;
	}
	public void setRun_minute_day(BigDecimal run_minute_day) {
		this.run_minute_day = run_minute_day;
	}
	
}
