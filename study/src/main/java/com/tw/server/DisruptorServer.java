package com.tw.server;

import io.netty.bootstrap.ServerBootstrap;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tw.disruptor.ClearingEventHandler;
import com.tw.disruptor.IntEventExceptionHandler;
import com.tw.disruptor.ProtocolEventFactory;
import com.tw.disruptor.ProtocolEventHandler;
import com.tw.model.Protocol;
import com.tw.server.hanlder.ServerInitializer;

public class DisruptorServer extends AbstractServer {

	private RingBuffer<Protocol> ringBuffer;
	private Disruptor<Protocol> disruptor;
	private ThreadFactory workers = new ThreadFactory() {
		AtomicInteger atomic = new AtomicInteger();

		public Thread newThread(Runnable r) {
			return new Thread(r, "AnalysizerThread:"
					+ this.atomic.getAndIncrement());
		}
	};
	
	@Override
	public void init(ServerBootstrap server) {
		// TODO Auto-generated method stub
		startDisruptor();
		server.childHandler(new ServerInitializer(ringBuffer));
		
	}
	
	@SuppressWarnings("unchecked")
	private void startDisruptor() {
		// 创建工厂
		EventFactory<Protocol> factory = new ProtocolEventFactory();
		// 创建bufferSize ,也就是RingBuffer大小，必须是2的N次方
		int ringBufferSize = 1024 * 1024 * 8;
		
		disruptor = new Disruptor<Protocol>(factory, ringBufferSize, workers,
				ProducerType.MULTI, new BlockingWaitStrategy());
		disruptor.setDefaultExceptionHandler(new IntEventExceptionHandler());
		disruptor.handleEventsWith(new ProtocolEventHandler()).then(
				new ClearingEventHandler());
		ringBuffer = disruptor.getRingBuffer();
		disruptor.start();
	}

	@Override
	public void subClose() {
		// TODO Auto-generated method stub
		disruptor.shutdown();
	}

	public static void main(String[] args) {
		new DisruptorServer().start();
	}

}
