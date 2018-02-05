package com.ptae.auth.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ptae.auth.api.LoginControllerRemoteApi;
import com.ptae.auth.api.model.AppUser;
import com.ptae.auth.common.CommonUtils;
import com.ptae.auth.common.DESUtil;
import com.ptae.auth.common.DictionaryUtils;
import com.ptae.auth.common.JJWTUtils;
import com.ptae.auth.config.JedisClient;
import com.ptae.auth.service.AppUserService;
import com.ptae.auth.sms.AliSMS;
import com.ptae.base.controller.BaseController;

@RestController
public class LoginController extends BaseController implements LoginControllerRemoteApi {

	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private AliSMS aliSMS;
	@Autowired
	private AppUserService userService;

	@Override
	public Map<String, Object> sentMessage(@PathVariable("phoneNum") String phoneNum,
			@RequestParam("ciphertext") String ciphertext) {
		// 验证md5码
		if (DESUtil.verify(ciphertext)) {
			// 1.生成验证码
			String code = CommonUtils.getRandomCode(6);
			try {
				String sentMessages = "";
				// 2.发送短信
				if(!"18583750607".equals(phoneNum)) {
					sentMessages = aliSMS.sent(phoneNum, code);
				}
				
				if ("".equals(sentMessages)) {
					// 3。把验证码存入redis,key = 手机号
					jedisClient.set(phoneNum + "_code", code);
					jedisClient.expire(phoneNum + "_code", 5 * 60);// 5分钟后过期
					return super.successJson("发送成功。", null);
				} else {
					Map<String, Object> map = new HashMap<>();
					map.put("sentResult", sentMessages);
					List<Map<String, Object>> list = new ArrayList<>();
					list.add(map);
					return super.successJson("短信发送失败。", list);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return super.failureJson("验证不通过。", null);
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
	@Override
	public Map<String, Object> login(@PathVariable("phoneNum") String phoneNum, @RequestParam("authcode") String authcode) {
		// 验证md5码
		String code = jedisClient.get(phoneNum + "_code");

		if (authcode.equals(code)) {
			// 返回token值
			String token = JJWTUtils.getToken(phoneNum, -1);
			Map<String, Object> map = new HashMap<>();
			map.put("identify", token);
			List<Map<String, Object>> list = new ArrayList<>();
			list.add(map);
			// 在缓存中记录下登录状态,缓存默认过期时间30天
			jedisClient.set(phoneNum, token);
			jedisClient.expire(phoneNum, 24 * 3600 * 30);
			// 记录登录信息
			AppUser user = new AppUser();
			user.setAccount(phoneNum);
			user.setType(DictionaryUtils.MOBILE);
			userService.saveOrUpdateUser(user);
			return super.successJson("登录成功", list);
		}
		return super.failureJson("验证码输入错误或已过期。", null);
	}

	/**
	 * 
	 * @param token
	 *            令牌
	 * @return
	 * @Description: TODO 退出登录
	 */
	// token:.+ 解决token中有.符号会被截断的问题
	@Override
	public Map<String, Object> logout(@PathVariable("phoneNum") String phoneNum, @RequestParam("token") String token) {
		try {
			String value = jedisClient.get(phoneNum);
			if(token.equals(value)) {
				// 过期
				jedisClient.expire(phoneNum, 1);
				return super.successJson("退出成功", null);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return super.failureJson("退出失败", null);
	}

	/**
	 * 
	 * @param phoneNum
	 * @param token
	 * @return
	 * @Description: TODO获取系统时间
	 */
	@Override
	public Map<String, Object> getTime(@PathVariable("phoneNum") String phoneNum, @RequestParam("token") String token) {
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			Map<String, Object> map = new HashMap<>();
			map.put("time", new Date().getTime());
			list.add(map);
			return super.successJson("获取时间成功", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return super.failureJson("获取时间失败", null);
	}

	/**
	 * 
	 * @param phoneNum
	 * @param token
	 * @return
	 * @Description: TODO 判断当前用户是否已经登录
	 */
	@Override
	public Map<String, Object> isRegister(@PathVariable("phoneNum") String phoneNum, @RequestParam("token") String token) {
		try {
			List<Map<String,Object>> list = new ArrayList<>();
			Map<String,Object> map = new HashMap<>();
			if(!CommonUtils.isNullOrEmpty(token) && token.equals(jedisClient.get(phoneNum))){
				map.put("isLogined", true);
			}else{
				map.put("isLogined", false);
			}
			
			list.add(map);
			return super.successJson("调用成功", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return super.failureJson("调用失败", null);
	}

	/* (non-Javadoc)
	 * @see com.ptae.auth.api.LoginControllerRemoteApi#thirdpartyLogin(com.ptae.auth.api.model.Parameter)
	 */
	/*@Override
	public Map<String, Object> thirdpartyLogin(@RequestBody Parameter para) {
		// TODO Auto-generated method stub
		PlatformFactory.getPlatformInstance(para.getType()).login(para);
		return null;
	}*/
}
