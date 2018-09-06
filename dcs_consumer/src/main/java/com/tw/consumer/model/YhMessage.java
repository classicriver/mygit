package com.tw.consumer.model;

import java.util.HashMap;
import java.util.Map;

public class YhMessage {
	/**
	 * 逆变器sn编码
	 */
	private String sn;
	/**
	 * 遥测
	 */
	private Map<String,Object> data_yc;
	/**
	 * 遥信
	 */
	private Map<String,Object> data_yx;
	/**
	 * 上传时间
	 */
	private String time;
	
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Map<String,Object> getData_yc() {
		return data_yc;
	}
	public void setData_yc(HashMap<String,Object> data_yc) {
		this.data_yc = data_yc;
	}
	public Map<String,Object> getData_yx() {
		return data_yx;
	}
	public void setData_yx(HashMap<String,Object> data_yx) {
		this.data_yx = data_yx;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
