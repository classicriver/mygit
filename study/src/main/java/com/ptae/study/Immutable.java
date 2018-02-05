/**   
*/ 
package com.ptae.study;

/**
 * @Description: TODO
* @author  xiesc
* @date 2018年1月16日 
* @version V1.0   
 */
public class Immutable {
	
	private final char value;
	
	Immutable(char value){
		this.value = value;
	}
	
	public Immutable append(char value) {
		return new Immutable(value);
	}
}
