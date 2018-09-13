package com.tw.hbasehelper.client;

import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.Scan;

public interface HbaseClientInterface {
	
	public void close() throws Exception;
	
	public List<Map<String,Map<String,Object>>> query(Scan scan);
}
