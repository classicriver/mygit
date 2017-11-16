/**
*/
package com.ptae.auth.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ptae.auth.api.AddressControllerRemoteApi;
import com.ptae.auth.api.model.AppAddress;
import com.ptae.auth.api.model.AppAddressExample;
import com.ptae.auth.common.CommonUtils;
import com.ptae.auth.service.AppAddressService;
import com.ptae.base.controller.BaseController;

/**
 * @Description: TODO()
 * @author xiesc
 * @date 2017年10月17日
 * @version V1.0  
 */
@RestController
public class AddressController extends BaseController implements AddressControllerRemoteApi{

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
    @Override
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
	/*@RequestMapping(value = "/address/{phoneNum}", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> saveAddress(@PathVariable String phoneNum, @RequestParam("token") String token,
			@RequestParam(value = "data") AppAddress home) {
		try {
			home.setUserAccount(phoneNum);
			home.setAddTime(new Date());
			service.insert(home);
			return super.successJson("新增成功", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.failureJson("新增失败", null);
	}*/

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
/*	@RequestMapping(value = "/address/{phoneNum}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deleteAddress(@PathVariable String phoneNum, @RequestParam("token") String token,
			@RequestParam(value = "home", required = false) String home,
			@RequestParam(value = "company", required = false) String company) {
		try {
			AppAddressExample example = new AppAddressExample();
			example.createCriteria().andUserAccountEqualTo(phoneNum);
			
			 * AppAddress address = new AppAddress();
			 * if(!CommonUtils.isNullOrEmpty(home)){
			 * address.setHomeAddress(home); }else
			 * if(!CommonUtils.isNullOrEmpty(company)){
			 * address.setCompnayAddress(company); }
			 
			service.deleteByExample(example);

			return super.successJson("删除成功", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.failureJson("删除失败", null);
	}*/

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
    @Override
	public Map<String, Object> updateAddress(@PathVariable String phoneNum, @RequestParam("token") String token,
			@RequestBody AppAddress home) {
    	try {
			//有id代表更新
			if(!CommonUtils.isNullOrEmpty(home.getId())){
				AppAddressExample example = new AppAddressExample();
				example.createCriteria().andUserAccountEqualTo(phoneNum);
				home.setUpdateTime(new Date());
				//如果家地址为空，设置家的经度纬度为空
				if(CommonUtils.isNullOrEmpty(home.getHomeAddress())){
					home.setHomeLongitude("");
					home.setHomeLatitude("");
				}
				//如果公司地址为空，设置公司的经度纬度为空
				if(CommonUtils.isNullOrEmpty(home.getCompnayAddress())){
					home.setCompanyLongitude("");
					home.setCompanyLatitude("");
				}
				service.updateByExampleSelective(home, example);
				return super.successJson("更新成功", null);
			//没id代表新增
			}else{
				home.setUserAccount(phoneNum);
				home.setAddTime(new Date());
				service.insert(home);
				return super.successJson("新增成功", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.failureJson("更新失败", null);
	}
}
