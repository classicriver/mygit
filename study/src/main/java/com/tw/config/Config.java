package com.tw.config;

import java.io.IOException;
import java.util.Properties;

public class Config {
	
	private final static String MAXTHREADS ="tw.analysizer.maxThreads";
	
	private final static Properties pro = new Properties();
	
	static {
		try {
			pro.load(Config.class.getClassLoader().getResourceAsStream(
					"config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int getMaxThreads(){
		return Integer.parseInt(pro.getProperty(MAXTHREADS));
	}
}
