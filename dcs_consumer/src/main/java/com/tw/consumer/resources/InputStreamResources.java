package com.tw.consumer.resources;

import java.io.InputStream;
/**
 * 
 * @author xiesc
 * @TODO 文件加载类
 * @time 2018年7月20日
 * @version 1.0
 */
public abstract class InputStreamResources implements Resources {

	protected InputStream stream;
	
	protected InputStreamResources(){
		load();
	}
	
	@Override
	public void load() {
		stream = InputStreamResources.class.getClassLoader().getResourceAsStream(getFileName());
	}

	public abstract void close();
	
	/**
	 * @return classpath相对路径
	 */
	protected abstract String getFileName();
}
