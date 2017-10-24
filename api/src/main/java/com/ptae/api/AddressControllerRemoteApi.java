
package com.ptae.api;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ptae.api.model.AppAddress;

/**
 * 
 * @Description: TODO
* @author  xiesc
* @date 2017年10月24日 
* @version V1.0  
 */
@RequestMapping("/adas-app")
public interface AddressControllerRemoteApi {
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
	public Map<String, Object> queryAddress(String phoneNum,String token);
	
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
	public Map<String, Object> updateAddress(String phoneNum,String token, AppAddress home);
}
