package com.tw.netty.http.server;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import io.netty.handler.codec.http.multipart.MemoryAttribute;
import io.netty.util.ReferenceCountUtil;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {

	private HttpRequest request;

	private FullHttpResponse response;

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
			String res = "test";
			response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
					HttpResponseStatus.OK, Unpooled.wrappedBuffer(res
							.getBytes("UTF-8")));

			Map<String, String> requestParams = new HashMap<>();
			// 处理get请求
			if (request.method() == HttpMethod.GET) {
				QueryStringDecoder decoder = new QueryStringDecoder(
						request.uri());
				Map<String, List<String>> parame = decoder.parameters();
				Iterator<Entry<String, List<String>>> iterator = parame
						.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, List<String>> next = iterator.next();
					requestParams.put(next.getKey(), next.getValue().get(0));
				}
			}
			// 处理POST请求
			if (request.method() == HttpMethod.POST) {
				HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(
						new DefaultHttpDataFactory(false), request);
				List<InterfaceHttpData> postData = decoder.getBodyHttpDatas(); //
				for (InterfaceHttpData data : postData) {
					if (data.getHttpDataType() == HttpDataType.Attribute) {
						MemoryAttribute attribute = (MemoryAttribute) data;
						requestParams.put(attribute.getName(),
								attribute.getValue());
					}
				}
			}
			// String string = request.headers().get("Cookie");
			// System.out.println(string);
			setHeaders(response);
			// 写给客户端
			ctx.writeAndFlush(response);
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

}
