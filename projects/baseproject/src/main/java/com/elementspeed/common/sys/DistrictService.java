package com.elementspeed.common.sys;

import java.util.ArrayList;
import java.util.List;

import com.elementspeed.framework.common.util.ContainerUtil;
import com.elementspeed.framework.common.util.SpringContextUtil;
import com.elementspeed.mapper.mdata.entity.SysDistrict;
import com.elementspeed.system.mdata.service.SysDistrictService;

/**
 * @ClassName: DistrictService
 * @Description: TODO(区域信息Service)
 * @author masn
 * @date 2018年8月4日 下午3:38:24
 */
public class DistrictService {

	private static SysDistrictService sysDistrictService;
	
	public static SysDistrictService getDistrictService() {
		if(sysDistrictService == null){
			sysDistrictService = SpringContextUtil.getBean("sysDistrictService", SysDistrictService.class);
		}
		return sysDistrictService;
	}

	private static List<SysDistrict> districtList = new ArrayList<SysDistrict>();
	
	/**
	 * @Title: getAllList
	 * @Description: TODO(获取所有学校信息)
	 * @author masn
	 * @date 2018年8月4日 下午3:37:48
	 * @return List<SysSchool>  返回类型
	 * @throws
	 */
	public static List<SysDistrict> getAllList() {
		if (ContainerUtil.isEmpty(districtList)) {
			districtList = getDistrictService().getAll();
		}
		return districtList;
	}
	
}