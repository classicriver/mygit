package com.tw.consumer.model;

import com.tw.consumer.redis.RedisOperation;

public class RedisModel {
	
	private byte[] key;
	private byte[] value;
	private RedisOperation operation;
	
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
	public RedisOperation getOperation() {
		return operation;
	}
	public void setOperation(RedisOperation operation) {
		this.operation = operation;
	}
	
}
