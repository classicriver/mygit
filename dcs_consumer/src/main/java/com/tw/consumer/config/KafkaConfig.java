package com.tw.consumer.config;

import com.tw.consumer.resources.PropertyResources;
/**
 * 
 * @author xiesc
 * @TODO kafka配置类
 * @time 2018年8月16日
 * @version 1.0
 */
public class KafkaConfig extends PropertyResources{
	
	@Override
	protected String getProFileName() {
		// TODO Auto-generated method stub
		return "kafka.properties";
	}
}
