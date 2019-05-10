package com.tw.flink;

public class Test {
	private String esn;
	private long timestamp;
	private long time;
	private int value;
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return esn.hashCode();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "esn:"+esn+" timestamp:"+timestamp+" value:"+value;
	}
	public String getEsn() {
		return esn;
	}
	public void setEsn(String esn) {
		this.esn = esn;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}

	
}
