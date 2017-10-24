package com.ptae.auth.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.exceptions.ClientException;
import com.ptae.auth.common.CommonUtils;
import com.ptae.auth.common.DESUtil;
import com.ptae.auth.common.JJWTUtils;
import com.ptae.auth.config.JedisClient;
import com.ptae.auth.sms.AliSMS;
import com.ptae.core.controller.BaseController;

@RestController
@RequestMapping("/adas-app")
public class LoginController extends BaseController {

	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private AliSMS aliSMS;

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
	public Map<String, Object> sentMessage(@PathVariable String phoneNum,
			@RequestParam("ciphertext") String ciphertext) {
		// 验证md5码
		if (DESUtil.verify(ciphertext)) {
			// 1.生成验证码
			String code = CommonUtils.getRandomCode(6);
			try {
				// 2.发送短信
				if(aliSMS.sent(phoneNum, code)){
					// 3。把验证码存入redis,key = 手机号
					jedisClient.set(phoneNum+"_code", code);
					jedisClient.expire(phoneNum+"_code", 5 * 60);// 5分钟后过期
					return super.successJson("发送成功。", null);
				}
			} catch (ClientException e) {
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
	@RequestMapping(value = "/login/{phoneNum}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(@PathVariable String phoneNum, @RequestParam("authcode") String authcode) {
		// 验证md5码
		String code = jedisClient.get(phoneNum+"_code");

		if (authcode.equals(code)) {
			// 返回token值
			String token = JJWTUtils.getToken(phoneNum, -1);
			Map<String, Object> map = new HashMap<>();
			map.put("identify", token);
			List<Map<String, Object>> list = new ArrayList<>();
			list.add(map);
			// 在缓存中记录下登录状态,缓存默认过期时间30天
			jedisClient.set(phoneNum, token);
			jedisClient.expire(phoneNum, 24*3600*30);
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
	@RequestMapping(value = "/logout/{phoneNum}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> logout(@PathVariable String phoneNum, @RequestParam("token") String token) {
		try {
			// 过期
			jedisClient.expire(phoneNum, 1);
			return super.successJson("退出成功", null);
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
	@RequestMapping(value = "/time/{phoneNum}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTime(@PathVariable String phoneNum, @RequestParam("token") String token) {
		try {
			List<Map<String,Object>> list = new ArrayList<>();
			Map<String,Object> map = new HashMap<>();
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
	@RequestMapping(value = "/isLogined/{phoneNum}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> isRegister(@PathVariable String phoneNum, @RequestParam("token") String token) {
		try {
			List<Map<String,Object>> list = new ArrayList<>();
			Map<String,Object> map = new HashMap<>();
			if(jedisClient.get(phoneNum).equals(token)){
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
}
