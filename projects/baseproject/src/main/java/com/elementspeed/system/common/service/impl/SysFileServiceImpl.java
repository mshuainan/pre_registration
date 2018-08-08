package com.elementspeed.system.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elementspeed.framework.base.dao.PageResults;
import com.elementspeed.framework.base.service.BaseServiceImpl;
import com.elementspeed.framework.common.SRMFile;
import com.elementspeed.mapper.system.dao.SysFileDao;
import com.elementspeed.mapper.system.entity.SysFile;
import com.elementspeed.system.common.service.SysFileService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 通用附件Service实现
 * @author sai.deng
 * @date 2015年10月21日 上午10:50:45
 */
@Service
public class SysFileServiceImpl extends BaseServiceImpl implements SysFileService {

	@Resource
	private SysFileDao sysFileDao;
	
	/**
	 * 根据联合主键获取通用附件
	 * @author sai.deng on 2015年10月19日 下午6:37:02
	 * @param tableName 业务表名称
	 * @param tlId 业务表ID
	 * @param busAction 功能/动作
	 * @return
	 */
	public SysFile getByUnionKey(String tableName, String tlId,  String busAction) {
		return sysFileDao.getByUnionKey(tableName, tlId, busAction);
	}
	
	
	public List<SysFile> getListByUnionKey(SysFile sysFile){
		return sysFileDao.getListByUnionKey(sysFile);
	}
	
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
	public SysFile getSysFile(SRMFile srmFile, String tableName, String tlId,
			String busAction) throws Exception {
		SysFile sysFile = new SysFile();
		sysFile.setCreateInfo();
		sysFile.setTableName(tableName); // 业务表名称
		sysFile.setTlId(tlId); // 业务表ID
		sysFile.setBusAction(busAction); // 功能/动作
		sysFile.setFileName(srmFile.getOriginalFilename()); // 附件名称
		sysFile.setServerFileName(srmFile.getServerFileName()); // 服务器存名
		sysFile.setUrl(srmFile.getPath()); // 路径
		return sysFile;
	}

	/**
	 * 根据id获取附件信息
	 */
	@Override
	public SysFile getById(String id) {
		
		return sysFileDao.getById(id);
	}
	
	@Override
	public void delete(String id) {
		
		 sysFileDao.delete(id);
	}


	@Override
	public void uploadSysFile( List<String> ids,String tableName,String tlId, String busAction) {
		
		sysFileDao.uploadSysFile(ids, tableName, tlId, busAction);
	}


	@Override
	public List<SysFile> getFileByTlId(String tlId) {
		return sysFileDao.getFileByTlId(tlId);
	}


	@Override
	public PageResults<SysFile> getFileByTlIdPage(String tlId, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageInfo<SysFile> pageInfo = new PageInfo<SysFile>(
				sysFileDao.getFileByTlId(tlId));
		return convertPageResult(pageInfo, SysFile.class);
	}


	@Override
	public PageResults<SysFile> query(SysFile sysFile, int pageNo,
			int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageInfo<SysFile> pageInfo = new PageInfo<SysFile>(
				sysFileDao.query(sysFile));
		return convertPageResult(pageInfo, SysFile.class);
	}
}
