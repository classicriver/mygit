package com.tw.consumer.http.server;

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
/**
 * 
 * @author xiesc
 * @TODO  http请求处理类
 * @time 2018年8月31日
 * @version 1.0
 */
public class HttpServerHandler extends ChannelInboundHandlerAdapter {

	private HttpRequest request;
	/**
	 * 监听请求，如果是close就关闭JVM
	 */
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
			//Map<String, String> requestParams = new HashMap<>();
			// 处理get请求
			if (request.method() == HttpMethod.GET) {
				if("/close".equals(uri.getPath())){
					FullHttpResponse response = getResponse("success");
					setHeaders(response);
					// 写给客户端
					ctx.writeAndFlush(response);
					System.exit(0);
				}else{
					FullHttpResponse response = getResponse("unsupported request");
					setHeaders(response);
					ctx.writeAndFlush(response);
				}
				/*QueryStringDecoder decoder = new QueryStringDecoder(
						request.uri());
				Map<String, List<String>> parame = decoder.parameters();
				Iterator<Entry<String, List<String>>> iterator = parame
						.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, List<String>> next = iterator.next();
					requestParams.put(next.getKey(), next.getValue().get(0));
				}*/
			}
			// 处理POST请求
			/*if (request.method() == HttpMethod.POST) {
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
			}*/
			// String string = request.headers().get("Cookie");
			// System.out.println(string);
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

