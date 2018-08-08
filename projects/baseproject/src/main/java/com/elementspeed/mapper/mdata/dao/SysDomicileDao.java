package com.elementspeed.mapper.mdata.dao;

import java.util.List;

import com.elementspeed.mapper.mdata.entity.SysDomicile;

/**
 * @ClassName: SysDomicileDao
 * @Description: TODO(居住地Dao)
 * @author masn
 * @date 2018年8月4日 下午3:08:01
 */
public interface SysDomicileDao{
	
	/**
	 * @Title: insert
	 * @Description: TODO()
	 * @author masn
	 * @date 2018年8月4日 下午3:08:12
	 * @return void  返回类型
	 * @throws
	 */
	public void insert(SysDomicile sysDomicile);
	
	/**
	 * @Title: update
	 * @Description: TODO()
	 * @author masn
	 * @date 2018年8月4日 下午3:08:15
	 * @return void  返回类型
	 * @throws
	 */
	public void update(SysDomicile sysDomicile);
	
	/**
	 * @Title: getByType
	 * @Description: TODO(根据类型获取居住地)
	 * @author masn
	 * @date 2018年8月4日 下午3:22:57
	 * @return List<SysDomicile>  返回类型
	 * @throws
	 */
	public List<SysDomicile> getByType(Integer domicileType);
	
	/**
	 * @Title: findById
	 * @Description: TODO()
	 * @author masn
	 * @date 2018年8月5日 上午12:29:43
	 * @return SysDomicile  返回类型
	 * @throws
	 */
	public SysDomicile findById(String id);
}