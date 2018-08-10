package com.elementspeed.mapper.system.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.elementspeed.mapper.system.entity.SysSeriaNo;

/**
 * 流水号生成器  
 *
 */
@Repository
public interface SysSeriaNoDao {
	
	/**
	 * 获取流水号
	 * @param tableName	表名
	 * @return
	 */
	SysSeriaNo getSn(@Param("tableName") String tableName);
	
	/**
	 * 流水号自增
	 * @param id
	 */
	void increment(@Param("id") String id);
	
	/**
	 * 新增业务序列 
	 * @param seriaNo
	 */
	void create(SysSeriaNo seriaNo);
	
	/**
	 * 
	 * @param seriaNo
	 */
	void update(SysSeriaNo seriaNo);
}
