package com.tw.kylinhelper.model;

import java.math.BigDecimal;

/**
 * 
 * @author xiesc
 * @TODO 汇流箱离散率model
 * @time 2019年4月2日
 * @version 1.0
 */
public class CombinerdcDisperse{
	private String station;
	private String esn;
	private String day_string;
	/**
	 * pv容量
	 */
	private BigDecimal pv_capacity;
	/**
	 * 平均母线电压
	 */
	private BigDecimal pvu_average;
	/**
	 * 最大母线电流
	 */
	private BigDecimal pi_max;
	/**
	 * 平均母线电流
	 */
	private BigDecimal pi_average;
	/**
	 * 平均离散率
	 */
	private BigDecimal disperse_average;
	/**
	 * 3%
	 */
	private BigDecimal three_percent;
	/**
	 * 5%
	 */
	private BigDecimal five_percent;
	/**
	 * 10%
	 */
	private BigDecimal ten_percent;
	/**
	 * 20%
	 */
	private BigDecimal twenty_percent;
	/**
	 * 20%以上
	 */
	private BigDecimal above_twenty_percent;
	/**
	 * 无效
	 */
	private BigDecimal invalid_percent;
	private BigDecimal i1_average;
	private BigDecimal i1_max;
	private BigDecimal i2_average;
	private BigDecimal i2_max;
	private BigDecimal i3_average;
	private BigDecimal i3_max;
	private BigDecimal i4_average;
	private BigDecimal i4_max;
	private BigDecimal i5_average;
	private BigDecimal i5_max;
	private BigDecimal i6_average;
	private BigDecimal i6_max;
	private BigDecimal i7_average;
	private BigDecimal i7_max;
	private BigDecimal i8_average;
	private BigDecimal i8_max;
	private BigDecimal i9_average;
	private BigDecimal i9_max;
	private BigDecimal i10_average;
	private BigDecimal i10_max;
	private BigDecimal i11_average;
	private BigDecimal i11_max;
	private BigDecimal i12_average;
	private BigDecimal i12_max;
	private BigDecimal i13_average;
	private BigDecimal i13_max;
	private BigDecimal i14_average;
	private BigDecimal i14_max;
	private BigDecimal i15_average;
	private BigDecimal i15_max;
	private BigDecimal i16_average;
	private BigDecimal i16_max;
	private BigDecimal i17_average;
	private BigDecimal i17_max;
	private BigDecimal i18_average;
	private BigDecimal i18_max;
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "station="+station+" esn="+esn+" day_string="+day_string+" pv_capacity="+pv_capacity
				+" pvu_average="+pvu_average+" pi_max="+pi_max+" pi_average="+pi_average+" disperse_average="+disperse_average
				+" three_percent="+three_percent+" five_percent="+five_percent+" ten_percent="+ten_percent
				+" twenty_percent="+twenty_percent+" above_twenty_percent="+above_twenty_percent;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getEsn() {
		return esn;
	}
	public void setEsn(String esn) {
		this.esn = esn;
	}
	public String getDay_string() {
		return day_string;
	}
	public void setDay_string(String day_string) {
		this.day_string = day_string;
	}
	public BigDecimal getPv_capacity() {
		return pv_capacity;
	}
	public void setPv_capacity(BigDecimal pv_capacity) {
		this.pv_capacity = pv_capacity;
	}
	public BigDecimal getPvu_average() {
		return pvu_average;
	}
	public void setPvu_average(BigDecimal pvu_average) {
		this.pvu_average = pvu_average;
	}
	public BigDecimal getPi_max() {
		return pi_max;
	}
	public void setPi_max(BigDecimal pi_max) {
		this.pi_max = pi_max;
	}
	public BigDecimal getPi_average() {
		return pi_average;
	}
	public void setPi_average(BigDecimal pi_average) {
		this.pi_average = pi_average;
	}
	public BigDecimal getDisperse_average() {
		return disperse_average;
	}
	public void setDisperse_average(BigDecimal disperse_average) {
		this.disperse_average = disperse_average;
	}
	public BigDecimal getThree_percent() {
		return three_percent;
	}
	public void setThree_percent(BigDecimal three_percent) {
		this.three_percent = three_percent;
	}
	public BigDecimal getFive_percent() {
		return five_percent;
	}
	public void setFive_percent(BigDecimal five_percent) {
		this.five_percent = five_percent;
	}
	public BigDecimal getTen_percent() {
		return ten_percent;
	}
	public void setTen_percent(BigDecimal ten_percent) {
		this.ten_percent = ten_percent;
	}
	public BigDecimal getTwenty_percent() {
		return twenty_percent;
	}
	public void setTwenty_percent(BigDecimal twenty_percent) {
		this.twenty_percent = twenty_percent;
	}
	public BigDecimal getAbove_twenty_percent() {
		return above_twenty_percent;
	}
	public void setAbove_twenty_percent(BigDecimal above_twenty_percent) {
		this.above_twenty_percent = above_twenty_percent;
	}
	public BigDecimal getInvalid_percent() {
		return invalid_percent;
	}
	public void setInvalid_percent(BigDecimal invalid_percent) {
		this.invalid_percent = invalid_percent;
	}
	public BigDecimal getI1_average() {
		return i1_average;
	}
	public void setI1_average(BigDecimal i1_average) {
		this.i1_average = i1_average;
	}
	public BigDecimal getI1_max() {
		return i1_max;
	}
	public void setI1_max(BigDecimal i1_max) {
		this.i1_max = i1_max;
	}
	public BigDecimal getI2_average() {
		return i2_average;
	}
	public void setI2_average(BigDecimal i2_average) {
		this.i2_average = i2_average;
	}
	public BigDecimal getI2_max() {
		return i2_max;
	}
	public void setI2_max(BigDecimal i2_max) {
		this.i2_max = i2_max;
	}
	public BigDecimal getI3_average() {
		return i3_average;
	}
	public void setI3_average(BigDecimal i3_average) {
		this.i3_average = i3_average;
	}
	public BigDecimal getI3_max() {
		return i3_max;
	}
	public void setI3_max(BigDecimal i3_max) {
		this.i3_max = i3_max;
	}
	public BigDecimal getI4_average() {
		return i4_average;
	}
	public void setI4_average(BigDecimal i4_average) {
		this.i4_average = i4_average;
	}
	public BigDecimal getI4_max() {
		return i4_max;
	}
	public void setI4_max(BigDecimal i4_max) {
		this.i4_max = i4_max;
	}
	public BigDecimal getI5_average() {
		return i5_average;
	}
	public void setI5_average(BigDecimal i5_average) {
		this.i5_average = i5_average;
	}
	public BigDecimal getI5_max() {
		return i5_max;
	}
	public void setI5_max(BigDecimal i5_max) {
		this.i5_max = i5_max;
	}
	public BigDecimal getI6_average() {
		return i6_average;
	}
	public void setI6_average(BigDecimal i6_average) {
		this.i6_average = i6_average;
	}
	public BigDecimal getI6_max() {
		return i6_max;
	}
	public void setI6_max(BigDecimal i6_max) {
		this.i6_max = i6_max;
	}
	public BigDecimal getI7_average() {
		return i7_average;
	}
	public void setI7_average(BigDecimal i7_average) {
		this.i7_average = i7_average;
	}
	public BigDecimal getI7_max() {
		return i7_max;
	}
	public void setI7_max(BigDecimal i7_max) {
		this.i7_max = i7_max;
	}
	public BigDecimal getI8_average() {
		return i8_average;
	}
	public void setI8_average(BigDecimal i8_average) {
		this.i8_average = i8_average;
	}
	public BigDecimal getI8_max() {
		return i8_max;
	}
	public void setI8_max(BigDecimal i8_max) {
		this.i8_max = i8_max;
	}
	public BigDecimal getI9_average() {
		return i9_average;
	}
	public void setI9_average(BigDecimal i9_average) {
		this.i9_average = i9_average;
	}
	public BigDecimal getI9_max() {
		return i9_max;
	}
	public void setI9_max(BigDecimal i9_max) {
		this.i9_max = i9_max;
	}
	public BigDecimal getI10_average() {
		return i10_average;
	}
	public void setI10_average(BigDecimal i10_average) {
		this.i10_average = i10_average;
	}
	public BigDecimal getI10_max() {
		return i10_max;
	}
	public void setI10_max(BigDecimal i10_max) {
		this.i10_max = i10_max;
	}
	public BigDecimal getI11_average() {
		return i11_average;
	}
	public void setI11_average(BigDecimal i11_average) {
		this.i11_average = i11_average;
	}
	public BigDecimal getI11_max() {
		return i11_max;
	}
	public void setI11_max(BigDecimal i11_max) {
		this.i11_max = i11_max;
	}
	public BigDecimal getI12_average() {
		return i12_average;
	}
	public void setI12_average(BigDecimal i12_average) {
		this.i12_average = i12_average;
	}
	public BigDecimal getI12_max() {
		return i12_max;
	}
	public void setI12_max(BigDecimal i12_max) {
		this.i12_max = i12_max;
	}
	public BigDecimal getI13_average() {
		return i13_average;
	}
	public void setI13_average(BigDecimal i13_average) {
		this.i13_average = i13_average;
	}
	public BigDecimal getI13_max() {
		return i13_max;
	}
	public void setI13_max(BigDecimal i13_max) {
		this.i13_max = i13_max;
	}
	public BigDecimal getI14_average() {
		return i14_average;
	}
	public void setI14_average(BigDecimal i14_average) {
		this.i14_average = i14_average;
	}
	public BigDecimal getI14_max() {
		return i14_max;
	}
	public void setI14_max(BigDecimal i14_max) {
		this.i14_max = i14_max;
	}
	public BigDecimal getI15_average() {
		return i15_average;
	}
	public void setI15_average(BigDecimal i15_average) {
		this.i15_average = i15_average;
	}
	public BigDecimal getI15_max() {
		return i15_max;
	}
	public void setI15_max(BigDecimal i15_max) {
		this.i15_max = i15_max;
	}
	public BigDecimal getI16_average() {
		return i16_average;
	}
	public void setI16_average(BigDecimal i16_average) {
		this.i16_average = i16_average;
	}
	public BigDecimal getI16_max() {
		return i16_max;
	}
	public void setI16_max(BigDecimal i16_max) {
		this.i16_max = i16_max;
	}
	public BigDecimal getI17_average() {
		return i17_average;
	}
	public void setI17_average(BigDecimal i17_average) {
		this.i17_average = i17_average;
	}
	public BigDecimal getI17_max() {
		return i17_max;
	}
	public void setI17_max(BigDecimal i17_max) {
		this.i17_max = i17_max;
	}
	public BigDecimal getI18_average() {
		return i18_average;
	}
	public void setI18_average(BigDecimal i18_average) {
		this.i18_average = i18_average;
	}
	public BigDecimal getI18_max() {
		return i18_max;
	}
	public void setI18_max(BigDecimal i18_max) {
		this.i18_max = i18_max;
	}
	
}
