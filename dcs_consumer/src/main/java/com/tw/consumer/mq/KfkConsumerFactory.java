package com.tw.consumer.mq;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.lmax.disruptor.RingBuffer;
import com.tw.consumer.config.Config;
import com.tw.consumer.core.ThreadFactoryBean;
import com.tw.consumer.model.MMessage;
/**
 * 
 * @author xiesc
 * @TODO
 * @time 2018年8月23日
 * @version 1.0
 */
public class KfkConsumerFactory {
	
	private final int threadCount = Config.getInstance().getKafkaConsumers();
	//kafka consumer线程池
	private final ExecutorService executor = ThreadFactoryBean.getFixedThreadPool("kafkaConsumerThread: ", threadCount);;				
	private final RingBuffer<MMessage> ringBuffer;
	private final List<KfkConsumer> consumers = new ArrayList<>();
	
	public KfkConsumerFactory(RingBuffer<MMessage> ringBuffer){
		this.ringBuffer = ringBuffer;
	}
	/**
	 * KfkConsumer 并不是线程安全的，每个线程单独创建一个KfkConsumer.
	 * @param count
	 */
	public void startConsumers(){
		for (int i = 0; i < threadCount; i++) {
			KfkConsumer consumer = new KfkConsumer(ringBuffer);
			executor.execute(consumer);
			consumers.add(consumer);
		}
	}
	
	public void shutdown(){
		for(KfkConsumer consumer : consumers){
			consumer.stop();
		}
		executor.shutdown();
	}
}
