package com.tw.ddcs.db.core;

import java.util.concurrent.ConcurrentHashMap;
/**
 * 
 * @author xiesc
 * @TODO Bean工厂类 工厂里的bean只会初始化一次
 * @time 2018年8月2日
 * @version 1.0
 */
public class SingleBeanFactory {
	
	protected static final ConcurrentHashMap<String, Object> beanCache = new ConcurrentHashMap<>();

	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> clazz) {
		T obj = (T) beanCache.get(clazz.getName());
		if(null == obj){
			obj = initBean(clazz);
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	private synchronized <T> T initBean(Class<T> clazz){
		T ins = (T) beanCache.get(clazz.getName());
		try {
			if(null == ins){
				ins =  clazz.newInstance();
				beanCache.put(clazz.getName(), ins);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ins;
	}

}
