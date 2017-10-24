/**    
*/ 
package com.ptae.core.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @Description: TODO(基础controller封装)
 * @author  xiesc
 * @date 2017年10月12日 
 * @version V1.0  
 */
public class BaseController {
	
	protected HttpServletRequest request;
	
	protected HttpServletResponse response;

	protected HttpSession session;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @Description: TODO
	 */
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
	
	protected Map<String,Object> successJson(String message , Object data){
		Map<String,Object> map = new HashMap<>();
		map.put("code", 200);//状态码 200成功
		map.put("data", data);
		map.put("message", message);
		return map;
	}
	
	protected Map<String,Object> failureJson(String message,Object data){
		Map<String,Object> map = new HashMap<>();
		map.put("code", 400);//状态码400失败
		map.put("data", data);
		map.put("message", message);
		return map;
	}
}
