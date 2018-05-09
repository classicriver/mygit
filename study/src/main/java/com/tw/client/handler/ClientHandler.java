package com.tw.client.handler;

import java.util.concurrent.locks.ReentrantLock;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler;

@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	
	//private ReentrantLock lock = new ReentrantLock();
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		ctx.close();
		super.exceptionCaught(ctx, cause);
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg)
			throws Exception {
		// TODO Auto-generated method stub
		//lock.lock();
		byte[] data = new byte[msg.readableBytes()];
		msg.readBytes(data);
		//System.out.println("Client：" + new String(data).trim());
		ctx.close();
		//lock.unlock();
	}

}
