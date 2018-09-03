package com.tw.ddcs.http.server;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.ReferenceCountUtil;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {

	private HttpRequest request;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		if (msg instanceof HttpRequest) {
			request = (HttpRequest) msg;
			URI uri = new URI(request.uri());
			if (uri.getPath().equals("/")) {
				return;
			}
			// 处理get请求
			if (request.method() == HttpMethod.GET) {
				if("/close".equals(uri.getPath())){
					FullHttpResponse response = getResponse("success");
					setHeaders(response);
					// 写给客户端
					ctx.writeAndFlush(response);
					System.exit(0);
				}else{
					FullHttpResponse response = getResponse("unknow request");
					setHeaders(response);
					ctx.writeAndFlush(response);
				}
			}
		}
		ReferenceCountUtil.release(msg);
	}

	/**
	 * 设置HTTP返回头信息
	 */
	private void setHeaders(FullHttpResponse response) {
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html");
		response.headers().set(HttpHeaderNames.CONTENT_LANGUAGE,
				response.content().readableBytes());
		response.headers().set(HttpHeaderNames.CONTENT_LENGTH,
				response.content().readableBytes());
		if (HttpUtil.isKeepAlive(request)) {
			response.headers().set(HttpHeaderNames.CONNECTION,
					HttpHeaderValues.KEEP_ALIVE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}
	
	private FullHttpResponse getResponse(String message) throws UnsupportedEncodingException{
		return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
				HttpResponseStatus.OK, Unpooled.wrappedBuffer(message
						.getBytes("UTF-8")));
	}
}

