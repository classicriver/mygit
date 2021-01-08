/**   
*/
package com.ptae.zuul.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.ptae.zuul.common.CommonUtils;
import com.ptae.zuul.common.JJWTUtils;
import com.ptae.zuul.config.JedisClient;

/**
 * @Description: TODO 请求过滤器，对转发之前的请求验证令牌
 * @author xiesc
 * @date 2017年10月26日
 * @version V1.0  
 */
@Component
public class TokenFilter extends ZuulFilter {

	@Value("${ptae.zuul.excludePathPatterns}")
	private String excludePathPatterns;

	@Autowired
	private JedisClient jedisClient;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netflix.zuul.IZuulFilter#run()
	 */
	@Override
	public Object run() {
		// TODO Auto-generated method stub
		AntPathMatcher matcher = new AntPathMatcher();
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String url = request.getRequestURI();
		String[] patterns = excludePathPatterns.split(",");
		// 检查当前url，如果不需要验证令牌，直接放行
		for (int i = 0; i < patterns.length; i++) {
			if (matcher.match(patterns[i], url)) {
				success(ctx);
				return null;
			}
		}
		String token = request.getParameter("token");
		if(CommonUtils.isNullOrEmpty(token)) {
			writeFailedMessages(ctx,"{\"code\":400,\"data\":null,\"message\":\"无效请求\"}");
			return null;
		}
		// 从redis中验证用户签名
		String userAccount = JJWTUtils.getSubject(token);
		String value = jedisClient.get(userAccount);
		if (!CommonUtils.isNullOrEmpty(value) && value.equals(token)) {
			success(ctx);
			return null;
		} else {
			writeFailedMessages(ctx,"{\"code\":400,\"data\":null,\"message\":\"令牌验证失败\"}");
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netflix.zuul.IZuulFilter#shouldFilter()
	 */
	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netflix.zuul.ZuulFilter#filterOrder()
	 */
	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netflix.zuul.ZuulFilter#filterType()
	 */
	/**
	 * (1) PRE：这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
	 * (2)ROUTING：这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用Apache HttpClient或NetfilxRibbon请求微服务。 
	 * (3) POST：这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTPHeader、收集统计信息和指标、将响应从微服务发送给客户端等。 
	 * (4) ERROR：在其他阶段发生错误时执行该过滤器。
	 * 除了默认的过滤器类型，Zuul还允许我们创建自定义的过滤器类型。例如，我们可以定制一种STATIC类型的过滤器，直接在Zuul中生成响应，而不将请求转发到后端的微服务。
	 */
	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

	/**
	 * 验证失败
	 *@Description: TODO
	 */
	private void writeFailedMessages(RequestContext ctx,String messages) {
		ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
		ctx.set("isSuccess", false);
		HttpServletResponse response = ctx.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(messages);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 验证成功
	 *@Description: TODO
	 */
	private void success(RequestContext ctx) {
		ctx.setSendZuulResponse(true);// 对该请求进行路由
		ctx.setResponseStatusCode(200);
		ctx.set("isSuccess", true);// 设值，让下一个Filter看到上一个Filter的状态
	}
}
