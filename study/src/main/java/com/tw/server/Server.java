package com.tw.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.tw.log.LogFactory;
import com.tw.server.hanlder.ServerInitializer;

public class Server {
	
	private ConcurrentLinkedQueue<Object> queue = new ConcurrentLinkedQueue<Object>();
	private NioEventLoopGroup parentGroup = new NioEventLoopGroup();
	private NioEventLoopGroup childGroup = new NioEventLoopGroup();
	
	
	public void start(){
		try {
			ServerBootstrap server = new ServerBootstrap();
			server.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
			.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
			.channel(NioServerSocketChannel.class)
			.group(parentGroup, childGroup)
			.childHandler(new ServerInitializer(queue));
			
			ChannelFuture channel = server.bind(10000).sync();
			LogFactory.getLogger(Server.class).info("server started..");
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					parentGroup.shutdownGracefully();
					childGroup.shutdownGracefully();
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
		}
	}
	
	public static void main(String[] args) {
		new Server().start();
	}
}
