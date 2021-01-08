package com.tw.consumer.kfk.producer;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.clients.producer.Producer;

import com.tw.consumer.config.Config;
import com.tw.consumer.config.KafkaConfig;
import com.tw.consumer.core.AutoShutdown;
import com.tw.consumer.device.dao.Dao;

public abstract class AbstactKfkProducer<P,T> extends KafkaConfig implements AutoShutdown,Dao<T>{
	
	protected final AtomicInteger counter;
	protected final int batchNum = Config.getInstance().getBatchNumber();
	/**
	 * producer 是线程安全的
	 */
	protected Producer<String, P> producer;
	
	protected AbstactKfkProducer(){
		counter = new AtomicInteger();
	}
	
	public void submit() {
		int size = counter.incrementAndGet();
		if(size % batchNum == 0){
			producer.flush();
		}
	}
	
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		producer.flush();
		producer.close();
	}
}
