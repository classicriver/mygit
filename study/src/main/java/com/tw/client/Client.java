package com.tw.client;

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

	public void start(int i) {
		Bootstrap client = new Bootstrap();
		client.channel(NioSocketChannel.class).group(worker)
				.handler(new ClientHandler());
		try {
			ChannelFuture future = client.connect("127.0.0.1", 10000).sync();
			Channel channel = future.channel();
			channel.writeAndFlush(Unpooled.copiedBuffer(String.valueOf(i)
					.getBytes()));
			channel.closeFuture().sync();
			worker.shutdownGracefully();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		for (; i < 200; i++) {
			Thread t = new Thread(new Client());
			t.start();
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		start(i);
	}
}
