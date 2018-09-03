package com.tw.consumer.hbase;

import java.util.List;

import org.apache.hadoop.hbase.client.Put;

public interface HbaseClientInterface {
	
	public void save(byte[] data);
	
	public void save(List<Put> data);
	
	public void close();
	
}
