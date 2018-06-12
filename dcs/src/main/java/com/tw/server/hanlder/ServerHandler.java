package com.tw.server.hanlder;

import com.lmax.disruptor.RingBuffer;
import com.tw.model.Protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
/**
 * 
 * @author xiesc
 * @TODO channelhandler
 * @time 2018年5月14日
 * @version 1.0
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

	//disruptor queue
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
		ByteBuf buf = (ByteBuf) msg;
		byte[] data = new byte[buf.readableBytes()];
		buf.readBytes(data);
		String request = new String(data, "utf-8");
		// 写给客户端
		String response = "我是反馈的信息";
		ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
		long sequence = ringBuffer.next();
		try {
			 // Get the entry in the Disruptor for the sequence
			Protocol event = ringBuffer.get(sequence);
			// Fill with data
			event.setMessage(request); 
		} finally {
			ringBuffer.publish(sequence);
		}
		//.addListener(ChannelFutureListener.CLOSE);
		ReferenceCountUtil.release(msg);
	}

}
