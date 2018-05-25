package com.tw.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tw.config.Config;

public class Utils {
	
	private final static SnowFlake snow = new SnowFlake(Config.getMachineId());
	
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
}
