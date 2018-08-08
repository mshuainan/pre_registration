package com.elementspeed.system.mdata.service;

import java.util.List;

import com.elementspeed.framework.base.service.BaseService;
import com.elementspeed.mapper.mdata.entity.SysDistrict;

/**
 * @ClassName: SysSchoolService
 * @Description: TODO(学校Service)
 * @author masn
 * @date 2018年8月4日 下午3:20:01
 */
public interface SysDistrictService extends BaseService {

	/**
	 * @Title: getAll
	 * @Description: TODO(获取所有学校)
	 * @author masn
	 * @date 2018年8月4日 下午3:19:55
	 * @return List<SysDistrict>  返回类型
	 * @throws
	 */
	List<SysDistrict> getAll();
	
}
