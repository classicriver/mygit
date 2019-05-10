package com.tw.consumer.model;

import java.util.Map;

public class YhMessage {
	/**
	 * 逆变器sn编码
	 */
	private String esn;
	/**
	 * 遥测
	 */
	private Map<String,Object> yc;
	/**
	 * 遥信
	 */
	private Map<String,Object> yx;
	/**
	 * 上传时间
	 */
	private String time;
	
	public String getEsn() {
		return esn;
	}
	public void setEsn(String esn) {
		this.esn = esn;
	}
	public Map<String,Object> getYc() {
		return yc;
	}
	public void setYc(Map<String,Object> yc) {
		this.yc = yc;
	}
	public Map<String,Object> getYx() {
		return yx;
	}
	public void setYx(Map<String,Object> yx) {
		this.yx = yx;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	
}
