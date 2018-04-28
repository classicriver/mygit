/**   
*/ 
package com.tw.netty;

//import org.apache.coyote.UpgradeProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Description: TODO
* @author  xiesc
* @date 2018年2月26日 
* @version V1.0   
 */
public class ClientHandler extends ChannelHandlerAdapter{
	
	private final ByteBuf message;
	
	ClientHandler(){
		byte[] req = "test".getBytes();
		message = Unpooled.buffer(req.length);
		message.writeBytes(req);
	}
	
	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.writeAndFlush(message);
		ctx.close();
		//super.channelActive(ctx);
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		super.channelRead(ctx, msg);
	}
	
}
