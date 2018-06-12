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
	/**
	 * 监听端口 
	 */
	private final static String SERVERPORT = "tw.server.port";
	/**
	 * rocketMQ nameserver
	 */
	private final static String NAMESERVER = "tw.rocketmq.nameServer";
	/**
	 * ocketMQ producerName 
	 */
	private final static String PRODUCERNAME = "tw.rocketmq.producerName";
	/**
	 * snowflake唯一id算法 机器标识，分布式部署的时候机器标识不能一样
	 */
	private final static String MACHINEID = "tw.snowflake.machineId";
	/**
	 * 序列化到本地磁盘的间隔时间，单位为秒
	 */
	private final static String REPEATINTERVAL = "tw.storage.repeatInterval";
	/**
	 * 序列化到本地磁盘的路径
	 */
	private final static String STORAGEPATH = "tw.storage.path";

	private final static Map<String, Object> valueCache = new HashMap<>();
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
		return Integer.parseInt((String) getOrSetDefault(MAXTHREADS, Runtime
				.getRuntime().availableProcessors() * 2));
	}

	public static int getServerPort() {
		return Integer.parseInt((String) getOrSetDefault(SERVERPORT, 10000));
	}

	public static String getNameServer() {
		return (String) getOrSetDefault(NAMESERVER, "");
	}

	public static String getProducerName() {
		return (String) getOrSetDefault(PRODUCERNAME, "");
	}

	public static int getMachineId() {
		return Integer.parseInt((String) getOrSetDefault(MACHINEID, 1));
	}

	public static int getRepeatInterval() {
		return Integer.parseInt((String) getOrSetDefault(REPEATINTERVAL, 60));
	}

	public static String getStoragePath() {
		return (String) getOrSetDefault(STORAGEPATH, "");
	}
	
	private static Object getOrSetDefault(String key, Object def) {
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

}
