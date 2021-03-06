package com.tw.consumer.kfk;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

import com.lmax.disruptor.RingBuffer;
import com.tw.consumer.config.Config;
import com.tw.consumer.core.AutoShutdown;
import com.tw.consumer.core.ThreadFactoryBean;
import com.tw.consumer.model.OriginMessage;
/**
 * 
 * @author xiesc
 * @TODO
 * @time 2018年8月23日
 * @version 1.0
 */
public class KfkConsumerFactory implements AutoShutdown{
	
	private final int threadCount = Config.getInstance().getKafkaConsumers();
	//kafka consumer线程池
	private final ExecutorService executor = ThreadFactoryBean.getFixedThreadPool("", threadCount);
	private final List<KfkConsumer> consumers = new ArrayList<>();
	private final Semaphore semaphore;
	private final RingBuffer<OriginMessage> ringBuffer;
	
	public KfkConsumerFactory(RingBuffer<OriginMessage> ringBuffer,Semaphore semaphore){
		this.ringBuffer = ringBuffer;
		this.semaphore = semaphore;
	}
	/**
	 * KfkConsumer 并不是线程安全的，每个线程单独创建一个KfkConsumer.
	 * @param count
	 */
	public void startConsumers(){
		for (int i = 0; i < threadCount; i++) {
			KfkConsumer consumer = new KfkConsumer(ringBuffer,semaphore);
			executor.execute(consumer);
			consumers.add(consumer);
		}
	}
	
	@Override
	public void shutdown(){
		for(KfkConsumer consumer : consumers){
			consumer.stop();
		}
		executor.shutdown();
	}
}
