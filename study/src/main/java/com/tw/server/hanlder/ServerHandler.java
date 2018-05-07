package com.tw.server.hanlder;

import java.util.concurrent.BlockingQueue;

import com.lmax.disruptor.RingBuffer;
import com.tw.model.Protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ServerHandler extends ChannelHandlerAdapter {

	private final RingBuffer<Protocol> ringBuffer;

	public ServerHandler(RingBuffer<Protocol> ringBuffer) {
		// TODO Auto-generated constructor stub
		this.ringBuffer = ringBuffer;
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
		// do something msg
		ByteBuf buf = (ByteBuf) msg;
		byte[] data = new byte[buf.readableBytes()];
		buf.readBytes(data);
		String request = new String(data, "utf-8");
		//System.out.println("Server: " + request);
		// 写给客户端
		String response = "我是反馈的信息";
		ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
		// queue.put(request);
		long sequence = ringBuffer.next();
		try {
			 // Get the entry in the Disruptor for the sequence
			Protocol event = ringBuffer.get(sequence);
			event.setValue(request); // Fill with data
		} finally {
			ringBuffer.publish(sequence);
		}
		// .addListener(ChannelFutureListener.CLOSE);
		ReferenceCountUtil.release(msg);
		// super.channelRead(ctx, msg);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
	}

}
