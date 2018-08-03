package com.tw.ddcs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
/**
 * 
 * @author xiesc
 * @TODO 
 * @time 2018年8月2日
 * @version 1.0
 */
public class Utils {
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		return sdf.format(new Date());
	}
	/**
	 * 
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date getDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
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
