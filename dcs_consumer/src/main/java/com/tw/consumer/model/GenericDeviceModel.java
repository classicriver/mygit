package com.tw.consumer.model;

import java.util.Map;

import com.tw.consumer.utils.DeviceType;

public class GenericDeviceModel {
	
	private DeviceType type;
	private Map<String,Object> value;
	
	public DeviceType getType() {
		return type;
	}
	public void setType(DeviceType type) {
		this.type = type;
	}
	public Map<String,Object> getValue() {
		return value;
	}
	public void setValue(Map<String,Object> value) {
		this.value = value;
	}

	
}
