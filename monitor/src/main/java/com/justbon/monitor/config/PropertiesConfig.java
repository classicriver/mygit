package com.justbon.monitor.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.justbon.monitor.log.LogFactory;

/**
 * @author xiesc
 * @date 2020年4月22日
 * @version 1.0.0
 * @Description: TODO配置文件
 */
public class PropertiesConfig {

	private static Properties pro = new Properties();
	
	private static class SingletonPropertiesConfig {
		private static final PropertiesConfig config = new PropertiesConfig();
	}

	public static PropertiesConfig getInstance() {
		return SingletonPropertiesConfig.config;
	}

	private PropertiesConfig() {
		// empty
	}

	public boolean initProperties(String configFilePath) {
		try (InputStream resourceAsStream = new FileInputStream(configFilePath)) {
			pro.load(resourceAsStream);
			if(!pro.isEmpty()){
				LogFactory.info("Properties init success!");
				return true;
			}
			return false;
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}

	/*private String getConfigFileDir(String configFilePath) {
		if (System.getProperty("os.name").contains("dows")) {
			int idx = configFilePath.lastIndexOf('\\');
			return configFilePath.substring(0, idx + 1);
		}
		int idx = configFilePath.lastIndexOf('/');
		return configFilePath.substring(0, idx + 1);
	}*/

	public String getStr(String key) {
		checkState();

		String value = System.getProperty(key);
		if (value != null) {
			return value;
		}
		return pro.getProperty(key);
	}

	private void checkState() {
		if (pro.isEmpty()) {
			throw new IllegalStateException("Properties is not initial yet!!!");
		}
	}

	public void setStr(String key, String value) {
		checkState();

		System.setProperty(key, value);
		pro.setProperty(key, value);
	}

	public String getStr(String key, String defaultValue) {
		checkState();

		String result = getStr(key);
		if (result != null) {
			return result;
		}
		return defaultValue;
	}

	public int getInt(String key, int defaultValue) {
		checkState();

		String result = getStr(key);
		if (result == null) {
			return defaultValue;
		}

		try {
			return Integer.parseInt(result);
		} catch (Exception e) {
			LogFactory.error("MyProperties.getInt(" + key + ", " + defaultValue + ")", e);
		}
		return defaultValue;
	}

	public long getLong(String key, long defaultValue) {
		checkState();

		String result = getStr(key);
		if (result == null) {
			return defaultValue;
		}

		try {
			return Long.parseLong(result);
		} catch (Exception e) {
			LogFactory.error("MyProperties.getLong(" + key + ", " + defaultValue + ")", e);
		}
		return defaultValue;
	}

	public long getLong(String key, long defaultValue, long minValue) {
		checkState();

		long result = getLong(key, defaultValue);
		if (result <= minValue) {
			return minValue;
		}
		return result;
	}

	public boolean isSame(String key, String expectValue) {
		checkState();

		if (expectValue == null) {
			throw new IllegalArgumentException("isSame(" + key + ", null): expectValue must not null!!!");
		}
		return expectValue.equals(getStr(key));
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		checkState();

		String result = getStr(key);
		if (result != null) {
			return result.equalsIgnoreCase("true");
		}
		return defaultValue;
	}
	
	public Properties getProperties(){
		return pro;
	}

}
