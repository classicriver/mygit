/**    
*/ 
package com.ptae.auth.service;

import com.ptae.auth.api.model.AppUser;
import com.ptae.base.service.BaseService;

/**
 * @Description: TODO()
 * @author  xiesc
 * @date 2017年10月12日 
 * @version V1.0  
 */
public interface AppUserService extends BaseService<AppUser> {
	public int saveOrUpdateUser(String phoneNum);
}
