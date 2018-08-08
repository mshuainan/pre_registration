package com.elementspeed.mapper.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.elementspeed.mapper.system.entity.SysFile;

/**
 * 通用附件Dao
 * @author sai.deng
 * @date 2015年10月19日 下午6:30:18
 */
@Repository
public interface SysFileDao {

	/**
	 * 根据ID获取通用附件
	 * @author sai.deng on 2015年10月19日 下午6:38:32
	 * @param id
	 * @return
	 */
	SysFile getById(@Param(value = "id") String id);
	
	/**
	 * 根据联合主键获取通用附件
	 * @author sai.deng on 2015年10月19日 下午6:37:02
	 * @param tableName 业务表名称
	 * @param tlId 业务表ID
	 * @param busAction 功能/动作
	 * @return
	 */
	SysFile getByUnionKey(@Param(value = "tableName") String tableName,
			@Param(value = "tlId") String tlId,
			@Param(value = "busAction") String busAction);
	
	/**
	 * 根据联合主键获取通用附件
	 * @param tableName 业务表名称
	 * @param tlId 业务表ID
	 * @param busAction 功能/动作
	 * @return
	 */
	List<SysFile> getListByUnionKey(SysFile sysFile);
	
	/**
	 * 新增通用附件
	 * @author sai.deng on 2015年10月19日 下午6:38:49
	 * @param sysFile
	 */
	void insert(SysFile sysFile);

	/**
	 * 根据ID删除通用附件 物理删除
	 * @author sai.deng on 2015年10月19日 下午6:38:58
	 * @param id
	 */
	void physicalDelete(@Param(value = "id") String id);
	
	/**
	 * 根据ID删除通用附件 DELETE_FLAG设为1
	 * @param id
	 */
	void delete(@Param(value = "id") String id);
	
	/**
	 * 根据联合主键删除通用附件 物理删除
	 * @author sai.deng on 2015年10月19日 下午6:37:46
	 * @param tableName 业务表名称
	 * @param tlId 业务表ID
	 * @param busAction 功能/动作
	 */
	void physicalDeleteByUnionKey(@Param(value = "tableName") String tableName,
			@Param(value = "tlId") String tlId,
			@Param(value = "busAction") String busAction);
	
	/**
	 * 根据联合主键删除通用附件 DELETE_FLAG设为1
	 * @author sai.deng on 2015年10月19日 下午6:37:46
	 * @param tableName 业务表名称
	 * @param tlId 业务表ID
	 * @param busAction 功能/动作
	 */
	void deleteByUnionKey(@Param(value = "tableName") String tableName,
			@Param(value = "tlId") String tlId,
			@Param(value = "busAction") String busAction);
	/**
	 * 更新附件对应的具体tlid
	 * @param tableName
	 * @param tlId
	 * @param busAction
	 */
	void uploadSysFile(	@Param(value = "list") List<String> tlIds,
			@Param(value = "tableName") String tableName,
			@Param(value = "tlId") String tlId,
			@Param(value = "busAction") String busAction);
	
	List<SysFile> getFileByTlId(String tlId);

	/**
	 * 根据联合主键查询主键
	 * @param tableName 业务表名称
	 * @param tlId 业务表ID
	 * @param busAction 功能/动作
	 * @return
	 */
	List<String> getIdListByUnionKey(@Param(value = "tableName") String tableName,
			@Param(value = "tlId") String tlId,
			@Param(value = "busAction") String busAction);
	
	
	/**
	 * 查询为未删除的附件
	 * @param tableName
	 * @param tlId
	 * @param busAction
	 * @return
	 */
	List<String> getIdListByUnionKeyNotDelete(@Param(value = "tableName") String tableName,
			@Param(value = "tlId") String tlId,
			@Param(value = "busAction") String busAction);
	
	
	
	/**
	 * 获取分页数据
	 */
	List<SysFile> query(SysFile sysFile);
	
	/**
	 * 根据业务名称，业务id,来源位置找修改
	 * @param tableName 业务表名称
	 * @param tlId 业务表Id
	 * @param srcLocation 来源位置编码
	 * @return
	 */
	void updateFileDeleteflag(@Param(value="tableName")String tableName ,@Param(value="tlId")String tlId,
			@Param(value="srcLocation")String srcLocation,@Param(value="srcKey")String srcKey);
	
	/**
	 * 查找附件
	 * @param tableName
	 * @param tlId
	 * @return
	 */
	List<SysFile> findByfile(@Param(value="tableName")String tableName ,@Param(value="tlId")String tlId);

}
