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

import com.tw.analysizer.Analysizer;
import com.tw.config.Config;
import com.tw.log.LogFactory;
import com.tw.server.hanlder.ServerInitializer;
/**
 * 
 * @author xiesc
 * @TODO
 * @time 2018年5月2日
 */
public class ProtocolServer {

	private int maxThreads = Config.getMaxThreads();
	private ExecutorService workers = Executors.newFixedThreadPool(maxThreads,
			new ThreadFactory() {
				AtomicInteger atomic = new AtomicInteger();
				public Thread newThread(Runnable r) {
					return new Thread(r, "AnalysizerThread:"
							+ this.atomic.getAndIncrement());
				}
			});
	private Boolean running = new Boolean(true);// 业务线程开关
	private BlockingQueue<String> protocolsQueue = new LinkedBlockingQueue<String>();// 协议队列

	private NioEventLoopGroup parentGroup = new NioEventLoopGroup();
	private NioEventLoopGroup childGroup = new NioEventLoopGroup();

	public void start() {
		try {
			ServerBootstrap server = new ServerBootstrap();
			server.option(ChannelOption.ALLOCATOR,
					PooledByteBufAllocator.DEFAULT)
					.childOption(ChannelOption.ALLOCATOR,
							PooledByteBufAllocator.DEFAULT)
					.channel(NioServerSocketChannel.class)
					.group(parentGroup, childGroup);
					//.childHandler(new ServerInitializer(protocolsQueue));
			ChannelFuture channel = server.bind(10000).sync();
			LogFactory.getLogger(ProtocolServer.class).info("server started..");
			/*// 启动协议解析线程
			for (int i = 0; i < maxThreads; i++) {
				workers.execute(new Analysizer(protocolsQueue, running));
			}*/
			// 注册关机hook,清理线程
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					parentGroup.shutdownGracefully();
					childGroup.shutdownGracefully();
					running = false;
					workers.shutdown();
					LogFactory.getLogger(ProtocolServer.class).info("shutdown..");
				}
			}));
			System.out.println("press enter to shutdown.");
			System.in.read();
			System.exit(0);
			channel.channel().closeFuture().sync();
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ProtocolServer().start();
	}
}
