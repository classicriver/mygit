/**
*/
package com.ptae.auth.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ptae.auth.common.CommonUtils;
import com.ptae.auth.model.AppAddress;
import com.ptae.auth.model.AppAddressExample;
import com.ptae.auth.service.AppAddressService;
import com.ptae.core.controller.BaseController;

/**
 * @Description: TODO()
 * @author xiesc
 * @date 2017年10月17日
 * @version V1.0  
 */
@Controller
public class AddressController extends BaseController {

	@Autowired
	private AppAddressService service;

	/**
	 * 
	 * @param phoneNum
	 *            电话号码
	 * @param token
	 *            令牌
	 * @return
	 * @Description: TODO 查询用户地址信息
	 */
	@RequestMapping(value = "/address/{phoneNum}", method = RequestMethod.GET, consumes = "application/json")
	@ResponseBody
	public Map<String, Object> queryAddress(@PathVariable String phoneNum, @RequestParam("token") String token) {
		try {
			AppAddressExample example = new AppAddressExample();
			example.createCriteria().andUserAccountEqualTo(phoneNum);
			List<AppAddress> address = service.selectByExample(example);
			return super.successJson("查询成功", address);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.failureJson("查询失败", null);
	}
	/**
	 * 
	 * @param phoneNum
	 * @param token
	 * @param home
	 * @param company
	 * @return
	 * @Description: TODO  新增用户地址信息
	 */
	@RequestMapping(value = "/address/{phoneNum}", method = RequestMethod.PUT, consumes = "application/json")
	@ResponseBody
	public Map<String, Object> saveAddress(@PathVariable String phoneNum, @RequestParam("token") String token,
			@RequestParam(value = "home", required = false) String home,
			@RequestParam(value = "company", required = false) String company) {
		try {
			AppAddress address = new AppAddress();
			address.setUserAccount(phoneNum);
			if (!CommonUtils.isNullOrEmpty(home)) {
				address.setHomeAddress(home);
			} 
			if (!CommonUtils.isNullOrEmpty(company)) {
				address.setCompnayAddress(company);
			}
			address.setAddTime(new Date());
			service.insert(address);
			return super.successJson("新增成功", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.failureJson("新增失败", null);
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
	 * @Description: TODO  删除地址
	 */
	@RequestMapping(value = "/address/{phoneNum}", method = RequestMethod.DELETE, consumes = "application/json")
	@ResponseBody
	public Map<String, Object> deleteAddress(@PathVariable String phoneNum, @RequestParam("token") String token,
			@RequestParam(value = "home", required = false) String home,
			@RequestParam(value = "company", required = false) String company) {
		try {
			AppAddressExample example = new AppAddressExample();
			example.createCriteria().andUserAccountEqualTo(phoneNum);
			/*
			 * AppAddress address = new AppAddress();
			 * if(!CommonUtils.isNullOrEmpty(home)){
			 * address.setHomeAddress(home); }else
			 * if(!CommonUtils.isNullOrEmpty(company)){
			 * address.setCompnayAddress(company); }
			 */
			service.deleteByExample(example);

			return super.successJson("删除成功", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.failureJson("删除失败", null);
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
	@RequestMapping(value = "/address/{phoneNum}", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Map<String, Object> updateAddress(@PathVariable String phoneNum, @RequestParam("token") String token,
			@RequestParam(value = "home", required = false) String home,
			@RequestParam(value = "company", required = false) String company) {
		try {
			AppAddressExample example = new AppAddressExample();
			example.createCriteria().andUserAccountEqualTo(phoneNum);
			AppAddress address = new AppAddress();
			if (!CommonUtils.isNullOrEmpty(home)) {
				address.setHomeAddress(home);
			} 
			if (!CommonUtils.isNullOrEmpty(company)) {
				address.setCompnayAddress(company);
			}
			address.setUpdateTime(new Date());
			service.updateByExampleSelective(address, example);

			return super.successJson("更新成功", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.failureJson("更新失败", null);
	}
}
