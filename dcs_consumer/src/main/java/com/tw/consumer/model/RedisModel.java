package com.tw.consumer.model;

public class RedisModel {
	
	private byte[] key;
	private byte[] value;
	
	public byte[] getKey() {
		return key;
	}
	public void setKey(byte[] key) {
		this.key = key;
	}
	public byte[] getValue() {
		return value;
	}
	public void setValue(byte[] value) {
		this.value = value;
	}
	
}
