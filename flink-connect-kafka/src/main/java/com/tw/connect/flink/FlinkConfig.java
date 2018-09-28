package com.tw.connect.flink;

import com.tw.connect.resources.PropertyResources;

public abstract class FlinkConfig extends PropertyResources {

	@Override
	protected String getProFileName() {
		// TODO Auto-generated method stub
		return "flink.properties";
	}

	public void start() {
		init();
	}

	abstract void init();

}
