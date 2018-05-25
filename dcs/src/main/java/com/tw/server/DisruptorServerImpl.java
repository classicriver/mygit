package com.tw.server;

import io.netty.bootstrap.ServerBootstrap;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tw.analysizer.DefaultAnalysizerImpl;
import com.tw.config.Config;
import com.tw.disruptor.IntEventExceptionHandler;
import com.tw.disruptor.ProtocolEventFactory;
import com.tw.disruptor.ProtocolWorkHandler;
import com.tw.model.Protocol;
import com.tw.mq.client.RocketMQClientImpl;
import com.tw.server.hanlder.ServerInitializer;

/**
 * @author xiesc
 * @TODO 带disruptor的netty
 * @time 2018年5月14日
 * @version 1.0
 */
public class DisruptorServerImpl extends AbstractServer {

	private RingBuffer<Protocol> ringBuffer;
	private Disruptor<Protocol> disruptor;
	/**
	 * 分析器线程工厂
	 */
	private ThreadFactory workers = new ThreadFactory() {
		private AtomicInteger atomic = new AtomicInteger();
		public Thread newThread(Runnable r) {
			return new Thread(r, "AnalysizerThread:"
					+ this.atomic.getAndIncrement());
		}
	};
	/**
	 * 如果连不上消息服务器，把数据放到队列中，序列化到本地磁盘保存
	 */
	private ConcurrentLinkedQueue<String> serializeQueue = new ConcurrentLinkedQueue<String>();

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

		ProtocolWorkHandler[] handlers = new ProtocolWorkHandler[Config
				.getMaxThreads()];
		for (int i = 0; i < Config.getMaxThreads(); i++) {
			handlers[i] = new ProtocolWorkHandler(new DefaultAnalysizerImpl(),
					new RocketMQClientImpl(serializeQueue));
		}
		// disruptor.handleEventsWith(handlers);每个handler会单独建立一个线程，链式handler，ringbuff的每条数据都会被所有handler处理
		// 每个handler会单独建立一个线程，并发消费ringbuff的数据
		disruptor.handleEventsWithWorkerPool(handlers);
		ringBuffer = disruptor.getRingBuffer();
		disruptor.start();
	}

	@Override
	public void subClose() {
		// TODO Auto-generated method stub
		disruptor.shutdown();
		RocketMQClientImpl.close();
	}

}
