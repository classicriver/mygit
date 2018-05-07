package com.tw.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tw.analysizer.Analysizer;
import com.tw.config.Config;
import com.tw.disruptor.ClearingEventHandler;
import com.tw.disruptor.IntEventExceptionHandler;
import com.tw.disruptor.ProtocolEventFactory;
import com.tw.disruptor.ProtocolEventHandler;
import com.tw.disruptor.ProtocolWorkHandler;
import com.tw.log.LogFactory;
import com.tw.model.Protocol;
import com.tw.server.hanlder.ServerInitializer;

public class ServerWithDisruptor {
	/*private ThreadFactory workers = new ThreadFactory() {
		AtomicInteger atomic = new AtomicInteger();

		public Thread newThread(Runnable r) {
			return new Thread(r, "AnalysizerThread:"
					+ this.atomic.getAndIncrement());
		}
	};*/
	
	private int maxThreads = Config.getMaxThreads();
	private ExecutorService workers = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
			new ThreadFactory() {
				AtomicInteger atomic = new AtomicInteger();
				public Thread newThread(Runnable r) {
					return new Thread(r, "AnalysizerThread:"
							+ this.atomic.getAndIncrement());
				}
			});
	
	// private Boolean running = new Boolean(true);// 业务线程开关
	private RingBuffer<Protocol> ringBuffer;
	//private Disruptor<Protocol> disruptor;
	// private BlockingQueue<String> protocolsQueue = new
	// LinkedBlockingQueue<String>();// 协议队列

	private NioEventLoopGroup parentGroup = new NioEventLoopGroup();
	private NioEventLoopGroup childGroup = new NioEventLoopGroup();

	public void start() {
		try {
		/*	// 启动协议解析线程   单线程
			// RingBuffer 大小，必须是 2 的 N 次方     
			int ringBufferSize = 1024 * 1024;
			// Construct the Disruptor
			disruptor = new Disruptor<>(new ProtocolEventFactory(), ringBufferSize, workers);
			disruptor.handleEventsWith(new ProtocolEventHandler()).then(
					new ClearingEventHandler());// 连接handler
			// 启动disruptor
			disruptor.start();
			// 从disruptor中获得ringBuffer用于发布
			ringBuffer = disruptor.getRingBuffer();
			*/
			
			/**
			 * 多线程
			 */
			// 创建工厂
			EventFactory<Protocol> factory = new EventFactory<Protocol>() {
				@Override
				public Protocol newInstance() {
					return new Protocol();
				}
			};
			// 创建bufferSize ,也就是RingBuffer大小，必须是2的N次方
			int ringBufferSize = 1024 * 1024; //
			WaitStrategy YIELDING_WAIT = new BlockingWaitStrategy();
			// 创建ringBuffer
			ringBuffer = RingBuffer.create(ProducerType.MULTI, factory, ringBufferSize, YIELDING_WAIT);
			SequenceBarrier barriers = ringBuffer.newBarrier();
			// 创建10个消费者来处理同一个生产者发的消息(这10个消费者不重复消费消息)
			ProtocolWorkHandler[] handlers = new ProtocolWorkHandler[Runtime.getRuntime().availableProcessors()];
			for (int i = 0; i < handlers.length; i++) {
				handlers[i] = new ProtocolWorkHandler();
			}
			
			WorkerPool<Protocol> workerPool = new WorkerPool<Protocol>(ringBuffer, barriers,
					new IntEventExceptionHandler(), handlers);

			ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
			workerPool.start(workers);
			
			
			
			ServerBootstrap server = new ServerBootstrap();
			server.option(ChannelOption.ALLOCATOR,
					PooledByteBufAllocator.DEFAULT)
					.childOption(ChannelOption.ALLOCATOR,
							PooledByteBufAllocator.DEFAULT)
					.channel(NioServerSocketChannel.class)
					.group(parentGroup, childGroup)
					.childHandler(new ServerInitializer(ringBuffer));
			ChannelFuture channel = server.bind(10000).sync();
			LogFactory.getLogger(Server.class).info("server started..");

			// 注册关机hook,清理线程
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					parentGroup.shutdownGracefully();
					childGroup.shutdownGracefully();
					// running = false;
					//disruptor.shutdown();
					workers.shutdown();
					LogFactory.getLogger(Server.class).info("shutdown..");
				}
			}));
			System.out.println("press enter to shutdown.");
			System.in.read();
			System.exit(0);
			channel.channel().closeFuture().sync();
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
			// running = false;
			//disruptor.shutdown();
			//workers.shutdown();
			LogFactory.getLogger(Server.class).info("shutdown..");
		}
	}

	public static void main(String[] args) {
		new ServerWithDisruptor().start();
	}
}
