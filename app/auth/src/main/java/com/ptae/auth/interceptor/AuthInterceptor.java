package com.ptae.auth.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ptae.auth.common.CommonUtils;
import com.ptae.auth.config.JedisClient;
/**
 * 
 * @Description: TODO(token验证拦截器)
 * @author  xiesc
 * @date 2017年10月13日 
 * @version V1.0  
 */
public class AuthInterceptor implements HandlerInterceptor{
	
	@Autowired
	private JedisClient jedisClient;
	/**
     * 页面渲染之后调用
     */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
	}
	/**
     * controller 执行之后，且页面渲染之前调用
     */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
	}
	/**
     * controller 执行之前调用
     */
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		Map<String,String> map =  (Map<String, String>) arg0.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String token = arg0.getParameter("token");
		String phoneNum = map.get("phoneNum");
		//从redis中验证用户签名
		String value = jedisClient.get(phoneNum);
		if(!CommonUtils.isNullOrEmpty(value) && value.equals(token)){
			return true;
		}
		//签名过期或者验证不通过
		arg1.setCharacterEncoding("UTF-8");
		arg1.getWriter().write("{\"code\":400,\"data\":null,\"message\":\"令牌验证失败\"}");
		return false;
	}

}
