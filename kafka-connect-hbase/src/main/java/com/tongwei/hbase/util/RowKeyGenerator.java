package com.tongwei.hbase.util;

public interface RowKeyGenerator {
	
	public byte[] getRowKey(String time,String sn);
	
}
