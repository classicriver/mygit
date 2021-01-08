package com.tongwei.facade;

import com.tongwei.hbase.config.HBaseSinkConfig;
import com.tongwei.schema.CascadeSchema;
import com.tongwei.strategy.CascadeStrategy;
/**
 * 
 * @author xiesc
 * @TODO 组串式逆变器
 * @time 2019年7月2日
 * @version 1.0
 */
public class CascadeFacade extends BaseFacade{
	
	public CascadeFacade(HBaseSinkConfig config){
		super(new CascadeStrategy(),new CascadeSchema(config),config);
	}
	
}
