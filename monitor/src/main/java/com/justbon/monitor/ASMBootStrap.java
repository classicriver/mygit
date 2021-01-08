package com.justbon.monitor;

import com.justbon.monitor.asm.AgentFilter;
import com.justbon.monitor.config.PropertiesConfig;
import com.justbon.monitor.log.LogFactory;
import com.justbon.monitor.scheduler.AgentRecorderScheduler;

public class ASMBootStrap extends AbstractBootStrap{

	private  final String configFilePath;
	private final AgentRecorderScheduler agentScheduler = new AgentRecorderScheduler();
	
	public ASMBootStrap(String configFilePath){
		this.configFilePath = configFilePath;
	}
	
	@Override
	public boolean init() {
		if (!PropertiesConfig.getInstance().initProperties(configFilePath)) {
			LogFactory.error(" Properties init FAILURE!!!");
			return false;
		}
		if (!AgentFilter.init()) {
			LogFactory.error(" Filter init FAILURE!!!");
			return false;
		}
		
		if(!agentScheduler.init()){
			LogFactory.error(" AgentScheduler init FAILURE!!!");
			return false;
		}
		return true;
	}

}
