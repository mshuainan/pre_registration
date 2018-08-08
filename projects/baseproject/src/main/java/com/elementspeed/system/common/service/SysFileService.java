package com.elementspeed.system.common.service;

import java.util.List;

import com.elementspeed.framework.base.dao.PageResults;
import com.elementspeed.framework.base.exception.BOException;
import com.elementspeed.framework.base.service.BaseService;
import com.elementspeed.framework.common.SRMFile;
import com.elementspeed.mapper.system.entity.SysFile;

public interface SysFileService extends BaseService {

	/**
	 * 根据联合主键获取通用附件
	 * @author sai.deng on 2015年10月19日 下午6:37:02
	 * @param tableName 业务表名称
	 * @param tlId 业务表ID
	 * @param busAction 功能/动作
	 * @return
	 */
	SysFile getByUnionKey(String tableName, String tlId,  String busAction);
	
	
	/**
	 * 根据联合主键获取通用附件
	 * @param tableName 业务表名称
	 * @param tlId 业务表ID
	 * @param busAction 功能/动作
	 * @return
	 */
	List<SysFile> getListByUnionKey(SysFile sysFile);
	
	
	/**
	 * 构造通用附件PO
	 * @author sai.deng on 2015年10月20日 上午9:16:27
	 * @param srmFile 上传的附件对象
	 * @param tableName 业务表名称
	 * @param tlId 业务表ID
	 * @param busAction 功能/动作Code
	 * @return
	 * @throws Exception
	 */
	SysFile getSysFile(SRMFile srmFile, String tableName, String tlId,
			String busAction) throws Exception;
	/**
	 * 根据id获取附件信息
	 * @param id
	 * @return
	 */
	SysFile getById(String id);
	
	void delete(String id)throws BOException;
	
	void uploadSysFile(List<String> ids,String tableName,
			String tlId,String busAction);
	
	List<SysFile> getFileByTlId(String tlId);
	
	PageResults<SysFile> getFileByTlIdPage(String tlId, int pageNo, int pageSize);
	
	PageResults<SysFile> query(SysFile sysFile, int pageNo, int pageSize);
	
}
