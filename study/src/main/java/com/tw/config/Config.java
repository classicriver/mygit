package com.tw.config;

import java.io.IOException;
import java.util.Properties;

public class Config {

	private static Properties pro = new Properties();
	static {
		try {
			pro.load(Config.class.getClassLoader().getResourceAsStream(
					"config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getPropertie(String key) {
		return pro.getProperty(key);
	}
}
