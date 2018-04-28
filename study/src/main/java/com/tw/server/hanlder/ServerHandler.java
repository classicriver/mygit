package com.tw.server.hanlder;

import java.util.concurrent.ConcurrentLinkedQueue;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {

	private ConcurrentLinkedQueue<Object> queue;

	public ServerHandler(ConcurrentLinkedQueue<Object> queue) {
		// TODO Auto-generated constructor stub
		this.queue = queue;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		queue.add(msg);
		super.channelRead(ctx, msg);
	}

}
