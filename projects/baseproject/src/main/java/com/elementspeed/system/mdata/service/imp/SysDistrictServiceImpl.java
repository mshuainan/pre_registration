package com.elementspeed.system.mdata.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elementspeed.framework.base.service.BaseServiceImpl;
import com.elementspeed.mapper.mdata.dao.SysDistrictDao;
import com.elementspeed.mapper.mdata.entity.SysDistrict;
import com.elementspeed.system.mdata.service.SysDistrictService;

@Service
public class SysDistrictServiceImpl extends BaseServiceImpl implements SysDistrictService{
	
	@Resource
	private SysDistrictDao sysDistrictDao;

	@Override
	public List<SysDistrict> getAll() {
		List<SysDistrict> sysDistricts = sysDistrictDao.getAll();
		return sysDistricts;
	}
}
