package com.tw.consumer.server;

import java.util.concurrent.ThreadFactory;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tw.consumer.core.SingleBeanFactory;
import com.tw.consumer.core.ThreadFactoryBean;
import com.tw.consumer.disruptor.EventExceptionHandler;
import com.tw.consumer.disruptor.MMessageEventFactory;
import com.tw.consumer.disruptor.WorkHandlerBuilder;
import com.tw.consumer.http.server.HttpServer;
import com.tw.consumer.kfk.KfkConsumerFactory;
import com.tw.consumer.log.LogFactory;
import com.tw.consumer.model.OriginMessage;
import com.tw.consumer.redis.RedisThreadFactory;

public class Server {
	//环形队列
	private RingBuffer<OriginMessage> ringBuffer;
	private Disruptor<OriginMessage> disruptor;
	//disruptor线程工厂
	private final ThreadFactory workers = ThreadFactoryBean.getThreadFactory("AnalysizerThread: ");

	public void start() {
		try {	
			// 创建bufferSize ,也就是RingBuffer大小，必须是8的N次方
			final int bufferSize = 128;
			disruptor = new Disruptor<OriginMessage>(new MMessageEventFactory(), bufferSize, workers,
					ProducerType.MULTI, new BlockingWaitStrategy());
			disruptor.setDefaultExceptionHandler(new EventExceptionHandler());
			disruptor.handleEventsWithWorkerPool(new WorkHandlerBuilder().build());
			ringBuffer = disruptor.start();
			//启动kafka consumer线程
			SingleBeanFactory.getBean(KfkConsumerFactory.class,new Class[]{RingBuffer.class},new Object[]{ringBuffer}).startConsumers();
			//开启redis线程
			SingleBeanFactory.getBean(RedisThreadFactory.class).start();
			//注册关机hook
			registerShutDownHook();
			LogFactory.getLogger().info("server started.....");
			//启动http服务，监听关闭请求
			SingleBeanFactory.getBean(HttpServer.class).start();;
		} catch (Exception e) {
			e.printStackTrace();
			LogFactory.getLogger().error("exception happened.",e);
		}
	}

	public void close() {
		disruptor.shutdown();
		SingleBeanFactory.autoShutdown();
		LogFactory.getLogger().info("server shutdown success.");
		//主线程等5秒，让其他线程完成关闭工作.
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//关机hook
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
