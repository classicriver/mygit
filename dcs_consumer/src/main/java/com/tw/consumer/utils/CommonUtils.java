package com.tw.consumer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.tw.consumer.config.Config;

public class CommonUtils{
	
private final static SnowFlake snow = new SnowFlake(Config.getInstance().getMachineId());
	
	public static long getUniqueId(){
		return snow.nextId();
	}
	
	public static String getStringUniqueId(){
		return String.valueOf(snow.nextId());
	}
	
	public static String getDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		return sdf.format(new Date());
	}
	
	public static long timeString2Timestamp(String time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(format.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendar.getTimeInMillis();
	}
}
