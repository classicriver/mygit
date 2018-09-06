package com.tw.consumer.core;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 
 * @author xiesc
 * @TODO 单例Bean工厂类
 * @time 2018年8月2日
 * @version 1.0
 */
public class SingleBeanFactory {
	
	protected static final ConcurrentHashMap<String, Object> beans = new ConcurrentHashMap<>();

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		T obj = (T) beans.get(clazz.getName());
		if(null == obj){
			obj = initBean(clazz);
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	private static synchronized <T> T initBean(Class<T> clazz){
		String clazzName = clazz.getName();
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
	
	public static void autoShutdown(){
		Iterator<String> it = beans.keySet().iterator();
		while(it.hasNext()){
			Object bean = beans.get(it.next());
			if(bean instanceof AutoShutdown){
				((AutoShutdown) bean).shutdown();
			}
		}
	}

}
