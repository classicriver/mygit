package com.tw.ddcs.model;

import java.util.Map;

public class GeneralMachineModel {
	
	private int circulation;
	private Map<String,String> properties;
	
	public int getCirculation() {
		return circulation;
	}
	
	public void setCirculation(int circulation) {
		this.circulation = circulation;
	}
	
	public Map<String,String> getProperties() {
		return properties;
	}
	
	public void setProperties(Map<String,String> properties) {
		this.properties = properties;
	}
	
}
