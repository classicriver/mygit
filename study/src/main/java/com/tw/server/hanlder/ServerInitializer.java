package com.tw.server.hanlder;

import java.util.concurrent.BlockingQueue;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * 
 * @author pc
 * 
 */
public class ServerInitializer extends ChannelInitializer<Channel> {

	private BlockingQueue<String> queue;

	public ServerInitializer(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		// TODO Auto-generated method stub
		ch.pipeline().addLast(new ServerHandler(queue));
	}

}
