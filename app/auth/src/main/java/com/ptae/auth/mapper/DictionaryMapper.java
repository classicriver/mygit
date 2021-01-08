package com.ptae.auth.mapper;

import com.ptae.auth.api.model.Dictionary;
import com.ptae.auth.api.model.DictionaryExample;
import com.ptae.base.dao.BaseMapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DictionaryMapper extends BaseMapper<Dictionary> {
    long countByExample(DictionaryExample example);

    int deleteByExample(DictionaryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    List<Dictionary> selectByExample(DictionaryExample example);

    Dictionary selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Dictionary record, @Param("example") DictionaryExample example);

    int updateByExample(@Param("record") Dictionary record, @Param("example") DictionaryExample example);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);
}