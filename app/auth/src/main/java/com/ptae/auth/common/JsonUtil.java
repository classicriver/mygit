/**   
*/ 
package com.ptae.auth.common;

import com.google.gson.Gson;

/**
 * @Description: TODO
* @author  xiesc
* @date 2018年1月31日 
* @version V1.0   
 */
public class JsonUtil {
	
	private static final Gson gson = new Gson();
	
	public static String toJSON(final Object obj) {
		return gson.toJson(obj);
	}
	
	public static <T> T json2Object(String json,Class<T> cls) {
		return gson.fromJson(json, cls);
	}
}
