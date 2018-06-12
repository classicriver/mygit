package com.tw.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tw.client.handler.ClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client implements Runnable {

	private NioEventLoopGroup worker = new NioEventLoopGroup();
	private static int i = 0;
	private static CountDownLatch latch = new CountDownLatch(1000);

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Bootstrap client = new Bootstrap();
		client.channel(NioSocketChannel.class).group(worker)
				.handler(new ClientHandler());
		try {
			ChannelFuture future = client.connect("106.14.8.149", 8099).sync();
			Channel channel = future.channel();
			channel.writeAndFlush(Unpooled.copiedBuffer(String.valueOf(i)
					.getBytes()));
			channel.closeFuture().sync();
			worker.shutdownGracefully();
			latch.countDown();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		ExecutorService ex = Executors.newCachedThreadPool();
		ex.execute(new Client());
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println((startTime - endTime)/1000 +" seconds");
		ex.shutdown();
	}
}
