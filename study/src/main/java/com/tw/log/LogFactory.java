package com.tw.log;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogFactory {

	private static ConcurrentMap<String, Logger> cache = new ConcurrentHashMap<>();

	static {
		PropertyConfigurator.configure(LogFactory.class.getClassLoader()
				.getResourceAsStream("log4j.properties"));
	}

	public static Logger getLogger(Class<?> clazz) {
		Logger logger;
		String className = clazz.getName();

		if (null == cache.get(className)) {
			logger = Logger.getLogger(clazz);
			cache.put(className, logger);
		} else {
			logger = cache.get(className);
		}
		return logger;
	}
}
