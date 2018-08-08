package com.elementspeed.system.mdata.service;

import java.util.List;

import com.elementspeed.framework.base.service.BaseService;
import com.elementspeed.mapper.mdata.entity.SysDomicile;

/**
 * @ClassName: SysDomicileService
 * @Description: TODO(居住地Service)
 * @author masn
 * @date 2018年8月4日 下午3:20:01
 */
public interface SysDomicileService extends BaseService {

	/**
	 * @Title: getByType
	 * @Description: TODO(根据类型获取居住地)
	 * @author masn
	 * @date 2018年8月4日 下午3:19:55
	 * @return List<SysDomicile>  返回类型
	 * @throws
	 */
	List<SysDomicile> getByType(Integer domicileType);
	
	/**
	 * @Title: findById
	 * @Description: TODO()
	 * @author masn
	 * @date 2018年8月5日 上午12:28:58
	 * @return SysDomicile  返回类型
	 * @throws
	 */
	SysDomicile findById(String id);
	
}
