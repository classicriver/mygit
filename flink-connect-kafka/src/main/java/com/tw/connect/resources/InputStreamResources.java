package com.tw.connect.resources;

import java.io.IOException;
import java.io.InputStream;
/**
 * 
 * @author xiesc
 * @TODO 文件加载类
 * @time 2018年7月20日
 * @version 1.0
 */
public abstract class InputStreamResources implements Resources {

	
	protected InputStreamResources(){
		load();
	}
	
	@Override
	public void load() {
		try(InputStream stream = InputStreamResources.class.getClassLoader().getResourceAsStream(getFileName())){
			init(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public abstract void init(InputStream stream);
	
	/**
	 * @return classpath相对路径
	 */
	protected abstract String getFileName();
}
