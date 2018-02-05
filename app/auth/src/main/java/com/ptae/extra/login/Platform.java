/**   
*/ 
package com.ptae.extra.login;

import java.util.Map;

import com.ptae.auth.api.model.Parameter;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年12月28日 
* @version V1.0   
 */
public interface Platform {
	
	public Map<String,Object> login(Parameter para);
	
}
