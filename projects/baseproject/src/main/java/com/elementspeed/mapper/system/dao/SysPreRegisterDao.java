package com.elementspeed.mapper.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.elementspeed.mapper.system.entity.SysPreRegister;

/**
 * @ClassName: SysPreRegisterDao
 * @Description: TODO(报名Dao接口)
 * @author masn
 * @date 2018年8月4日 上午10:37:28
 */
public interface SysPreRegisterDao{
	
	/**
	 * @Title: insert
	 * @Description: TODO()
	 * @author masn
	 * @date 2018年8月3日 下午9:52:40
	 * @return void  返回类型
	 * @throws
	 */
	public void insert(SysPreRegister po);
	
	/**
	 * @Title: update
	 * @Description: TODO()
	 * @author masn
	 * @date 2018年8月3日 下午9:52:43
	 * @return void  返回类型
	 * @throws
	 */
	public void update(SysPreRegister po);

	/**
	 * @Title: query
	 * @Description: TODO(查询结果)
	 * @author masn
	 * @date 2018年8月3日 下午11:36:15
	 * @return List<SysPreRegister>  返回类型
	 * @throws
	 */
	List<SysPreRegister> query(SysPreRegister user);
	
	/**
	 * @Title: findById
	 * @Description: TODO()
	 * @author masn
	 * @date 2018年8月4日 上午10:37:06
	 * @return SysPreRegister  返回类型
	 * @throws
	 */
	SysPreRegister findById(@Param(value = "id") String id);
	
	/**
	 * @Title: findByStuIdCard
	 * @Description: TODO(根据学生身份证查找记录)
	 * @author masn
	 * @date 2018年8月5日 上午10:02:21
	 * @return SysPreRegister  返回类型
	 * @throws
	 */
	SysPreRegister findByStuIdCard(@Param(value = "idCard") String idCard);
}