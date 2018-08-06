package com.tw.consumer.config;

import java.util.HashMap;
import java.util.Map;

import com.tw.consumer.resources.PropertyResources;

/**
 * 
 * @author xiesc
 * @TODO 全局配置文件
 * @time 2018年5月14日
 * @version 1.0
 */
public class Config extends PropertyResources{
	
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
	
	private final static String BATCHNUMBER = "tw.mysql.batchNumber";

	private final static Map<String, Object> valueCache = new HashMap<>();

	private Config() {
	}

	public static Config getInstance() {
		return SingletonConfig.PRO;
	}

	private static class SingletonConfig {
		private static final Config PRO = new Config();
	}

	public int getMaxThreads() {
		return Integer.parseInt((String) getOrSetDefaultProperty(MAXTHREADS, Runtime
				.getRuntime().availableProcessors() * 2));
	}

	public int getServerPort() {
		return Integer.parseInt((String) getOrSetDefaultProperty(SERVERPORT, 10000));
	}

	public String getNameServer() {
		return (String) getOrSetDefaultProperty(NAMESERVER, "");
	}

	public String getProducerName() {
		return (String) getOrSetDefaultProperty(PRODUCERNAME, "");
	}

	public int getMachineId() {
		return Integer.parseInt((String) getOrSetDefaultProperty(MACHINEID, 1));
	}

	public int getRepeatInterval() {
		return Integer.parseInt((String) getOrSetDefaultProperty(REPEATINTERVAL, 60));
	}

	public String getStoragePath() {
		return (String) getOrSetDefaultProperty(STORAGEPATH, "");
	}
	
	public int getBatchNumber(){
		return Integer.parseInt((String) getOrSetDefaultProperty(BATCHNUMBER, 50));
	}
	
	private Object getOrSetDefaultProperty(String key, Object def) {
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
