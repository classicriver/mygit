package com.tw.consumer.core;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 
 * @author xiesc
 * @TODO 单例Bean工厂类
 * @time 2018年8月2日
 * @version 1.0
 */
public class SingleBeanFactory {

	protected static final HashMap<String, Object> beans = new HashMap<>();

	protected SingleBeanFactory() {
	}

	/**
	 * 构造函数无参bean
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		T obj = (T) beans.get(clazz.getName());
		if (null == obj) {
			obj = initBean(clazz, null, null);
		}
		return obj;
	}

	/**
	 * 构造函数有参bean
	 * 
	 * @param clazz
	 * @param parameterTypes
	 *            参数类
	 * @param parameters
	 *            参数对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz, Class<?>[] parameterTypes,
			Object[] parameters) {
		T obj = (T) beans.get(clazz.getName());
		if (null == obj) {
			obj = initBean(clazz, parameterTypes, parameters);
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	private synchronized static <T> T initBean(Class<T> clazz,
			Class<?>[] parameterTypes, Object[] parameters) {
		String clazzName = clazz.getName();
		T ins = (T) beans.get(clazzName);
		try {
			// 双重判空
			if (null == ins) {
				MethodType methodType = null;
				if (null != parameterTypes && null != parameters) {
					methodType = MethodType.methodType(void.class,parameterTypes);
				} else {
					methodType = MethodType.methodType(void.class);
				}
				ins = (T) MethodHandles.lookup().findConstructor(clazz,
						methodType).invokeWithArguments(parameters);
				beans.put(clazzName, ins);
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ins;
	}

	public static void autoShutdown() {
		Iterator<String> it = beans.keySet().iterator();
		while (it.hasNext()) {
			Object bean = beans.get(it.next());
			if (bean instanceof AutoShutdown) {
				((AutoShutdown) bean).shutdown();
			}
		}
	}
}
