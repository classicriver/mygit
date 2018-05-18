package com.tw.common.utils;

import com.tw.config.Config;

public class Utils {
	
	private final static SnowFlake snow = new SnowFlake(Config.getMachineId());
	
	public static long getUniqueId(){
		return snow.nextId();
	}
	
	public static String getStringUniqueId(){
		return String.valueOf(snow.nextId());
	}
}
