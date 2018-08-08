package com.elementspeed.system.newregister.service;

import java.io.OutputStream;
import java.util.List;

import com.elementspeed.framework.base.controller.ExecuteResult;
import com.elementspeed.framework.base.dao.PageResults;
import com.elementspeed.framework.base.service.BaseService;
import com.elementspeed.mapper.system.entity.SysPreRegister;

/**
 * @ClassName: RegistrationService
 * @Description: TODO(报名注册Service)
 * @author masn
 * @date 2018年8月3日 下午10:07:03
 */
public interface RegistrationService extends BaseService{

	/**
	 * @Title: register
	 * @Description: TODO(注册新增)
	 * @author masn
	 * @date 2018年8月3日 下午10:07:26
	 * @return ExecuteResult  返回类型
	 * @throws
	 */
	public ExecuteResult register(SysPreRegister sysPreRegister);
	
	/**
	 * @Title: query
	 * @Description: TODO(查询结果)
	 * @author masn
	 * @date 2018年8月3日 下午11:30:44
	 * @return PageResults<SysPreRegister>  返回类型
	 * @throws
	 */
	PageResults<SysPreRegister> query(SysPreRegister sysPreRegister, int pageNo, int pageSize);
	
	/**
	 * @Title: findById
	 * @Description: TODO()
	 * @author masn
	 * @date 2018年8月6日 下午11:34:26
	 * @return SysPreRegister  返回类型
	 * @throws
	 */
	SysPreRegister findById(String id);
	
	/**
	 * @Title: findByStuIdCard
	 * @Description: TODO(通过身份证获取)
	 * @author masn
	 * @date 2018年8月7日 上午12:02:10
	 * @return SysPreRegister  返回类型
	 * @throws
	 */
	SysPreRegister findByStuIdCard(String idCard);
	
	/**
	 * @Title: exportRegisterInfo
	 * @Description: TODO(勾选导出)
	 * @author masn
	 * @date 2018年8月4日 上午10:34:14
	 * @return List<SysPreRegister>  返回类型
	 * @throws
	 */
	List<SysPreRegister> exportRegisterInfo(List<String> registerIds);	
	
	/**
	 * @Title: exportByQuery
	 * @Description: TODO(导出通过查询条件)
	 * @author masn
	 * @date 2018年8月4日 上午10:51:43
	 * @return List<SysPreRegister>  返回类型
	 * @throws
	 */
	List<SysPreRegister> exportByQuery(SysPreRegister sysPreRegister);	
	
	/**
	 * @Title: expRegPdf
	 * @Description: TODO(导出报名表)
	 * @author masn
	 * @date 2018年8月6日 下午11:32:00
	 * @return void  返回类型
	 * @throws
	 */
	public void expRegPdf(SysPreRegister sysPreRegister, OutputStream os) throws Exception;

}