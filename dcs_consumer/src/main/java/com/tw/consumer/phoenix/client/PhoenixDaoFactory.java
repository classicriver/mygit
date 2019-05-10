package com.tw.consumer.phoenix.client;

import com.tw.consumer.core.SingleBeanFactory;
import com.tw.consumer.device.dao.Dao;
import com.tw.consumer.model.GenericDeviceModel;
import com.tw.consumer.utils.DeviceType;

public class PhoenixDaoFactory implements Dao<GenericDeviceModel>{
	
	private final CascadePhoenixDao caDao = SingleBeanFactory.getBean(CascadePhoenixDao.class);
	private final CombinerPhoenixDao coDao = SingleBeanFactory.getBean(CombinerPhoenixDao.class);
	
	private PhoenixJDBC getPhoenixDaoByType(DeviceType type){
		switch (type) {
		case CASCADE:
			return caDao;
		case COMBINER:
			return coDao;
		default:
			return null;
		}
	}

	@Override
	public void add(GenericDeviceModel data) {
		// TODO Auto-generated method stub
		PhoenixJDBC dao = getPhoenixDaoByType(data.getType());
		if(null != dao){
			dao.add(data.getValue());
		}
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
