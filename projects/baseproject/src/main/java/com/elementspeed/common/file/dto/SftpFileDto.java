package com.elementspeed.common.file.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("serial")
public class SftpFileDto implements Serializable {
	private String packageName;
	private MultipartFile multFile; // 上传的附件

	private String tableName;
	private String busAction;
	private String tlId; // 通用附件表使用
	
	private boolean httpFlag;
	
	private boolean delOldFileFlag = true; //是否删除之前上传的附件，默认要删除
	
	public SftpFileDto() {
		super();
	}

	public SftpFileDto(String packageName, MultipartFile multFile, String tableName, String busAction, String tlId,
			boolean httpFlag) {
		super();
		this.packageName = packageName;
		this.multFile = multFile;
		this.tableName = tableName;
		this.busAction = busAction;
		this.tlId = tlId;
		this.httpFlag = httpFlag;
	}
	
	public SftpFileDto(String packageName, MultipartFile multFile, String tableName, String busAction, String tlId,
			boolean httpFlag, boolean delOldFileFlag) {
		super();
		this.packageName = packageName;
		this.multFile = multFile;
		this.tableName = tableName;
		this.busAction = busAction;
		this.tlId = tlId;
		this.httpFlag = httpFlag;
		this.delOldFileFlag = delOldFileFlag;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public MultipartFile getMultFile() {
		return multFile;
	}

	public void setMultFile(MultipartFile multFile) {
		this.multFile = multFile;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getBusAction() {
		return busAction;
	}

	public void setBusAction(String busAction) {
		this.busAction = busAction;
	}

	public String getTlId() {
		return tlId;
	}

	public void setTlId(String tlId) {
		this.tlId = tlId;
	}

	public boolean isHttpFlag() {
		return httpFlag;
	}

	public void setHttpFlag(boolean httpFlag) {
		this.httpFlag = httpFlag;
	}

	public boolean isDelOldFileFlag() {
		return delOldFileFlag;
	}

	public void setDelOldFileFlag(boolean delOldFileFlag) {
		this.delOldFileFlag = delOldFileFlag;
	}

}
