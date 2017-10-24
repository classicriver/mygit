package com.ptae.auth.mapper;

import com.ptae.auth.model.AppAddress;
import com.ptae.auth.model.AppAddressExample;
import com.ptae.core.dao.BaseMapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

<<<<<<< HEAD
public interface AppAddressMapper extends BaseMapper<AppAddress>{
=======
public interface AppAddressMapper extends BaseMapper<AppAddress> {
>>>>>>> branch 'master' of https://github.com/classicriver/mygit
    long countByExample(AppAddressExample example);

    int deleteByExample(AppAddressExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AppAddress record);

    int insertSelective(AppAddress record);

    List<AppAddress> selectByExample(AppAddressExample example);

    AppAddress selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AppAddress record, @Param("example") AppAddressExample example);

    int updateByExample(@Param("record") AppAddress record, @Param("example") AppAddressExample example);

    int updateByPrimaryKeySelective(AppAddress record);

    int updateByPrimaryKey(AppAddress record);
}