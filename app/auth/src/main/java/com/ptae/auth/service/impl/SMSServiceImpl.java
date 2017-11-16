package com.ptae.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ptae.auth.api.model.AppSms;
import com.ptae.auth.api.model.AppSmsExample;
import com.ptae.auth.api.model.AppSmsExample.Criteria;
import com.ptae.auth.api.model.AppUserExample;
import com.ptae.auth.common.TimeUtils;
import com.ptae.auth.mapper.AppSmsMapper;
import com.ptae.auth.mapper.AppUserMapper;
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
	private AppUserMapper userMapper;
	
	//@Value("${ptae.sms.maxcount}")
	//private int maxCount;//当日可发短信总数
	@Value("${ptae.sms.userMaxCount}")
	private int userMaxCount;//用户当日可发短信数
	@Value("${ptae.sms.maxUser}")
	private int maxUser;//最大用户数
	@Value("${ptae.sms.userMaxCountMsg}")
	private String userMaxCountMsg;//用户当日可发短信数提示信息
	@Value("${ptae.sms.maxUserMsg}")
	private String maxUserMsg;//最大用户数提示信息
	/* (non-Javadoc)
	 * @see com.ptae.auth.service.SMSService#canSent()
	 */
	@Override
	public String canSent(String phoneNumber) {
		// TODO Auto-generated method stub
		String messages = "";
		try {
			AppSmsExample e = new AppSmsExample();
			Criteria criteria = e.createCriteria();
			criteria.andSentTimeGreaterThanOrEqualTo(TimeUtils.getTodayZeroTime());
			/*
			long count = baseMapper.countByExample(e);
			if(count > maxCount){
				messages = "本日可发短信数已用完。";
			}*/
			criteria.andUserAccountEqualTo(phoneNumber);
			long todayCount = baseMapper.countByExample(e);
			//用户当日可发短信数
			if(todayCount >= userMaxCount){
				messages = userMaxCountMsg;
			}
			AppUserExample userExmaole = new AppUserExample(); 
			userExmaole.createCriteria().andUserAccountIsNotNull();
			long userCount = userMapper.countByExample(userExmaole);
			//最大用户数
			if(userCount >= maxUser){
				messages = maxUserMsg;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return messages;
	}
	
}
