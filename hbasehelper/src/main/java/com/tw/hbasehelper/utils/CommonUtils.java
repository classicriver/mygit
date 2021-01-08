package com.tw.hbasehelper.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class CommonUtils{
	
	public static String getDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		return sdf.format(new Date());
	}
	
	public static long timeString2Timestamp(String time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(format.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendar.getTimeInMillis();
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
}
