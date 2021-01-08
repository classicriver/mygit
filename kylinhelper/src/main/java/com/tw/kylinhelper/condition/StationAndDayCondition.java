package com.tw.kylinhelper.condition;
/**
 * 
 * @author xiesc
 * @TODO 电站+日期条件
 * @time 2019年4月2日
 * @version 1.0
 */
public class StationAndDayCondition extends BaseCondition{
	/**
	 * eg: 18
	 */
	private String station;
	/**
	 * 格式：yyyy-mm-dd</br>
	 * eg: 2019-03-29
	 */
	private String day;
	
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	
}
