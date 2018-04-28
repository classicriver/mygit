/**   
*/ 
package com.tw.study;

/**
 * @Description: TODO 双重检验锁单例模式
* @author  xiesc
* @date 2017年12月28日 
* @version V1.0   
 */
public class Single {
	//禁用jvm的指令重排序
	private static volatile Single instance = null;
	
	private Single() {}
	
	public static Single getInstence() {
		 if(instance == null) {
			 //静态变量的锁是属于class对象的
			 synchronized(Single.class) {
				 if(instance == null) {
					 instance = new Single();
				 }
			 }
		 }
		return instance;
	}
}
