package com.tongwei.facade;

import com.tongwei.hbase.config.HBaseSinkConfig;
import com.tongwei.schema.CombinerdcSchema;
import com.tongwei.strategy.CombinerStrategy;
/**
 * 
 * @author xiesc
 * @TODO 汇流箱
 * @time 2019年7月2日
 * @version 1.0
 */
public class CombinerdcFacade extends BaseFacade{
	
	public CombinerdcFacade(HBaseSinkConfig config){
		super(new CombinerStrategy(),new CombinerdcSchema(config),config);
	}
	
}
