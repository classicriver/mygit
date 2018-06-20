package com.tw.server;

import io.netty.bootstrap.ServerBootstrap;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tw.config.Config;
import com.tw.disruptor.IntEventExceptionHandler;
import com.tw.disruptor.ProtocolEventFactory;
import com.tw.disruptor.WorkHandlerBuilder;
import com.tw.log.LogFactory;
import com.tw.model.Protocol;
import com.tw.mq.producer.MQProducer;
import com.tw.mq.producer.RocketMQProducerImpl;
import com.tw.server.hanlder.ServerInitializer;

/**
 * @author xiesc
 * @TODO 带disruptor的netty
 * @time 2018年5月14日
 * @version 1.0
 */
public class DisruptorServerImpl extends AbstractNettyServer {

	/**
	 * 环形队列
	 */
	private RingBuffer<Protocol> ringBuffer;

	private Disruptor<Protocol> disruptor;

	/**
	 * 消息实现类
	 */
	private MQProducer cilent = new RocketMQProducerImpl();
	/**
	 * Analysizer线程工厂
	 */
	private ThreadFactory workers = new ThreadFactory() {
		private AtomicInteger atomic = new AtomicInteger();

		public Thread newThread(Runnable r) {
			return new Thread(r, "AnalysizerThread: "
					+ this.atomic.getAndIncrement());
		}
	};

	@Override
	public void init(ServerBootstrap server) {
		// TODO Auto-generated method stub
		startDisruptor();
		server.childHandler(new ServerInitializer(ringBuffer));
	}

	private void startDisruptor() {
		// 创建工厂
		EventFactory<Protocol> factory = new ProtocolEventFactory();
		// 创建bufferSize ,也就是RingBuffer大小，必须是2的N次方
		int ringBufferSize = 1024 * 1024 * 8;
		disruptor = new Disruptor<Protocol>(factory, ringBufferSize, workers,
				ProducerType.MULTI, new BlockingWaitStrategy());
		disruptor.setDefaultExceptionHandler(new IntEventExceptionHandler());
		// disruptor.handleEventsWith(handlers);每个handler会单独建立一个线程，链式handler，ringbuff的每条数据都会被所有handler处理
		// disruptor.handleEventsWithWorkerPool(handlers);每个handler会单独建立一个线程，并发消费ringbuff的数据
		disruptor.handleEventsWithWorkerPool(WorkHandlerBuilder.build(
				Config.getMaxThreads(), cilent));
		ringBuffer = disruptor.getRingBuffer();
		disruptor.start();
		LogFactory.getLogger().info("disruptor init.");
	}

	@Override
	public void subClose() {
		// TODO Auto-generated method stub
		disruptor.shutdown();
		cilent.close();
	}

}
