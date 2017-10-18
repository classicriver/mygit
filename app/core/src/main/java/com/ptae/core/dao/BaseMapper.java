/**    
*/ 
package com.ptae.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ptae.core.model.Entity;
import com.ptae.core.model.EntityExample;

/**
 * @Description: TODO()
 * @author  xiesc
 * @date 2017年10月13日 
 * @version V1.0  
 */
public interface BaseMapper<E> {
	
	long countByExample(EntityExample example);

    int deleteByExample(EntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Entity record);

    int insertSelective(Entity record);

    List<E> selectByExample(EntityExample example);

    E selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Entity record, @Param("example") EntityExample example);

    int updateByExample(@Param("record") Entity record, @Param("example") EntityExample example);

    int updateByPrimaryKeySelective(Entity record);

    int updateByPrimaryKey(Entity record);
}
