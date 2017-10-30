/**   
*/ 
package com.ptae.consumer.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ptae.consumer.service.LoginService;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年10月27日 
* @version V1.0   
 */

public class LoginFallBackImpl implements LoginService{

	/* (non-Javadoc)
	 * @see com.ptae.auth.api.LoginControllerRemoteApi#sentMessage(java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> sentMessage(String phoneNum, String ciphertext) {
		// TODO Auto-generated method stub
		System.out.println("do");
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ptae.auth.api.LoginControllerRemoteApi#login(java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> login(String phoneNum, String authcode) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ptae.auth.api.LoginControllerRemoteApi#logout(java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> logout(String phoneNum, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ptae.auth.api.LoginControllerRemoteApi#getTime(java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> getTime(String phoneNum, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ptae.auth.api.LoginControllerRemoteApi#isRegister(java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> isRegister(String phoneNum, String token) {
		// TODO Auto-generated method stub
		return null;
	}

}
