/**   
*/ 
package com.ptae.auth.service;

import com.ptae.auth.api.model.Dictionary;
import com.ptae.base.service.BaseService;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年12月5日 
* @version V1.0   
 */
public interface DictionaryService extends BaseService<Dictionary> {
	
	public String getKey(String dictType,String value);
	
	public String getValue(String dictType,String key);
	
}
