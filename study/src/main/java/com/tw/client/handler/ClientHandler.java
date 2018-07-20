package com.tw.client.handler;

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

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("aaaaaaa");
		super.channelActive(ctx);
	}

}
