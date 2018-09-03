package com.tw.consumer.server;

import java.util.concurrent.ThreadFactory;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tw.consumer.core.ThreadFactoryBean;
import com.tw.consumer.disruptor.EventExceptionHandler;
import com.tw.consumer.disruptor.MMessageEventFactory;
import com.tw.consumer.disruptor.WorkHandlerBuilder;
import com.tw.consumer.hbase.HbaseClientManager;
import com.tw.consumer.http.server.HttpServer;
import com.tw.consumer.log.LogFactory;
import com.tw.consumer.model.MMessage;
import com.tw.consumer.mq.KfkConsumerFactory;

public class Server {
	//环形队列
	private RingBuffer<MMessage> ringBuffer;
	private Disruptor<MMessage> disruptor;
	private KfkConsumerFactory consumerFactory;
	private HttpServer httpServer;
	//disruptor线程工厂
	private final ThreadFactory workers = ThreadFactoryBean.getThreadFactory("AnalysizerThread: ");

	public void start() {
		try {
			// 创建工厂
			final EventFactory<MMessage> factory = new MMessageEventFactory();
			// 创建bufferSize ,也就是RingBuffer大小，必须是8的N次方
			final int bufferSize = 1024 * 1024 * 8;
			disruptor = new Disruptor<MMessage>(factory, bufferSize, workers,
					ProducerType.MULTI, new BlockingWaitStrategy());
			disruptor.setDefaultExceptionHandler(new EventExceptionHandler());
			disruptor.handleEventsWithWorkerPool(new WorkHandlerBuilder().build());
			ringBuffer = disruptor.start();
			//启动kafka consumer线程
			consumerFactory = new KfkConsumerFactory(ringBuffer);
			consumerFactory.startConsumers();
			registerShutDownHook();
			LogFactory.getLogger().info("server started.....");
			//启动http服务，监听关闭请求
			httpServer = new HttpServer();
			httpServer.start();
		} catch (Exception e) {
			e.printStackTrace();
			LogFactory.getLogger().error("exception happened.",e.getMessage());
		}
	}

	public void close() {
		consumerFactory.shutdown();
		HbaseClientManager.getInstance().close();
		disruptor.shutdown();
		httpServer.close();
		LogFactory.getLogger().info("server shutdown success.");
		//主线程等5秒，让其他线程完成关闭工作.
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//注册关机hook
	private void registerShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				close();
			}
		}));
	}
}
