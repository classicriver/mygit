package com.tw.server;

import java.io.IOException;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.tw.log.LogFactory;
import com.tw.resources.ConfigProperties;
/**
 * 
 * @author xiesc
 * @TODO netty服务抽象类
 * @time 2018年5月14日
 * @version 1.0
 */
public abstract class AbstractNettyServer implements Server {

	private final ConfigProperties config = ConfigProperties.getInstance();
	private final NioEventLoopGroup parentGroup = new NioEventLoopGroup();
	private final NioEventLoopGroup childGroup = new NioEventLoopGroup();
	private final ServerBootstrap server = new ServerBootstrap();

	@Override
	public void start() {
		// TODO Auto-generated method stub
		try {
			server.option(ChannelOption.ALLOCATOR,   
					PooledByteBufAllocator.DEFAULT)  
					.childOption(ChannelOption.ALLOCATOR,
							PooledByteBufAllocator.DEFAULT)//开启内存池
					.channel(NioServerSocketChannel.class)
					.childOption(ChannelOption.TCP_NODELAY, true) //禁用Nagle算法提高响应速度
					.group(parentGroup, childGroup);
			init(server);
			ChannelFuture channel = server.bind(config.getServerPort()).sync();
			LogFactory.getLogger().info("----> server started....");
			registerShutDownHook();
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
				LogFactory.getLogger().info("shutdown..");
			}
		}));
	}

	protected abstract void subClose();

	protected abstract void init(ServerBootstrap server);
}
