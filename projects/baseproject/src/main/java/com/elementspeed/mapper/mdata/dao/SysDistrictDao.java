package com.elementspeed.mapper.mdata.dao;

import java.util.List;

import com.elementspeed.mapper.mdata.entity.SysDistrict;

/**
 * @ClassName: SysDistrictDao
 * @Description: TODO(区域Dao)
 * @author masn
 * @date 2018年8月4日 下午3:02:49
 */
public interface SysDistrictDao {
	
	/**
	 * @Title: insert
	 * @Description: TODO()
	 * @author masn
	 * @date 2018年8月4日 下午3:03:03
	 * @return void  返回类型
	 * @throws
	 */
	public void insert(SysDistrict sysDistrict);
	
	/**
	 * @Title: update
	 * @Description: TODO()
	 * @author masn
	 * @date 2018年8月4日 下午3:03:06
	 * @return void  返回类型
	 * @throws
	 */
	public void update(SysDistrict sysDistrict);
	
	/**
	 * @Title: getAll
	 * @Description: TODO()
	 * @author masn
	 * @date 2018年8月4日 下午3:22:57
	 * @return List<SysDistrict>  返回类型
	 * @throws
	 */
	public List<SysDistrict> getAll();
}