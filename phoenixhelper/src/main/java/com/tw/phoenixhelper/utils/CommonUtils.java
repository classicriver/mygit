package com.tw.phoenixhelper.utils;

import java.util.Map;

/**
 * 
 * @author xiesc
 * @TODO  通用工具,时间方法都是线程安全的
 * @time 2018年12月14日
 * @version 1.0
 */
public class CommonUtils {
	/**
	 * map转对象
	 * @param map
	 * @param beanClass
	 * @return 
	 * @throws Exception
	 */
	public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) throws Exception {    
        if (map == null)  
            return null;  
        T obj = beanClass.newInstance();  
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);  
        return obj;  
    }    
    /**
     * 对象转map
     * @param obj
     * @return
     */
    public static Map<?, ?> objectToMap(Object obj) {  
        if(obj == null)  
            return null;   
        return new org.apache.commons.beanutils.BeanMap(obj);  
    } 
}
