package com.elementspeed.common.sys;

import java.util.ArrayList;
import java.util.List;

import com.elementspeed.framework.common.util.SpringContextUtil;
import com.elementspeed.mapper.mdata.entity.SysDomicile;
import com.elementspeed.system.mdata.service.SysDomicileService;

/**
 * @ClassName: DomicileService
 * @Description: TODO(居住地Service)
 * @author masn
 * @date 2018年8月4日 下午3:38:24
 */
public class DomicileService {

	private static SysDomicileService sysDomicileService;
	
	public static SysDomicileService getDomicileService() {
		if(sysDomicileService == null){
			sysDomicileService = SpringContextUtil.getBean("sysDomicileService", SysDomicileService.class);
		}
		return sysDomicileService;
	}

	private static List<SysDomicile> sysDomiciles = new ArrayList<SysDomicile>();
	
	/**
	 * @Title: getAllList
	 * @Description: TODO(根据类型获取居住地)
	 * @author masn
	 * @date 2018年8月4日 下午3:37:48
	 * @return List<SysSchool>  返回类型
	 * @throws
	 */
	public static List<SysDomicile> getByType(Integer domicileType) {
		sysDomiciles = getDomicileService().getByType(domicileType);
		return sysDomiciles;
	}
	
}