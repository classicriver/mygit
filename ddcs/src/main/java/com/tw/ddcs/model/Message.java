package com.tw.ddcs.model;
import java.util.HashMap;
/**
 * 
 * @author xiesc
 * @TODO 
 * @time 2018年8月2日
 * @version 1.0
 */
public class Message {
	/**
	 * 逆变器sn编码
	 */
	private String sn;
	/**
	 * 遥测
	 */
	private HashMap<String,Object> data_yc;
	/**
	 * 遥信
	 */
	private HashMap<String,Object> data_yx;
	/**
	 * 上传时间
	 */
	private String time;
	
	private boolean managed;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public HashMap<String,Object> getData_yc() {
		return data_yc;
	}

	public void setData_yc(HashMap<String,Object> data_yc) {
		this.data_yc = data_yc;
	}

	public HashMap<String,Object> getData_yx() {
		return data_yx;
	}

	public void setData_yx(HashMap<String,Object> data_yx) {
		this.data_yx = data_yx;
	}

	public boolean isManaged() {
		return managed;
	}

	public void setManaged(boolean managed) {
		this.managed = managed;
	}
	
}
