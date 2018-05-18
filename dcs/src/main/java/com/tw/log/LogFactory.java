package com.tw.log;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
/**
 * 
 * @author xiesc
 * @TODO  log4j2
 * @time 2018年5月14日
 * @version 1.0
 */
public class LogFactory {

	private static ConcurrentMap<String, Logger> cache = new ConcurrentHashMap<>();

	static {
		ConfigurationSource source;
		try {
			source = new ConfigurationSource(LogFactory.class.getClassLoader()
					.getResourceAsStream("log4j2.xml"));

			Configurator.initialize(null, source);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static Logger getLogger(Class<?> clazz) {
		Logger logger;
		String className = clazz.getName();

		if (null == cache.get(className)) {
			logger = LogManager.getLogger(clazz);
			cache.put(className, logger);
		} else {
			logger = cache.get(className);
		}
		return logger;
	}
}
