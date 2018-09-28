package com.tw.connect.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.tw.connect.log.LogFactory;
/**
 * 
 * @author xiesc
 * @TODO	Property文件加载类
 * @time 2018年7月20日
 * @version 1.0
 */
public abstract class PropertyResources implements Resources{

	protected final Properties pro = new Properties();
	private InputStream stream;

	protected PropertyResources(){
		load();
	}
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		stream = PropertyResources.class.getClassLoader().getResourceAsStream(getProFileName());
		try {
			pro.load(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogFactory.getLogger().error("exception happened.",e.getMessage());
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
	
	protected abstract String getProFileName();
}
