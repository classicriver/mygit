package com.tw.hbasehelper.core;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.apache.hadoop.hbase.client.Scan;

import com.tw.hbasehelper.client.HbaseClientManager;

public class QueryTask implements Callable<List<Map<String,Map<String,Object>>>>{

	private final HbaseClientManager client;
	private final Scan scan;
	private final CountDownLatch latch;
	
	public QueryTask(HbaseClientManager client,Scan scan,CountDownLatch latch){
		this.client = client;
		this.scan = scan;
		this.latch = latch;
	}
	
	@Override
	public List<Map<String,Map<String,Object>>> call() throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Map<String, Object>>> query = client.query(scan);
		latch.countDown();
		return query;
	}

}
