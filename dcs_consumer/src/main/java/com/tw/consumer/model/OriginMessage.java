package com.tw.consumer.model;
/**
 * 
 * @author xiesc
 * @TODO kafka最原始的json字符串
 * @time 2018年9月6日
 * @version 1.0
 */
public class OriginMessage {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return message.toString();
	}
}
