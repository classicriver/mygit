package com.tw.netty.http.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import com.tw.resources.ConfigProperties;

public class HttpServer {

	private final NioEventLoopGroup parentGroup = new NioEventLoopGroup(
			ConfigProperties.getInstance().getMaxThreads());
	private final NioEventLoopGroup childGroup = new NioEventLoopGroup(
			ConfigProperties.getInstance().getMaxThreads());
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
	public static void main(String[] args) {
		new HttpServer().start();
	}
}
