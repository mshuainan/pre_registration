package com.elementspeed.mapper.mdata.dao;

import java.util.List;

import com.elementspeed.mapper.mdata.entity.SysSchool;

/**
 * @ClassName: SysSchoolDao
 * @Description: TODO(学校Dao)
 * @author masn
 * @date 2018年8月4日 下午3:05:50
 */
public interface SysSchoolDao{
	
	/**
	 * @Title: insert
	 * @Description: TODO()
	 * @author masn
	 * @date 2018年8月4日 下午3:06:06
	 * @return void  返回类型
	 * @throws
	 */
	public void insert(SysSchool sysSchool);
	
	/**
	 * @Title: update
	 * @Description: TODO()
	 * @author masn
	 * @date 2018年8月4日 下午3:06:11
	 * @return void  返回类型
	 * @throws
	 */
	public void update(SysSchool sysSchool);
	
	/**
	 * @Title: getAll
	 * @Description: TODO()
	 * @author masn
	 * @date 2018年8月4日 下午3:22:57
	 * @return List<SysSchool>  返回类型
	 * @throws
	 */
	public List<SysSchool> getAll();
}