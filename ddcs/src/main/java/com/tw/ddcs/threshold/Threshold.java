package com.tw.ddcs.threshold;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.tw.ddcs.model.ThresholdModel;
import com.tw.ddcs.resources.PropertyResources;
/**
 * 
 * @author xiesc
 * @TODO 遥测阀值
 * @time 2018年7月30日
 * @version 1.0
 */
public class Threshold extends PropertyResources{
	
	private final Map<String,ThresholdModel> thMap = new HashMap<>();
	
	{
		Set<Object> keySet = pro.keySet();
		Iterator<Object> it = keySet.iterator();
		while(it.hasNext()){
			String key = (String) it.next();
			String proValue = (String) pro.get(key);
			String[] split = proValue.split(",");
			ThresholdModel model = new ThresholdModel();
			model.setKey(key);
			model.setValue(BigDecimal.valueOf(Double.valueOf(split[1].trim())));
			switch(split[0].trim()){
			case "=" :
				model.setType(CompareType.equal);
				break;
			case "<" :
				model.setType(CompareType.lessThan);
				break;
			case ">" :
				model.setType(CompareType.moreThan);
				break;
			case "<=" :
				model.setType(CompareType.lessThanAndEqual);
				break;
			case ">=" :
				model.setType(CompareType.moreThanAndEqual);
				break;
			default :
				break;
			}
			thMap.put(key, model);
		}
	}

	@Override
	protected String getProFileName() {
		// TODO Auto-generated method stub
		return "threshold.properties";
	}
	
	public void validate(Map<String,Object> map){
		Set<String> keySet = map.keySet();
		Iterator<String> it = keySet.iterator();
		while(it.hasNext()){
			String key = it.next();
			Object object = map.get(key);
			thMap.get(key);
		}
	}
	
}
