package com.ptae.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ptae.core.controller.BaseController;

/**
 * 
 * @Description: TODO(获取用户协议页面)
 * @author  xiesc
 * @date 2017年10月19日 
 * @version V1.0  
 */
@Controller
@RequestMapping("/adas-app")
public class ProtocolController extends BaseController{
	
	@RequestMapping(value = "/protocol" ,method = RequestMethod.GET)
	public String getProtocol() {
		return "agreement";
	}
	
}
