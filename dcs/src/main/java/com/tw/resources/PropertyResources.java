package com.tw.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * 
 * @author xiesc
 * @TODO	Property文件加载类
 * @time 2018年7月20日
 * @version 1.0
 */
public abstract class PropertyResources implements Resources{

	protected Properties pro = new Properties();
	private InputStream stream;
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		stream = PropertyResources.class.getClassLoader().getResourceAsStream(getProFileName());
		try {
			pro.load(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close();
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		try {
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected PropertyResources (){
		load();
	}
	
	protected abstract String getProFileName();
}
