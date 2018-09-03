package com.tw.consumer.dao.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tw.consumer.core.SingleBeanFactory;
import com.tw.consumer.dao.BaseDao;

/**
 * @author xiesc
 * @TODO dao工厂类
 * @time 2018年8月2日
 * @version 1.0
 */
public class DaoFactory extends SingleBeanFactory {

	public <T> T getDao(Class<T> clazz) {
		T dao = super.getBean(clazz);
		if (dao instanceof BaseDao) {
			return dao;
		}
		return null;
	}

	public List<BaseDao<?>> getAllDaos() {
		List<BaseDao<?>> list = new ArrayList<>();
		Iterator<String> it = beans.keySet().iterator();
		while (it.hasNext()) {
			Object object = beans.get(it.next());
			if (object instanceof BaseDao) {
				list.add((BaseDao<?>) object);
			}
		}
		return list;
	}

}
