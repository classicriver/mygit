package com.ptae.auth.mapper;

import com.ptae.api.model.AppUser;
import com.ptae.api.model.AppUserExample;
import com.ptae.base.dao.BaseMapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppUserMapper extends BaseMapper<AppUser>{
    long countByExample(AppUserExample example);

    int deleteByExample(AppUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AppUser record);

    int insertSelective(AppUser record);

    List<AppUser> selectByExample(AppUserExample example);

    AppUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AppUser record, @Param("example") AppUserExample example);

    int updateByExample(@Param("record") AppUser record, @Param("example") AppUserExample example);

    int updateByPrimaryKeySelective(AppUser record);

    int updateByPrimaryKey(AppUser record);
}