/**   
*/ 
package com.tw.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Description: TODO
* @author  xiesc
* @date 2018年2月24日 
* @version V1.0   
 */
public class Client implements Runnable{
	
	public void run() {
		NioEventLoopGroup worker = new NioEventLoopGroup();
		try {
			Bootstrap client = new Bootstrap();
			client.group(worker)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					// TODO Auto-generated method stub
					ch.pipeline().addLast(new ClientHandler());
				}
				
				
			});
			ChannelFuture f = client.connect("127.0.0.1", 10001).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			worker.shutdownGracefully();
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService pool = Executors.newCachedThreadPool();
		
		//for(int i = 0 ; i < 1000;i++) {
			Client c = new Client();
			pool.execute(c);
		//}
			Thread.sleep(1000);
			pool.shutdown();
			System.exit(0);
		
	}
}
