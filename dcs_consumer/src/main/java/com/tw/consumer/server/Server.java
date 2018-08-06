package com.tw.consumer.server;

import java.io.IOException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tw.consumer.config.Config;
import com.tw.consumer.disruptor.IntEventExceptionHandler;
import com.tw.consumer.disruptor.MMessageEventFactory;
import com.tw.consumer.disruptor.WorkHandlerBuilder;
import com.tw.consumer.model.MMessage;
import com.tw.consumer.mq.PullConsumer;

public class Server {
	/**
	 * 环形队列
	 */
	private RingBuffer<MMessage> ringBuffer;

	private Disruptor<MMessage> disruptor;
	
	private WorkHandlerBuilder builder;
	
	private final ThreadFactory workers = new ThreadFactory() {
		private AtomicInteger atomic = new AtomicInteger();
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "AnalysizerThread: "
					+ this.atomic.getAndIncrement());
		}
	};
	
	private void startDisruptor(){
		// 创建工厂
		EventFactory<MMessage> factory = new MMessageEventFactory();
		// 创建bufferSize ,也就是RingBuffer大小，必须是8的N次方
		final int bufferSize = 1024 * 1024 * 8;
		disruptor = new Disruptor<MMessage>(factory, bufferSize, workers,
				ProducerType.MULTI, new BlockingWaitStrategy());
		disruptor.setDefaultExceptionHandler(new IntEventExceptionHandler());
		builder = new WorkHandlerBuilder();
		disruptor.handleEventsWithWorkerPool(builder.build(
				Config.getInstance().getMaxThreads()));
		ringBuffer = disruptor.start();
	}
	
	public void run(){
		try{
			startDisruptor();
			PullConsumer consumer = new PullConsumer(ringBuffer);
			consumer.pullData();
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//LogFactory.getLogger().info("shutdown..");
					disruptor.shutdown();
					builder.shutdownHTables();
				}
			}));
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			disruptor.shutdown();
			builder.shutdownHTables();
		}
	}
}
