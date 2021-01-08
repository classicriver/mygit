package com.tw.ddcs.dao.core;

import java.util.concurrent.ConcurrentHashMap;
/**
 * 
 * @author xiesc
 * @TODO Bean工厂类 工厂里的bean只会初始化一次
 * @time 2018年8月2日
 * @version 1.0
 */
public class SingleBeanFactory {
	
	protected static final ConcurrentHashMap<String, Object> beans = new ConcurrentHashMap<>();

	public <T> T getBean(Class<T> clazz) {
		@SuppressWarnings("unchecked")
		T obj = (T) beans.get(clazz.getName());
		if(null == obj){
			obj = initBean(clazz);
		}
		return obj;
	}
	
	private synchronized <T> T initBean(Class<T> clazz){
		String clazzName = clazz.getName();
		@SuppressWarnings("unchecked")
		T ins = (T) beans.get(clazzName);
		try {
			if(null == ins){
				ins =  clazz.newInstance();
				beans.put(clazzName, ins);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ins;
	}

}
