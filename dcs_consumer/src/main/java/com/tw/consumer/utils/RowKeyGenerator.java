package com.tw.consumer.utils;

public interface RowKeyGenerator {
	
	public byte[] getRowKey(String time,String sn);
	
}
