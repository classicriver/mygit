
package com.ptae.auth.api;

import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ptae.auth.api.model.AppAddress;
import com.ptae.auth.api.model.AppUser;

/**
 * 
 * @Description: TODO
* @author  xiesc
* @date 2017年10月24日 
* @version V1.0  
 */
@RequestMapping("/provider-auth")
public interface LoginControllerRemoteApi {
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
	public Map<String, Object> sentMessage(@PathVariable("phoneNum")String phoneNum,@RequestParam("ciphertext")String ciphertext);
	
	/**
	 * 
	 * @param phoneNum 用户名
	 * @param authcode 验证码
	 * @return
	 * @Description: TODO 登录
	 */
	@RequestMapping(value = "/login/{phoneNum}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(@PathVariable("phoneNum")String phoneNum, @RequestParam("authcode")String authcode);
	
	/**
	 * 
	 * @param token 令牌
	 * @return
	 * @Description: TODO 退出登录
	 */
	// token:.+ 解决token中有.符号会被截断的问题
	@RequestMapping(value = "/logout/{phoneNum}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> logout(@PathVariable("phoneNum") String phoneNum, @RequestParam("token") String token);
	
	/**
	 * 
	 * @param phoneNum
	 * @param token
	 * @return
	 * @Description: TODO获取系统时间
	 */
	@RequestMapping(value = "/time/{phoneNum}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTime(@PathVariable("phoneNum") String phoneNum,@RequestParam("token")String token);
	
	/**
	 * 
	 * @param phoneNum
	 * @param token
	 * @return
	 * @Description: TODO 判断当前用户是否已经登录
	 */
	@RequestMapping(value = "/isLogined/{phoneNum}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> isRegister(@PathVariable("phoneNum")String phoneNum,@RequestParam("token")String token);
	
	/**
	 * 
	 * @param phoneNum
	 * @param token
	 * @return
	 * @Description: TODO 第三方登录接口
	 */
	/*@RequestMapping(value = "/thirdpartyLogin/{phoneNum}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> thirdpartyLogin(@PathVariable("account")String account,@RequestBody AppUser user);*/
	
}
