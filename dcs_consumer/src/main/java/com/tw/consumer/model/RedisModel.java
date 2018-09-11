package com.tw.consumer.model;

import java.util.Map;

public class RedisModel {
	
	private String key;
	private Map<String,Object> map;
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public Map<String,Object> getMap() {
		return map;
	}
	
	public void setMap(Map<String,Object> map) {
		this.map = map;
	}
	
}
