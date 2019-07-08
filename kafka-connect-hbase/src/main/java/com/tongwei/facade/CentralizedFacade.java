package com.tongwei.facade;

import com.tongwei.hbase.config.HBaseSinkConfig;
import com.tongwei.schema.CentralizedSchema;
import com.tongwei.strategy.CentralizedStrategy;
/**
 * 
 * @author xiesc
 * @TODO 集中式逆变器
 * @time 2019年7月2日
 * @version 1.0
 */
public class CentralizedFacade extends BaseFacade{
	
	public CentralizedFacade(HBaseSinkConfig config){
		super(new CentralizedStrategy(),new CentralizedSchema(config),config);
	}
	
}
