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
import com.tw.ddcs.http.server.HttpServer;
import com.tw.ddcs.log.LogFactory;
import com.tw.ddcs.model.OriginMessage;
import com.tw.ddcs.mqtt.MqttService;
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
	//环形队列
	private RingBuffer<OriginMessage> ringBuffer;
	private Disruptor<OriginMessage> disruptor;
	private MqttService mqtt;
	private final QuartzScheduler scheduler = QuartzScheduler.getInstance();
	private HttpServer httpServer ;
	// 线程工厂
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
		EventFactory<OriginMessage> factory = new MessageEventFactory();
		// RingBuffer 数组大小，必须是2的N次方,设置太大会内存溢出
		int ringBufferSize = 512;
		disruptor = new Disruptor<OriginMessage>(factory, ringBufferSize, workers,
				ProducerType.MULTI, new BlockingWaitStrategy());
		disruptor.setDefaultExceptionHandler(new IntEventExceptionHandler());
		disruptor.handleEventsWithWorkerPool(WorkHandlerBuilder
				.build(DdcsConfig.getInstance().getMaxThreads()));
		ringBuffer = disruptor.start();
		// 启动mqtt客户端服务
		mqtt = new MqttService(ringBuffer);
		mqtt.start();
		// 启动mysql定时提交调度
		scheduler.createRepeatSecondScheduler("mysqlJob",
				"mysqlTrigger", DdcsConfig.getInstance().getRepeatInterval(),
				MysqlSubmitJob.class);
		// 注册关机hook
		registerShutDownHook();
		LogFactory.getLogger().info("----> server started.");
		//启动http服务,监听关闭请求
		httpServer = new HttpServer();
		httpServer.start();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		disruptor.shutdown();
		mqtt.getMqttClient().close();
		scheduler.close();
		httpServer.close();
		//等待1秒再关闭
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
