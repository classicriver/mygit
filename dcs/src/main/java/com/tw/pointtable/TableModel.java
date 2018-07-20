package com.tw.pointtable;

import java.util.Map;

public class TableModel {
	
	private String protocolName;
	private Map<String,Object> body;
	
	public String getProtocolName() {
		return protocolName;
	}
	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}
	public Map<String, Object> getBody() {
		return body;
	}
	public void setBody(Map<String, Object> body) {
		this.body = body;
	}
	
}
