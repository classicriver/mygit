package com.tw.connect.utils;

import java.lang.reflect.Field;
import java.util.Calendar;

public class Utils {
	
	public static String getCurrentYearAndMonth(){
		int month = Calendar.getInstance().get(Calendar.MONTH);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		return String.valueOf(year)+String.valueOf(month+1);
	}
	/**
	 *  pojo 转为   esn,timestamps.rowtime,power_factor.......格式
	 * @param clazz
	 * @return
	 */
	public static String pojo2TableSelectString(Class<?> clazz){
		StringBuffer sb = new StringBuffer();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			String name = field.getName();
			if("timestamps".equals(name)){
				//水位线
				sb.append(field.getName()+".rowtime,");
			}else{
				sb.append(field.getName()+",");
			}
		}
		return sb.substring(0, sb.length()-1);
	}
}
