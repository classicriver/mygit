package com.tw.ddcs.db.core;
/**
 * 
 * @author xiesc
 * @TODO dao工厂类
 * @time 2018年8月2日
 * @version 1.0
 */
public class DaoFactory extends SingleBeanFactory{
	
	public <T> T  getDao(Class<T> clazz) {
		return super.getBean(clazz);
	}
}
