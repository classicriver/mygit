package com.tw.consumer.strategy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tongwei.conops.arithmetic.calculate.CalDispersionRatio;
import com.tongwei.conops.arithmetic.calculate.JudgeSeriesStatus;
/**
 * 汇流箱数据计算策略
 * @author xiesc
 * @TODO
 * @time 2019年3月28日
 * @version 1.0
 */
public class CombinerStrategy extends AbstractStrategy{

	@SuppressWarnings("unchecked")
	@Override
	public void policy1(Map<String, Object> data) {
		// TODO Auto-generated method stub
		String esn = data.get("esn").toString();
		//离散率
		CalDispersionRatio.calCombinerdcDispersionRatio(esn, data);
		List<BigDecimal> pvListByEsn = getDeviceInfoByEsn(esn,"pvList");
		int sum = pvListByEsn.stream().mapToInt(BigDecimal::intValue).sum();
		//pv容量
		data.put("pvSum", new BigDecimal(sum).divide(new BigDecimal("1000"),6, BigDecimal.ROUND_HALF_DOWN));
		//母线电流
		Map<String,BigDecimal> piList = (Map<String, BigDecimal>) data.get("piList");
		BigDecimal piSum = new BigDecimal("0");
		for( Map.Entry<String,BigDecimal> entry : piList.entrySet()){
			piSum = piSum.add(entry.getValue());
		}
		data.put("piSum", piSum);
		//组串状态
		mergeData(data,JudgeSeriesStatus.combinerdcSeriesStatus(esn, data));
	}

}
