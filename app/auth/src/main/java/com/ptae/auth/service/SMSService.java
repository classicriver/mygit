/**   
* @Description: TODO(用一句话描述该文件做什么) 
* @author  
* @date 2017年10月16日 
* @version V1.0   
*/ 
package com.ptae.auth.service;

import com.ptae.auth.api.model.AppSms;
import com.ptae.base.service.BaseService;

/**
 * @Description: TODO()
 * @author  xiesc
 * @date 2017年10月16日 
 * @version V1.0  
 */
public interface SMSService extends BaseService<AppSms>{
	/**
	 * 
	 * @return
	 * @Description: TODO 短信发送前验证
	 */
	public boolean canSent(); 
}
