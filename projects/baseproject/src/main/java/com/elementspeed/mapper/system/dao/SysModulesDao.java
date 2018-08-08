package com.elementspeed.mapper.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.elementspeed.mapper.system.entity.SysModulesDto;

@Repository
public interface SysModulesDao {

	List<SysModulesDto> getAll();
	
}
