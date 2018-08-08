package com.elementspeed.common.file.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SysFileDto implements Serializable {

	private String caption;
	private int size;
	private String width;
	private String key;
	private Extra extra;

	public SysFileDto() {
		super();
	}

	public SysFileDto(String caption, int size, String width, String key) {
		super();
		this.caption = caption;
		this.size = size;
		this.width = width;
		this.key = key;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Extra getExtra() {
		return extra;
	}

	public void setExtra(Extra extra) {
		this.extra = extra;
	}

}
