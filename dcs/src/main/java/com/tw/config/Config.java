package com.tw.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * @author xiesc
 * @TODO 全局配置文件
 * @time 2018年5月14日
 * @version 1.0
 */
public class Config {

	private final static String MAXTHREADS = "tw.analysizer.maxThreads";
	private final static String SERVERPORT = "tw.server.port";
	private final static String NAMESERVER = "tw.rocketmq.nameServer";
	private final static String PRODUCERNAME = "tw.rocketmq.producerName";
	private final static String MACHINEID = "tw.snowflake.machineId";
	
	private final static Map<String,Object> valueCache = new HashMap<>();
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

	public static int getMaxThreads() {
		return Integer.parseInt((String) getOrSetDefault(MAXTHREADS,Runtime.getRuntime().availableProcessors() * 2));
	}

	public static int getServerPort() {
		return Integer.parseInt((String) getOrSetDefault(SERVERPORT,10000));
	}

	public static String getNameServer() {
		return (String) getOrSetDefault(NAMESERVER,"");
	}

	public static String getProducerName() {
		return (String) getOrSetDefault(PRODUCERNAME,"");
	}
	
	public static int getMachineId() {
		return Integer.parseInt((String) getOrSetDefault(MACHINEID,1));
	}
	
	private static Object getOrSetDefault(String key,Object defaultValue){
		Object temp = valueCache.get(key);
		if(null == temp){
			String property = pro.getProperty(key);
			Object value = defaultValue;
			if (!"".equals(property)) {
				value = property;
			}
			valueCache.put(key,value);
			return value;
		}else{
			return  temp;
		}
	}

}
