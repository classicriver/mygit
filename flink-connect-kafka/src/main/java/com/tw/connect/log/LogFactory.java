package com.tw.connect.log;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.util.ReflectionUtil;

/**
 * 
 * @author xiesc
 * @TODO log4j2
 * @time 2018年5月14日
 * @version 1.0
 */
public class LogFactory {

	private static ConcurrentMap<String, Logger> loggerCache = new ConcurrentHashMap<>();

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

	public static Logger getLogger() {
		Class<?> callerClass = ReflectionUtil.getCallerClass(2);
		String className = callerClass.getName();
		Logger logger;
		if (null == loggerCache.get(className)) {
			logger = LogManager.getLogger(callerClass);
			loggerCache.put(className, logger);
		} else {
			logger = loggerCache.get(className);
		}
		return logger;
	}
}
