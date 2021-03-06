package com.tw.consumer.utils;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import com.tw.consumer.config.Config;
/**
 * 
 * @author xiesc
 * @TODO  通用工具,时间方法都是线程安全的
 * @time 2018年12月14日
 * @version 1.0
 */
public class CommonUtils {

	private final static SnowFlake snow = new SnowFlake(Config.getInstance()
			.getMachineId());
	private final static DateTimeFormatter dateTime = DateTimeFormatter
			.ofPattern("yyyy-MM-dd HH:mm:ss");
	private final static DateTimeFormatter dayFormat = DateTimeFormatter
			.ofPattern("yyyy-MM-dd");
	private final static DateTimeFormatter hourFormat = DateTimeFormatter
			.ofPattern("HH");

	public static long getUniqueId() {
		return snow.nextId();
	}

	public static String getStringUniqueId() {
		return String.valueOf(snow.nextId());
	}

	/**
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getTimeNow() {
		return LocalDateTime.now(ZoneOffset.ofHours(8)).format(dateTime);
	}

	/**
	 * 
	 * @param timestamps
	 * @param formatter
	 * @return
	 */
	public static String getSpecialTime(long timestamps,
			DateTimeFormatter formatter) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamps),
				ZoneId.systemDefault()).format(formatter);
	}

	/**
	 * 
	 * @param timestamps
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTime(long timestamps) {
		return getSpecialTime(timestamps, dateTime);
	}

	/**
	 * 
	 * @param timestamps
	 * @return yyyy-MM-dd
	 */
	public static String getDay(long timestamps) {
		return getSpecialTime(timestamps, dayFormat);
	}

	/**
	 * 
	 * @param timestamps
	 * @return HH 24小时制
	 */
	public static String getHour(long timestamps) {
		return getSpecialTime(timestamps, hourFormat);

	}
	
	/**
	 * 获取今天零时零分零秒的时间戳
	 * @return
	 */
	public static long getTodayZeroTime(ZoneOffset timeZone){
		return LocalDateTime.of(LocalDate.now(), LocalTime.MIN).toInstant(timeZone).toEpochMilli();
	}
	
	/**
	 * 获取今天往前或往后推的零时时间戳。如：-1  = 昨天零时时间戳，1  = 明天零时时间戳
	 * @param days 负数表示前推，正数表示后推
	 * @return
	 */
	public static long getZeroTime(long days,ZoneOffset timeZone){
		LocalDateTime of = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
		if(days < 0){
			of = of.minusDays(Math.abs(days));
		}else{
			of = of.plusDays(days);
		}
		return of.toInstant(timeZone).toEpochMilli();
		
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
    
    public static long strToDateLong(String strDate) {
    	     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	     ParsePosition pos = new ParsePosition(0);
    	     return formatter.parse(strDate, pos).getTime();

    	  }
    
    public static void main(String[] args) {
    	System.out.println(CommonUtils.strToDateLong("2020-02-13 09:32:59"));
    	//System.out.println(CommonUtils.getDateTime(1571103179000l));
    	
	}
}
