package com.elementspeed.common.file.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class FileInputDto implements Serializable {
	private List<String> initialPreview;
	private List<SysFileDto> initialPreviewConfig;
	private boolean append;
	//执行结果
	private boolean success = false;
	//提示信息
	private String message = "";

	public FileInputDto() {
		super();
	}

	public FileInputDto(List<String> initialPreview, List<SysFileDto> initialPreviewConfig, boolean append) {
		super();
		this.initialPreview = initialPreview;
		this.initialPreviewConfig = initialPreviewConfig;
		this.append = append;
	}

	public List<String> getInitialPreview() {
		return initialPreview;
	}

	public void setInitialPreview(List<String> initialPreview) {
		this.initialPreview = initialPreview;
	}

	public List<SysFileDto> getInitialPreviewConfig() {
		return initialPreviewConfig;
	}

	public void setInitialPreviewConfig(List<SysFileDto> initialPreviewConfig) {
		this.initialPreviewConfig = initialPreviewConfig;
	}

	public boolean isAppend() {
		return append;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
