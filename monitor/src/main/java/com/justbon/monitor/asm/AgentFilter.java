package com.justbon.monitor.asm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.justbon.monitor.config.PropertiesConfig;
import com.justbon.monitor.constants.PropertyKeys;
import com.justbon.monitor.log.LogFactory;

/**
 * 
 * @author xiesc
 * @date 2020年4月23日
 * @version 1.0.0
 * @Description: TODO 过滤出要agent的类和方法
 */
public class AgentFilter {
	
	private static Map<String,List<String>> cache = new HashMap<>();
	
	public static boolean init(){
		try{
			for(String klass : PropertiesConfig.getInstance().getStr(PropertyKeys.MONITOR_AGENT_KLASSES).split(",")){
				String className = klass.split(":")[0];
				String methodName = klass.split(":")[1];
				List<String> methodList = cache.get(className);
				if(null != methodList && methodList.size() > 0){
					methodList.add(methodName);
				}else{
					methodList = new ArrayList<>();
					methodList.add(methodName);
					cache.put(className, methodList);
				}
			}
			LogFactory.info("AgentFilter init success!");
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean needInject(String klassName){
		if(null != cache.get(klassName)){
			return true;
		}
		return false;
	}
	
	public static List<String> getInjectMethods(String klassName){
		return cache.get(klassName);
	}
}
