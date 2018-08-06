package com.tw.consumer.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * 
 * @author xiesc
 * @TODO 文件加载类
 * @time 2018年7月20日
 * @version 1.0
 */
public abstract class FileResources implements Resources {

	protected FileInputStream stream;
	
	protected FileResources(){
		load();
	}
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		try {
			stream = new FileInputStream(FileResources.class
					.getResource("/").getPath() + getFileName());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	/**
	 * @return classpath相对路径
	 */
	protected abstract String getFileName();
}
