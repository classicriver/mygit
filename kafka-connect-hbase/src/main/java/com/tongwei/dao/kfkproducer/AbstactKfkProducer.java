package com.tongwei.dao.kfkproducer;

import java.util.Properties;

import org.apache.kafka.clients.producer.Producer;

public abstract class AbstactKfkProducer<P>{
	
	protected final Properties pro = new Properties();
	/**
	 * producer 是线程安全的
	 */
	protected Producer<String, P> producer;
	
	protected AbstactKfkProducer(){
		pro.put("ack", "1");
		pro.put("compression.type", "gzip");
	}
	
	public void close(){
		producer.flush();
		producer.close();
	}
}
