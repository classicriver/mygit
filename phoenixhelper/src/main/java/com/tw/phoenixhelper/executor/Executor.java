package com.tw.phoenixhelper.executor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Executor {
	
	private final static ThreadFactory threadFactory = new ThreadFactory() {
		private AtomicInteger atomic = new AtomicInteger();

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "phoenixhelper:" + this.atomic.getAndIncrement());
		}
	};
	private final static ExecutorService threadPool = Executors
			.newCachedThreadPool(threadFactory);
	
	public Map<String, List<Map<String, Object>>> rangeQuery(List<String> esns, long startTime, long endTime,String... columns) throws Exception {
		return this.execut(esns, startTime, endTime, columns);
	}

	private Map<String, List<Map<String, Object>>> execut(List<String> esnList, long startTime, long endTime, String[] columns) throws Exception {
		Map<String, List<Map<String, Object>>> result = new HashMap<>();
		CompletionService<Map<String, List<Map<String, Object>>>> future = new ExecutorCompletionService<>(
				threadPool);
		CountDownLatch latch = new CountDownLatch(esnList.size());
		for (int i = 0; i < esnList.size(); i++) {
			future.submit(new QueryTask(esnList.get(i),startTime,endTime,latch,columns));
		}
		latch.await(10, TimeUnit.SECONDS);
		for (int i = 0; i < esnList.size(); i++) {
			result.putAll(future.take().get());
		}
		return result;
	}
	
}
