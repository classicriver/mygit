package com.tw.resources;

import java.util.HashMap;
import java.util.Map;

public class ConfigProperties extends PropertyResources{

	private final static String MAXTHREADS = "tw.analysizer.maxThreads";
	/**
	 * 监听端口
	 */
	private final static String SERVERPORT = "tw.server.port";
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
	/**
	 * rocketMQ nameserver
	 */
	private final static String NAMESERVER = "tw.rocketmq.nameServer";
	/**
	 * rocketMQ producerName
	 */
	private final static String PRODUCERNAME = "tw.rocketmq.producerName";

	private final static Map<String, Object> valueCache = new HashMap<>();

	private ConfigProperties() {
	}

	public static ConfigProperties getInstance() {
		return SingletonConfig.PRO;
	}

	private static class SingletonConfig {
		private static final ConfigProperties PRO = new ConfigProperties();
	}

	public int getMaxThreads() {
		return Integer.parseInt((String) getOrSetDefault(MAXTHREADS, Runtime
				.getRuntime().availableProcessors() * 2));
	}

	public int getServerPort() {
		return Integer.parseInt((String) getOrSetDefault(SERVERPORT, 10000));
	}

	public int getMachineId() {
		return Integer.parseInt((String) getOrSetDefault(MACHINEID, 1));
	}

	public int getRepeatInterval() {
		return Integer.parseInt((String) getOrSetDefault(REPEATINTERVAL, 60));
	}

	public String getStoragePath() {
		return (String) getOrSetDefault(STORAGEPATH, "");
	}

	public String getNameServer() {
		return (String) getOrSetDefault(NAMESERVER, "");
	}

	public String getProducerName() {
		return (String) getOrSetDefault(PRODUCERNAME, "");
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
}
