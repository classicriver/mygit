/**    
*/ 
package com.ptae.base.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ptae.base.model.Entity;
import com.ptae.base.model.EntityExample;

/**
 * @Description: TODO()
 * @author  xiesc
 * @date 2017年10月13日 
 * @version V1.0  
 */
public interface BaseService<E> {
	
	long countByExample(EntityExample example);
	
	@Transactional
    int deleteByExample(EntityExample example);
	@Transactional
    int deleteByPrimaryKey(Long id);
	@Transactional
    int insert(Entity record);
	@Transactional
    int insertSelective(Entity record);

    List<E> selectByExample(EntityExample example);

    E selectByPrimaryKey(Long id);
    @Transactional
    int updateByExampleSelective(@Param("record") Entity record, @Param("example") EntityExample example);
    @Transactional
    int updateByExample(@Param("record") Entity record, @Param("example") EntityExample example);
    @Transactional
    int updateByPrimaryKeySelective(Entity record);
    @Transactional
    int updateByPrimaryKey(Entity record);
}
