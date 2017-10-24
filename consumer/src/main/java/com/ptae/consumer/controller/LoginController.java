/**   
*/ 
package com.ptae.consumer.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ptae.consumer.service.LoginService;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年10月24日 
* @version V1.0   
 */
@RestController
public class LoginController {
	
	@Autowired
	private LoginService service;
	
	@RequestMapping("/test")
	@ResponseBody
	public Map<String, Object> test() {
		 Map<String, Object> sentMessage = service.sentMessage("18284566781", "Lu102p5BYxY=");
		 return sentMessage;
	}
}
