package com.tw.consumer.utils;

public interface RowKeyGenerator {
	
	public byte[] getRowKey(long time,String sn);
	
}
