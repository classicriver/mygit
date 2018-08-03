package com.tw.ddcs.config;

import java.util.HashMap;
import java.util.Map;

import com.tw.ddcs.resources.PropertyResources;
/**
 * 
 * @author xiesc
 * @TODO 应用配置文件
 * @time 2018年8月2日
 * @version 1.0
 */
public class DdcsConfig extends PropertyResources{

	private final static String MAXTHREADS = "tw.disruptor.maxThreads";
	
	private final static String HOST = "tw.mqtt.host";
	
	private final static String TOPIC = "tw.mqtt.topic";
	
	private final static String CLIENTID = "tw.mqtt.clientid";
	
	private final static String USERNAME = "tw.mqtt.username";
	
	private final static String PWD = "tw.mqtt.pwd";
	
	private final static String REPEATINTERVAL = "tw.mysql.repeatInterval";
	
	private final static String BATCHNUMBER = "tw.mysql.batchNumber";


	private final static Map<String, Object> valueCache = new HashMap<>();

	private DdcsConfig() {
	}

	public static DdcsConfig getInstance() {
		return SingletonConfig.PRO;
	}

	private static class SingletonConfig {
		private static final DdcsConfig PRO = new DdcsConfig();
	}

	private Object getOrSetDefault(String key, Object def) {
		Object temp = valueCache.get(key);
		if (null == temp) {
			String property = pro.getProperty(key);
			if (!"".equals(property)) {
				valueCache.put(key, property);
				return property;
			}
			valueCache.put(key, def);
			return def;
		} else {
			return temp;
		}
	}
	
	@Override
	protected String getProFileName() {
		// TODO Auto-generated method stub
		return "config.properties";
	}

	public int getMaxThreads() {
		return Integer.parseInt((String) getOrSetDefault(MAXTHREADS, Runtime
				.getRuntime().availableProcessors() * 2));
	}
	
	public String getMqttHost(){
		return (String) getOrSetDefault(HOST,"");
	}
	
	public String getMqttTopic(){
		return (String) getOrSetDefault(TOPIC,"");
	}
	
	public String getMqttClientid(){
		return (String) getOrSetDefault(CLIENTID,"");
	}
	
	public String getMqttUserName(){
		return (String) getOrSetDefault(USERNAME,"");
	}
	
	public String getMqttPwd(){
		return (String) getOrSetDefault(PWD,"");
	}
	
	public int getRepeatInterval(){
		return Integer.parseInt((String) getOrSetDefault(REPEATINTERVAL, 20));
	}
	
	public int getBatchNumber(){
		return Integer.parseInt((String) getOrSetDefault(BATCHNUMBER, 50));
	}
}
