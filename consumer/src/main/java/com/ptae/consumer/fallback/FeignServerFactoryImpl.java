/**   
*/ 
package com.ptae.consumer.fallback;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ptae.consumer.service.LoginService;

import feign.hystrix.FallbackFactory;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年10月27日 
* @version V1.0   
 */
@Component
public class FeignServerFactoryImpl implements FallbackFactory<LoginService>{

	/* (non-Javadoc)
	 * @see feign.hystrix.FallbackFactory#create(java.lang.Throwable)
	 */
	@Override
	public LoginService create(Throwable arg0) {
		
		// TODO Auto-generated method stub
		return new LoginService() {

			@Override
			public Map<String, Object> sentMessage(String phoneNum, String ciphertext) {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<>();
				map.put("error", arg0.getMessage());
				return map;
			}

			@Override
			public Map<String, Object> login(String phoneNum, String authcode) {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<>();
				map.put("error", arg0.getMessage());
				return map;
			}

			@Override
			public Map<String, Object> logout(String phoneNum, String token) {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<>();
				map.put("error", arg0.getMessage());
				return map;
			}

			@Override
			public Map<String, Object> getTime(String phoneNum, String token) {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<>();
				map.put("error", arg0.getMessage());
				return map;
			}

			@Override
			public Map<String, Object> isRegister(String phoneNum, String token) {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<>();
				map.put("error", arg0.getMessage());
				return map;
			}
			
		};
	}

}
