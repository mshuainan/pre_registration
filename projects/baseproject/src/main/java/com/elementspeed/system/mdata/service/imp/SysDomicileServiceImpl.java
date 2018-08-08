package com.elementspeed.system.mdata.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elementspeed.framework.base.service.BaseServiceImpl;
import com.elementspeed.mapper.mdata.dao.SysDomicileDao;
import com.elementspeed.mapper.mdata.entity.SysDomicile;
import com.elementspeed.system.mdata.service.SysDomicileService;

@Service
public class SysDomicileServiceImpl extends BaseServiceImpl implements SysDomicileService{
	
	@Resource
	private SysDomicileDao sysDomicileDao;

	@Override
	public List<SysDomicile> getByType(Integer domicileType) {
		List<SysDomicile> sysDomiciles = sysDomicileDao.getByType(domicileType);
		return sysDomiciles;
	}

	@Override
	public SysDomicile findById(String id) {
		return sysDomicileDao.findById(id);
	}
	
}