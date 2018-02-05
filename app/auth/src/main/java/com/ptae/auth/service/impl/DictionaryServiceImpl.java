/**   
*/ 
package com.ptae.auth.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ptae.auth.api.model.Dictionary;
import com.ptae.auth.api.model.DictionaryExample;
import com.ptae.auth.common.CommonUtils;
import com.ptae.auth.mapper.DictionaryMapper;
import com.ptae.auth.service.DictionaryService;
import com.ptae.base.service.impl.BaseServiceImpl;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年12月5日 
* @version V1.0   
 */
//@Service
public class DictionaryServiceImpl extends BaseServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService{
	
	private Map<String,List<Dictionary>> dictCache = new HashMap<>();
	/* (non-Javadoc)
	 * @see com.ptae.auth.service.DictionaryService#getCode(java.lang.String, java.lang.String)
	 */
	@Override
	public String getKey(String dictType, String value) {
		// TODO Auto-generated method stub
		List<Dictionary> data = dictCache.get(dictType);
		if(CommonUtils.isNullOrEmpty(data)) {
			//从数据库读取
			DictionaryExample example = new DictionaryExample();
			example.createCriteria().andCodeEqualTo(dictType);
			List<Dictionary> dictionaries = baseMapper.selectByExample(example);
			//放入cache
			String key = findKey(dictionaries,value);
			dictCache.put(dictType, dictionaries);
			return key;
		}else {
			//从cache中获取
			return findKey(data,value);
		}
	}

	/* (non-Javadoc)
	 * @see com.ptae.auth.service.DictionaryService#getValue(java.lang.String, java.lang.String)
	 */
	@Override
	public String getValue(String dictType, String code) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 根据传进来的值获取字典编码
	 *@Description: TODO
	 */
	private String findKey(List<Dictionary> list,String value) {
		for(Dictionary dict : list) {
			if(value.equals(dict.getValue())) {
				return dict.getKey();
			}
		}
		return null;
	}
	/**
	 * 根据字典编码获取值
	 *@Description: TODO
	 */
	private String findValue(List<Dictionary> list,String key) {
		for(Dictionary dict : list) {
			if(key.equals(dict.getKey())) {
				return dict.getValue();
			}
		}
		return null;
	}
}
