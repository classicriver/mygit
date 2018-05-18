package com.tw.model;

/**
 * 
 * @author xiesc
 * @TODO  协议实体类
 * @time 2018年5月7日
 * @version 1.0
 */
public class Protocol {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String value) {
		this.message = value;
	}
	
	public void clear(){
		message = null;
	}
}
