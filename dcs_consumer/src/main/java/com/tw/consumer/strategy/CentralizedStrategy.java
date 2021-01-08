package com.tw.consumer.strategy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 集中式逆变器数据计算策略
 * @author xiesc
 * @TODO
 * @time 2019年3月28日
 * @version 1.0
 */
public class CentralizedStrategy extends AbstractStrategy{

	@Override
	public void policy1(Map<String, Object> data) {
		// TODO Auto-generated method stub
		String esn = data.get("esn").toString();
		List<BigDecimal> listByEsn = getDeviceInfoByEsn(esn,"pvList");
		int sum = listByEsn.stream().mapToInt(BigDecimal::intValue).sum();
		//pv容量
		data.put("pvSum", new BigDecimal(sum).divide(new BigDecimal("1000"),6, BigDecimal.ROUND_HALF_DOWN));
	}

}
