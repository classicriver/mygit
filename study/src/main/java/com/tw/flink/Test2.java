package com.tw.flink;

public class Test2 {
	private String esn;
	private long count;
	public String getEsn() {
		return esn;
	}
	public void setEsn(String esn) {
		this.esn = esn;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	private int value;
	private java.sql.Timestamp end;
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return esn.hashCode();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "esn:"+esn+" count:"+count+" value:"+value +" endtime:"+end;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public java.sql.Timestamp getEnd() {
		return end;
	}
	public void setEnd(java.sql.Timestamp end) {
		this.end = end;
	}
	
	
	
}
