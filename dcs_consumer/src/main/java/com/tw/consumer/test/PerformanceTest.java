package com.tw.consumer.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PerformanceTest {
	
	public static void main(String[] args) {
		int threadCount = 20;
		long startTime = System.currentTimeMillis();
		ExecutorService pool = Executors.newCachedThreadPool();
		CountDownLatch latch = new CountDownLatch(threadCount);
		for (int i = 0; i < threadCount; i++) {
			TestClient client = new HttpClientTest();
			client.init(100);
			pool.submit(client);
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
		System.exit(0);
	}
}
