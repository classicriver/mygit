package com.tw.consumer.model;

import java.util.Map;

public class CopyOfYhMessage {
	/**
	 * 逆变器sn编码
	 */
	private String esn;
	/**
	 * 遥测
	 */
	private Object yc;
	/**
	 * 遥信
	 */
	private Object yx;
	/**
	 * 上传时间
	 */
	private long timestamps;
	
	public String getEsn() {
		return esn;
	}
	public void setEsn(String esn) {
		this.esn = esn;
	}
	
	public Object getYc() {
		return yc;
	}
	public void setYc(Object yc) {
		this.yc = yc;
	}
	public Object getYx() {
		return yx;
	}
	public void setYx(Object yx) {
		this.yx = yx;
	}
	public long getTimestamps() {
		return timestamps;
	}
	public void setTimestamps(long timestamps) {
		this.timestamps = timestamps;
	}
	
	

	
}
