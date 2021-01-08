package com.justbon.monitor.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.justbon.monitor.entity.Recorder;
/**
 * 
 * @author xiesc
 * @date 2020年4月28日
 * @version 1.0.0
 * @Description: TODO 记录采集到的应用信息
 */
public class APPRecorderContainer {
	
	private static final ConcurrentHashMap<Integer, Recorder> recorders = new ConcurrentHashMap<>();
	
	public static void saveRecorde(int methodId,Recorder recorde){
		recorders.put(methodId, recorde);
	}
	
	public static Map<Integer, Recorder> getRecorders(){
		return recorders;
	}
	
	public static Recorder getRecorder(int methodId){
		return recorders.get(methodId);
	}
}
