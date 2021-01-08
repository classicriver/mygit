package com.tw.consumer.model;
/**
 * 
 * @author xiesc
 * @TODO kylin cube
 * @time 2019年4月8日
 * @version 1.0
 */
public class Cube {
	
	private String name;
	private String uuid;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "name="+name+" uuid="+uuid;
	}
	
}
