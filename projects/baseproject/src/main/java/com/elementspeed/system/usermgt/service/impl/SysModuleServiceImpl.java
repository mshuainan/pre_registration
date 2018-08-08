package com.elementspeed.system.usermgt.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elementspeed.framework.base.service.BaseServiceImpl;
import com.elementspeed.mapper.system.dao.SysModulesDao;
import com.elementspeed.mapper.system.entity.SysModulesDto;
import com.elementspeed.system.usermgt.service.SysModuleService;

@Service
public class SysModuleServiceImpl extends BaseServiceImpl implements SysModuleService{
	@Resource
	private SysModulesDao sysModuleDao;
	
	/**
	 * 获取当前登录用户的菜单
	 * @return
	 */
	@Override
	public List<SysModulesDto> getAll() {
		List<SysModulesDto> modules = sysModuleDao.getAll();
		return modules;
	}
	
}
