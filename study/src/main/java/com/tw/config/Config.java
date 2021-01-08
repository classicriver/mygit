package com.tw.config;

import java.io.IOException;
import java.util.Properties;

public class Config {
	
	private final static String MAXTHREADS ="tw.analysizer.maxThreads";
	
	private final static String SERVERPORT ="tw.server.port";
	
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
		String property = pro.getProperty(MAXTHREADS);
		if(!"".equals(property) && property != "0"){
			return Integer.parseInt(pro.getProperty(MAXTHREADS));
		}else{
			return Runtime.getRuntime().availableProcessors();
		}
	}

	public static int getServerport() {
		String property = pro.getProperty(SERVERPORT);
		if(!"".equals(property)){
			return Integer.parseInt(property);
		}else{
			return 10000;
		}
	}
}
