package com.tw.consumer.http.server;

import com.tw.consumer.core.AutoShutdown;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
/**
 * 
 * @author xiesc
 * @TODO web服务器
 * @time 2018年8月31日
 * @version 1.0
 */
public class HttpServer implements AutoShutdown{

	private final NioEventLoopGroup parentGroup = new NioEventLoopGroup(1);
	private final NioEventLoopGroup childGroup = new NioEventLoopGroup(1);
	private final ServerBootstrap server = new ServerBootstrap();
	
	public void start() {
		server.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new HttpResponseEncoder());
						ch.pipeline().addLast(new HttpRequestDecoder());
						ch.pipeline().addLast(new HttpServerHandler());
					}
				}).option(ChannelOption.SO_BACKLOG, 1024)
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				.group(parentGroup, childGroup);
		try {
			ChannelFuture channel = server.bind(9901).sync();
			channel.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		parentGroup.shutdownGracefully();
		childGroup.shutdownGracefully();
	};
}
