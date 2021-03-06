package com.ptae.auth.mapper;

import com.ptae.auth.api.model.AppSms;
import com.ptae.auth.api.model.AppSmsExample;
import com.ptae.base.dao.BaseMapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppSmsMapper extends BaseMapper<AppSms>{
    long countByExample(AppSmsExample example);

    int deleteByExample(AppSmsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AppSms record);

    int insertSelective(AppSms record);

    List<AppSms> selectByExample(AppSmsExample example);

    AppSms selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AppSms record, @Param("example") AppSmsExample example);

    int updateByExample(@Param("record") AppSms record, @Param("example") AppSmsExample example);

    int updateByPrimaryKeySelective(AppSms record);

    int updateByPrimaryKey(AppSms record);
}