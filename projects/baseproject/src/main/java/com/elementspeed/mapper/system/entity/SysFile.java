package com.elementspeed.mapper.system.entity;

import java.util.Date;

import com.elementspeed.framework.base.dao.BaseEntity;

/**
 * 通用附件
 * @author sai.deng
 * @date 2015年10月13日 下午5:33:49
 */
@SuppressWarnings("serial")
public class SysFile extends BaseEntity {
	// 业务表名称
	private String tableName;
	// 业务表ID
	private String tlId;
	// 功能/动作
	private String busAction;
	// 附件名称
	private String fileName;
	// 服务器存名
	private String serverFileName;
	// 存储路径
	private String url;
	// 生效日期
	private Date beginDate;
	// 失效日期
	private Date endDate;
	// 备注
	private String remarks;
	
	//文件大小
	private int  fileSize;
	
	/** 来源位置编码，如果有文件共享时使用 */
	private String srcLocation;
	/** 来源关键字，如果有文件共享时使用 */
	private String srcKey;
	
	public SysFile(){
		
	}
	
	public SysFile(String url,String fileName,String serverFileName,int fileSize){
		this.url = url;
		this.fileName = fileName;
		this.serverFileName = serverFileName;
		this.fileSize = fileSize;
	}

	
	
	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTlId() {
		return tlId;
	}

	public void setTlId(String tlId) {
		this.tlId = tlId;
	}

	public String getBusAction() {
		return busAction;
	}

	public void setBusAction(String busAction) {
		this.busAction = busAction;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getServerFileName() {
		return serverFileName;
	}

	public void setServerFileName(String serverFileName) {
		this.serverFileName = serverFileName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSrcLocation() {
		return srcLocation;
	}

	public void setSrcLocation(String srcLocation) {
		this.srcLocation = srcLocation;
	}

	public String getSrcKey() {
		return srcKey;
	}

	public void setSrcKey(String srcKey) {
		this.srcKey = srcKey;
	}
	
	

}