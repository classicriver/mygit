package com.tw.hbasehelper.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.apache.hadoop.hbase.client.Scan;

import com.tw.hbasehelper.client.HbaseClient;

public class ScanTask implements  Callable<Map<String,List<Map<String, Object>>>>{

	private final HbaseClient client;
	private final String esn;
	private final Scan scan;
	private final CountDownLatch latch;
	
	public ScanTask(HbaseClient client,String esn,Scan scan,CountDownLatch latch){
		this.esn = esn;
		this.client = client;
		this.scan = scan;
		this.latch = latch;
	}
	/**
	 * 数据格式
	 * {esn:[{i1:1,u1:1},{i1:0,i2:1}],esn:[{i1:1,u1:1},{i1:0,i2:1}]}
	 */
	@Override
	public Map<String,List<Map<String, Object>>> call() throws Exception {
		// TODO Auto-generated method stub
		Map<String,List<Map<String, Object>>> map = new HashMap<>();
		map.put(esn, client.query(scan));
		latch.countDown();
		client.close();
		return map;
	}
	
	

}
