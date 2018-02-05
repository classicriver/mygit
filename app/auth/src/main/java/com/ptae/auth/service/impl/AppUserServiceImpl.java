/**    
*/ 
package com.ptae.auth.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ptae.auth.api.model.AppUser;
import com.ptae.auth.api.model.AppUserExample;
import com.ptae.auth.mapper.AppUserMapper;
import com.ptae.auth.service.AppUserService;
import com.ptae.base.service.impl.BaseServiceImpl;

/**
 * @Description: TODO()
 * @author  xiesc
 * @date 2017年10月12日 
 * @version V1.0  
 */
@Service
public class AppUserServiceImpl extends BaseServiceImpl<AppUserMapper, AppUser> implements AppUserService{
	@Override
	public int saveOrUpdateUser(AppUser user){
		AppUserExample example = new AppUserExample();
		example.createCriteria().andAccountEqualTo(user.getAccount()).andTypeEqualTo(user.getType());
		List<AppUser> userList = baseMapper.selectByExample(example);
		
		if(userList.size() > 0){
			AppUser appUser = userList.get(0);
			appUser.setLogintime(new Date());
			return baseMapper.updateByExample(appUser, example);
		}else{
			user.setLogintime(new Date());
			return baseMapper.insert(user);
		}
	}
}
