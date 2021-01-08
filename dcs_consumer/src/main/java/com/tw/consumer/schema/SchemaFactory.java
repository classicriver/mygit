package com.tw.consumer.schema;

import com.tw.consumer.core.SingleBeanFactory;
import com.tw.consumer.utils.DeviceType;

public class SchemaFactory extends SingleBeanFactory{

	public TwAbstractSchema getSchema(DeviceType type){
		TwAbstractSchema schema = null;
		switch(type){
		case CASCADE:
			schema = super.getBean(CascadeSchema.class);
			break;
		case CENTRALIZED:
			schema = super.getBean(CentralizedSchema.class);
			break;
		case COMBINER:
			schema = super.getBean(CombinerdcSchema.class);
			break;
		case ENVIR:
			schema = super.getBean(EnvirSchema.class);
			break;
		default :
			break;
		}
		return schema;
	}
}
