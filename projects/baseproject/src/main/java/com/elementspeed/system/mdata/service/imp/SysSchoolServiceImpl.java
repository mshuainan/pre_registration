package com.elementspeed.system.mdata.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elementspeed.framework.base.service.BaseServiceImpl;
import com.elementspeed.mapper.mdata.dao.SysSchoolDao;
import com.elementspeed.mapper.mdata.entity.SysSchool;
import com.elementspeed.system.mdata.service.SysSchoolService;

@Service
public class SysSchoolServiceImpl extends BaseServiceImpl implements SysSchoolService{
	
	@Resource
	private SysSchoolDao sysSchoolDao;

	@Override
	public List<SysSchool> getAll() {
		List<SysSchool> sysSchools = sysSchoolDao.getAll();
		return sysSchools;
	}
}
