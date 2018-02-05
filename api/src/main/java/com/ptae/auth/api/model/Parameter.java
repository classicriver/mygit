/**   
*/ 
package com.ptae.auth.api.model;

/**
 * @Description: TODO 第三方登录参数实体类
* @author  xiesc
* @date 2017年12月28日 
* @version V1.0   
 */
public class Parameter {
	/**
	 * 登录类型
	 */
	private String type;
	/**
	 * 访问令牌，有效时间2小时
	 */
	private String code;
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

}
