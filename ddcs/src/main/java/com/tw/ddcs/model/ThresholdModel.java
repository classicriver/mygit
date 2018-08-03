package com.tw.ddcs.model;

import java.math.BigDecimal;

import com.tw.ddcs.threshold.CompareType;

public class ThresholdModel {
	
	private String key;
	private CompareType type;
	private BigDecimal value;
	
	public CompareType getType() {
		return type;
	}
	public void setType(CompareType type) {
		this.type = type;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
}
