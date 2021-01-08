package com.tw.consumer.strategy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 组串式逆变器数据计算策略
 * @author xiesc
 * @TODO
 * @time 2019年3月28日
 * @version 1.0
 */
public class CascadeStrategy extends AbstractStrategy{
	@SuppressWarnings("unchecked")
	@Override
	public void policy1(Map<String, Object> data) {
		// TODO Auto-generated method stub
		String esn = data.get("esn").toString();
		//离散率
		//CalDispersionRatio.calCascadeDispersionRatio(esn, data);
		//组串状态
		//mergeData(data,JudgeSeriesStatus.invertrtSeriesStatus(esn, data));
		List<BigDecimal> pvListByEsn = getDeviceInfoByEsn(esn,"pvList");
		int pvSum = pvListByEsn.stream().mapToInt(BigDecimal::intValue).sum();
		//pv容量
		data.put("pvSum", new BigDecimal(pvSum).divide(new BigDecimal("1000"),6, BigDecimal.ROUND_HALF_DOWN));
	}

}
