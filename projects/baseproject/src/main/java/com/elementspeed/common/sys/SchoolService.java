package com.elementspeed.common.sys;

import java.util.ArrayList;
import java.util.List;

import com.elementspeed.framework.common.util.ContainerUtil;
import com.elementspeed.framework.common.util.SpringContextUtil;
import com.elementspeed.mapper.mdata.entity.SysSchool;
import com.elementspeed.system.mdata.service.SysSchoolService;

/**
 * @ClassName: SchoolService
 * @Description: TODO(学校信息Service)
 * @author masn
 * @date 2018年8月4日 下午3:38:24
 */
public class SchoolService {

	private static SysSchoolService sysSchoolService;
	
	public static SysSchoolService getSchoolService() {
		if(sysSchoolService == null){
			sysSchoolService = SpringContextUtil.getBean("sysSchoolService", SysSchoolService.class);
		}
		return sysSchoolService;
	}

	private static List<SysSchool> schoolList = new ArrayList<SysSchool>();
	
	/**
	 * @Title: getAllList
	 * @Description: TODO(获取所有学校信息)
	 * @author masn
	 * @date 2018年8月4日 下午3:37:48
	 * @return List<SysSchool>  返回类型
	 * @throws
	 */
	public static List<SysSchool> getAllList() {
		if (ContainerUtil.isEmpty(schoolList)) {
			schoolList = getSchoolService().getAll();
		}
		return schoolList;
	}
	
}