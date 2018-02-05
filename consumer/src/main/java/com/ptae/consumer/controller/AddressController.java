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

import com.ptae.auth.api.AddressControllerRemoteApi;
import com.ptae.auth.api.model.AppAddress;
import com.ptae.base.controller.BaseController;
import com.ptae.consumer.service.AddressService;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年10月27日 
* @version V1.0   
 */
@RestController
public class AddressController extends BaseController{
	@Autowired
	private AddressService service;
	/**
	 * 
	 * @param phoneNum
	 *            电话号码
	 * @param token
	 *            令牌
	 * @return
	 * @Description: TODO 查询用户地址信息
	 */
	@RequestMapping(value = "/address/{phoneNum}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryAddress(@PathVariable("phoneNum") String phoneNum, @RequestParam("token") String token){
		return service.queryAddress(phoneNum, token);
	}
	
	/**
	 * 
	 * @param phoneNum
	 *            电话号码
	 * @param token
	 *            令牌
	 * @param home
	 *            家庭地址
	 * @param company
	 *            公司地址
	 * @return
	 * @Description: TODO 更新地址
	 */
	@RequestMapping(value = "/address/{phoneNum}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAddress(@PathVariable("phoneNum") String phoneNum, @RequestParam("token") String token,
			@RequestBody AppAddress home){
		return service.updateAddress(phoneNum, token, home);
	}
}
