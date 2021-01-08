package com.tw.server;

import java.io.IOException;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.tw.config.Config;
import com.tw.log.LogFactory;

public abstract class AbstractServer implements Server {

	private int maxThreads = Config.getMaxThreads();
	private int serverPort = Config.getServerport();

	private final NioEventLoopGroup parentGroup = new NioEventLoopGroup(maxThreads);
	private final NioEventLoopGroup childGroup = new NioEventLoopGroup(maxThreads);
	private final ServerBootstrap server = new ServerBootstrap();

	@Override
	public void start() {
		// TODO Auto-generated method stub
		try {
			server.option(ChannelOption.ALLOCATOR,
					PooledByteBufAllocator.DEFAULT)
					.childOption(ChannelOption.ALLOCATOR,
							PooledByteBufAllocator.DEFAULT)
					.channel(NioServerSocketChannel.class)
					.group(parentGroup, childGroup);
			init(server);
			ChannelFuture channel = server.bind(serverPort).sync();
			registerShutDownHook();
			LogFactory.getLogger(ProtocolServer.class).info("server started..");
			System.out.println("press enter to shutdown.");
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
			channel.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void close() {
		subClose();
		parentGroup.shutdownGracefully();
		childGroup.shutdownGracefully();
	};

	// 注册关机hook,清理线程
	private void registerShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				close();
				LogFactory.getLogger(ProtocolServer.class).info("shutdown..");
			}
		}));
	}

	public abstract void subClose();

	public abstract void init(ServerBootstrap server);
}
