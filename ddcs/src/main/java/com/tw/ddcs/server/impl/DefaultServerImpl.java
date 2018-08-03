package com.tw.ddcs.server.impl;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tw.ddcs.config.DdcsConfig;
import com.tw.ddcs.disruptor.IntEventExceptionHandler;
import com.tw.ddcs.disruptor.MessageEventFactory;
import com.tw.ddcs.disruptor.WorkHandlerBuilder;
import com.tw.ddcs.log.LogFactory;
import com.tw.ddcs.model.Message;
import com.tw.ddcs.mqtt.MqttClient;
import com.tw.ddcs.mqtt.impl.DefaultClientImpl;
import com.tw.ddcs.quartz.QuartzScheduler;
import com.tw.ddcs.quartz.job.MysqlSubmitJob;
import com.tw.ddcs.server.Server;
/**
 * 
 * @author xiesc
 * @TODO 服务器实现类
 * @time 2018年8月2日
 * @version 1.0
 */
public class DefaultServerImpl implements Server {
	/**
	 * 环形队列
	 */
	private RingBuffer<Message> ringBuffer;
	private Disruptor<Message> disruptor;
	private final QuartzScheduler scheduler = new QuartzScheduler();
	private MqttClient mqtt;
	/**
	 *线程工厂
	 */
	private final ThreadFactory workers = new ThreadFactory() {
		private AtomicInteger atomic = new AtomicInteger();
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "DDCS : persistence"
					+ this.atomic.getAndIncrement());
		}
	};

	@Override
	public void start() {
		// TODO Auto-generated method stub
		EventFactory<Message> factory = new MessageEventFactory();
		// RingBuffer size，必须是2的N次方
		int ringBufferSize = 1024 * 1024 * 4;
		disruptor = new Disruptor<Message>(factory, ringBufferSize, workers,
				ProducerType.MULTI, new BlockingWaitStrategy());
		disruptor.setDefaultExceptionHandler(new IntEventExceptionHandler());
		disruptor.handleEventsWithWorkerPool(WorkHandlerBuilder
				.build(DdcsConfig.getInstance().getMaxThreads()));
		ringBuffer = disruptor.start();
		// 启动mqtt客户端服务
		mqtt = new DefaultClientImpl(ringBuffer);
		mqtt.start();
		//启动mysql定时提交调度
		scheduler.startSimpleScheduler("mysqlJob",
				"mysqlTrigger", DdcsConfig.getInstance().getRepeatInterval(),
				MysqlSubmitJob.class);
		// 注册关机hook,清理线程
		registerShutDownHook();
		LogFactory.getLogger().info("----> server started.");
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		disruptor.shutdown();
		mqtt.close();
		
	}
	/**
	 * 关机hook
	 */
	private void registerShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				LogFactory.getLogger().warn("server stop.");
				close();
			}
		}));
	}

}
