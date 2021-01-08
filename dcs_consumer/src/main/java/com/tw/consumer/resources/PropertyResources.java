package com.tw.consumer.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.tw.consumer.log.LogFactory;
/**
 * 
 * @author xiesc
 * @TODO	Property文件加载类
 * @time 2018年7月20日
 * @version 1.0
 */
public abstract class PropertyResources implements Resources{

	protected final Properties pro = new Properties();

	protected PropertyResources(){
		load();
	}
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		try(InputStream stream = PropertyResources.class.getClassLoader().getResourceAsStream(getProFileName())) {
			pro.load(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogFactory.getLogger().error("exception happened.",e.getMessage());
		}
	}
	
	protected abstract String getProFileName();
}
