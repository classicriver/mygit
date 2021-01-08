package com.justbon.lps.config;

import java.io.IOException;
import java.util.Properties;
/**
 * 
 * @author xiesc
 * @date 2020年7月20日
 * @version 1.0.0
 * @Description: 配置文件类
 */
public class ServiceConfig {
	
	private static final Properties properties = new Properties(); 
	
	private ServiceConfig() {
		try {
			properties.load(ServiceConfig.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ServiceConfig getInstance() {
		return SingletonConfig.PRO;
	}

	private static class SingletonConfig {
		private static final ServiceConfig PRO = new ServiceConfig();
	}
	
	public String getAsString(String key){
		return properties.getProperty(key);
	}
	
	public int getAsInt(String key){
		String stringValue = getAsString(key);
		if(null == stringValue || "".equals(stringValue)){
			return 0;
		}
		return Integer.parseInt(stringValue);
	}
	
	public Properties getProperties(){
		return properties;
	}
}
