package com.tw.phoenixhelper.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	
	private static final Properties pro = new Properties();
	
	static{
		try(InputStream stream = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
			pro.load(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getPhoenixUrl(){
		return pro.getProperty("tw.phoenix.url");
	}
	
	public static String getPhoenixDriverClass(){
		return pro.getProperty("tw.phoenix.driverClass");
	}
	
}
