package com.tw.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tw.client.handler.ClientHandler;

public class NettyPerformanceTest {
	
	private static int count = 200;
	private static CountDownLatch latch = new CountDownLatch(count);
	private static int i = 0;
	
	public static class PerformanceTestThread implements Runnable {
		private NioEventLoopGroup worker = new NioEventLoopGroup();
		private void start() {
			// TODO Auto-generated method stub
			Bootstrap client = new Bootstrap();
			client.channel(NioSocketChannel.class).group(worker)
					.handler(new ClientHandler());
			try {
				for(; i < count; i++){
					ChannelFuture future = client.connect("localhost", 10000)
							.sync();
					Channel channel = future.channel();
					channel.writeAndFlush(Unpooled.copiedBuffer(String.valueOf(i)
							.getBytes()));
					//channel.closeFuture().sync();
					latch.countDown();
				}
				worker.shutdownGracefully();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			start();
			latch.countDown();
		}
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		ExecutorService ex = Executors.newCachedThreadPool();
		ex.execute(new PerformanceTestThread());
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println((startTime - endTime) / 1000 + " seconds");
		ex.shutdown();
	}
}
