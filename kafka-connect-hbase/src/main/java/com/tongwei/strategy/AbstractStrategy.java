package com.tongwei.strategy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.tongwei.conops.arithmetic.calculate.CalDispersionRatio;

public abstract class AbstractStrategy implements Strategy {

	@Override
	public void policy(Map<String, Object> data) {
		// TODO Auto-generated method stub
		policy1(data);
	}

	/**
	 * 合并组串状态和遥测map的数据
	 * 
	 * @param yc 遥测map
	 * @param jss 组串状态map
	 * @return
	 */
	protected void mergeData(Map<String, Object> yc, Map<Object, Object> jss) {
		for (Map.Entry<Object, Object> m : jss.entrySet()) {
			yc.put("st" + m.getKey().toString(), m.getValue().toString());
		}
	}

	protected List<BigDecimal> getDeviceInfoByEsn(String esn,String key) {
		return JSON.parseArray(
				CalDispersionRatio.getDeviceInfoByEsn(esn).get(key)
						.toString(), BigDecimal.class);
	}

	protected abstract void policy1(Map<String, Object> data);

}
