/**   
*/ 
package com.ptae.consumer.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ptae.auth.api.LoginControllerRemoteApi;
import com.ptae.auth.api.model.Parameter;
import com.ptae.base.controller.BaseController;
import com.ptae.consumer.service.LoginService;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年10月24日 
* @version V1.0   
 */
@RestController
public class LoginController extends BaseController{
	
	@Autowired
	private LoginService service;
	
	/**
	 * 
	 * @param phoneNum
	 *            电话号码
	 * @param ciphertext
	 *            md5密文
	 * @return
	 * @Description: TODO 发送验证码
	 */
	@RequestMapping(value = "/sentAuthCode/{phoneNum}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sentMessage(@PathVariable("phoneNum")String phoneNum,@RequestParam("ciphertext")String ciphertext){
		 return service.sentMessage(phoneNum, ciphertext);
	}
	
	/**
	 * 
	 * @param phoneNum
	 *            用户名
	 * @param authcode
	 *            验证码
	 * @return
	 * @Description: TODO 登录
	 */
	@RequestMapping(value = "/login/{phoneNum}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(@PathVariable("phoneNum")String phoneNum, @RequestParam("authcode")String authcode){
		return service.login(phoneNum, authcode);
	}
	
	/**
	 * 
	 * @param token
	 *            令牌
	 * @return
	 * @Description: TODO 退出登录
	 */
	// token:.+ 解决token中有.符号会被截断的问题
	@RequestMapping(value = "/logout/{phoneNum}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> logout(@PathVariable("phoneNum") String phoneNum, @RequestParam("token") String token){
		return service.logout(phoneNum, token);
	}
	
	/**
	 * 
	 * @param phoneNum
	 * @param token
	 * @return
	 * @Description: TODO获取系统时间
	 */
	@RequestMapping(value = "/time/{phoneNum}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTime(@PathVariable("phoneNum") String phoneNum,@RequestParam("token")String token){
		return service.getTime(phoneNum, token);
	}
	
	/**
	 * 
	 * @param phoneNum
	 * @param token
	 * @return
	 * @Description: TODO 判断当前用户是否已经登录
	 */
	@RequestMapping(value = "/isLogined/{phoneNum}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> isRegister(@PathVariable("phoneNum")String phoneNum,@RequestParam("token")String token){
		return service.isRegister(phoneNum, token);
	}

	/**
	 * 第三方登录
	 *@Description: TODO
	 */
/*	@RequestMapping(value = "/thirdpartyLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> thirdpartyLogin(@RequestBody Parameter para) {
		// TODO Auto-generated method stub
		return service.thirdpartyLogin(para);
	}*/
}
