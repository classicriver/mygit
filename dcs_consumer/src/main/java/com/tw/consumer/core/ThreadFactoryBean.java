package com.tw.consumer.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author xiesc
 * @TODO 
 * @time 2018年8月23日
 * @version 1.0
 */
public class ThreadFactoryBean {
	
	private ThreadFactoryBean(){}
	
	public static ThreadFactory getThreadFactory(String name){
		return new ThreadFactory() {
			private AtomicInteger atomic = new AtomicInteger();
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, name + this.atomic.getAndIncrement());
			}
		};
	}
	
	public static ExecutorService getFixedThreadPool(String name,int count){
		return Executors.newFixedThreadPool(count,getThreadFactory(name));
	}
	
	public static ScheduledExecutorService getScheduledThreadPool(String name,int count){
		return Executors.newScheduledThreadPool(count,getThreadFactory(name));
	}
}
