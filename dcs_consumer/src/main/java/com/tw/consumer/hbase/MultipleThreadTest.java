package com.tw.consumer.hbase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultipleThreadTest {
	
	private static CountDownLatch l = new CountDownLatch(10);
	
	static class Test implements Runnable{
		private HbaseClientWriter client;
		Test(HbaseClientWriter client){
			this.client = client;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			client.test();
			l.countDown();
		}
		
	}
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		ExecutorService pool = Executors.newCachedThreadPool();
		HbaseClientWriter client = new HbaseClientWriter();
		Test t = new MultipleThreadTest.Test(client);
		for(int i = 0 ;i <10;i++){
			pool.execute(t);
		}
		try {
			l.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.shutdown();
		pool.shutdown();
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
}	
