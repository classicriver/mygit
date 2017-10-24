/**   
* @Description: TODO(用一句话描述该文件做什么) 
* @author  
* @date 2017年10月16日 
* @version V1.0   
*/ 
package com.ptae.auth.service.impl;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ptae.auth.common.TimeUtils;
import com.ptae.auth.mapper.AppSmsMapper;
import com.ptae.api.model.AppSms;
import com.ptae.api.model.AppSmsExample;
import com.ptae.auth.service.SMSService;
import com.ptae.base.service.impl.BaseServiceImpl;

/**
 * @Description: TODO()
 * @author  xiesc
 * @date 2017年10月16日 
 * @version V1.0  
 */
@Service
public class SMSServiceImpl extends BaseServiceImpl<AppSmsMapper, AppSms> implements SMSService{
	
	@Autowired
	private AppSmsMapper mapper;
	
	@Value("${ptae.sms.maxcount}")
	private int maxcount;//当日可发最大短信数
	/* (non-Javadoc)
	 * @see com.ptae.auth.service.SMSService#canSent()
	 */
	@Override
	public boolean canSent() {
		// TODO Auto-generated method stub
		try {
			AppSmsExample e = new AppSmsExample();
			e.createCriteria().andSentTimeGreaterThanOrEqualTo(TimeUtils.getTodayZeroTime());
			long count = mapper.countByExample(e);
			if(count < maxcount){
				return true;
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return false;
	}
	
}
