package com.tw.consumer.server;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tw.consumer.config.Config;
import com.tw.consumer.core.SingleBeanFactory;
import com.tw.consumer.core.ThreadFactoryBean;
import com.tw.consumer.device.executor.DeveiceExecutor;
import com.tw.consumer.disruptor.EventExceptionHandler;
import com.tw.consumer.disruptor.MMessageEventFactory;
import com.tw.consumer.disruptor.WorkHandlerFactory;
import com.tw.consumer.http.server.HttpServer;
import com.tw.consumer.kfk.KfkConsumerFactory;
import com.tw.consumer.log.LogFactory;
import com.tw.consumer.model.GenericDeviceModel;
import com.tw.consumer.model.OriginMessage;
import com.tw.consumer.redis.RedisThreadFactory;
import com.tw.consumer.zookeeper.watcher.KylinMonitor;

public class Server {
	// 环形队列
	private RingBuffer<OriginMessage> ringBuffer;
	private Disruptor<OriginMessage> disruptor;
	// RingBuffer大小，必须是2的N次方
	private final int bufferSize = Config.getInstance().getMaxRingBuffer();
	// 避免kafka consumer消费累积消息时速度过快导致的内存溢出
	private final Semaphore semaphore = new Semaphore(bufferSize);
	//
	private final LinkedBlockingQueue<GenericDeviceModel> queue = new LinkedBlockingQueue<GenericDeviceModel>(
			Config.getInstance().getMaxRingBuffer() * 2);
	// disruptor线程工厂
	private final ThreadFactory workers = ThreadFactoryBean
			.getThreadFactory("AnalysizerThread: ");
	private final KylinMonitor monitor = new KylinMonitor();

	public void start() {
		try {
			disruptor = new Disruptor<OriginMessage>(
					new MMessageEventFactory(), bufferSize, workers,
					ProducerType.MULTI, new BlockingWaitStrategy());
			disruptor.setDefaultExceptionHandler(new EventExceptionHandler());
			disruptor.handleEventsWithWorkerPool(new WorkHandlerFactory(
					semaphore, queue).getHandlers());
			ringBuffer = disruptor.start();
			// 启动kafka consumer线程
			SingleBeanFactory.getBean(KfkConsumerFactory.class,
					new Class[] { RingBuffer.class, Semaphore.class },
					new Object[] { ringBuffer, semaphore }).startConsumers();
			// 启动设备处理线程
			SingleBeanFactory.getBean(DeveiceExecutor.class,
					new Class[] { LinkedBlockingQueue.class },
					new Object[] { queue }).start();
			// 开启redis线程
			SingleBeanFactory.getBean(RedisThreadFactory.class).start();
			//开启kylin自动build调度
			monitor.init();
			// 注册关机hook
			registerShutDownHook();
			LogFactory.getLogger().info("server started.....");
			// 启动http服务，监听关闭请求
			SingleBeanFactory.getBean(HttpServer.class).start();
		} catch (Exception e) {
			e.printStackTrace();
			LogFactory.getLogger().error("exception happened.", e);
		}
	}

	public void close() {
		disruptor.shutdown();
		// kylinScheduler.shutdown();
		SingleBeanFactory.autoShutdown();
		LogFactory.getLogger().info("server shutdown success.");
		// 主线程等10秒，让其他线程完成关闭工作.
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 关机hook
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
