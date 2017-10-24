/**    
*/ 
package com.ptae.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ptae.base.dao.BaseMapper;
import com.ptae.base.model.Entity;
import com.ptae.base.model.EntityExample;
import com.ptae.base.service.BaseService;

/**
 * @Description: TODO()
 * @author  xiesc
 * @date 2017年10月13日 
 * @version V1.0  
 * @param <E>
 */
public class BaseServiceImpl<M extends BaseMapper<E>, E> implements BaseService<E>{
	
	@Autowired
	protected M baseMapper;

	/**
	 * @param mapper the mapper to set
	 */
	//public abstract void setMapper();

	/* (non-Javadoc)
	 * @see com.ptae.core.service.BaseService#countByExample(com.ptae.core.model.EntityExample)
	 */
	@Override
	public long countByExample(EntityExample example) {
		// TODO Auto-generated method stub
		return baseMapper.countByExample(example);
	}

	/* (non-Javadoc)
	 * @see com.ptae.core.service.BaseService#deleteByExample(com.ptae.core.model.EntityExample)
	 */
	@Override
	public int deleteByExample(EntityExample example) {
		// TODO Auto-generated method stub
		return baseMapper.deleteByExample(example);
	}

	/* (non-Javadoc)
	 * @see com.ptae.core.service.BaseService#deleteByPrimaryKey(java.lang.Long)
	 */
	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return baseMapper.deleteByPrimaryKey(id);
	}

	/* (non-Javadoc)
	 * @see com.ptae.core.service.BaseService#insert(com.ptae.core.model.Entity)
	 */
	@Override
	public int insert(Entity record) {
		// TODO Auto-generated method stub
		return baseMapper.insert(record);
	}

	/* (non-Javadoc)
	 * @see com.ptae.core.service.BaseService#insertSelective(com.ptae.core.model.Entity)
	 */
	@Override
	public int insertSelective(Entity record) {
		// TODO Auto-generated method stub
		return baseMapper.insertSelective(record);
	}

	/* (non-Javadoc)
	 * @see com.ptae.core.service.BaseService#selectByExample(com.ptae.core.model.EntityExample)
	 */
	@Override
	public List<E> selectByExample(EntityExample example) {
		// TODO Auto-generated method stub
		return baseMapper.selectByExample(example);
	}

	/* (non-Javadoc)
	 * @see com.ptae.core.service.BaseService#selectByPrimaryKey(java.lang.Long)
	 */
	@Override
	public E selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return baseMapper.selectByPrimaryKey(id);
	}

	/* (non-Javadoc)
	 * @see com.ptae.core.service.BaseService#updateByExampleSelective(com.ptae.core.model.Entity, com.ptae.core.model.EntityExample)
	 */
	@Override
	public int updateByExampleSelective(Entity record, EntityExample example) {
		// TODO Auto-generated method stub
		return baseMapper.updateByExampleSelective(record, example);
	}

	/* (non-Javadoc)
	 * @see com.ptae.core.service.BaseService#updateByExample(com.ptae.core.model.Entity, com.ptae.core.model.EntityExample)
	 */
	@Override
	public int updateByExample(Entity record, EntityExample example) {
		// TODO Auto-generated method stub
		return baseMapper.updateByExample(record, example);
	}

	/* (non-Javadoc)
	 * @see com.ptae.core.service.BaseService#updateByPrimaryKeySelective(com.ptae.core.model.Entity)
	 */
	@Override
	public int updateByPrimaryKeySelective(Entity record) {
		// TODO Auto-generated method stub
		return baseMapper.updateByPrimaryKeySelective(record);
	}

	/* (non-Javadoc)
	 * @see com.ptae.core.service.BaseService#updateByPrimaryKey(com.ptae.core.model.Entity)
	 */
	@Override
	public int updateByPrimaryKey(Entity record) {
		// TODO Auto-generated method stub
		return baseMapper.updateByPrimaryKey(record);
	}
}
