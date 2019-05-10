package com.tw.hbasehelper.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.apache.hadoop.hbase.client.Get;

import com.tw.hbasehelper.client.HbaseClient;

public class GetTask implements Callable<Map<String,List<Map<String, Object>>>>{

	private final HbaseClient client;
	private final String esn;
	private final List<Get> getList;
	private final CountDownLatch latch;
	
	public GetTask(HbaseClient client,String esn,List<Get> getList,CountDownLatch latch){
		this.esn = esn;
		this.client = client;
		this.getList = getList;
		this.latch = latch;
	}
	
	@Override
	public Map<String, List<Map<String, Object>>> call() throws Exception {
		// TODO Auto-generated method stub
		Map<String,List<Map<String, Object>>> map = new HashMap<>();
		map.put(esn, client.batchGet(getList));
		latch.countDown();
		client.close();
		return map;
	}

}
