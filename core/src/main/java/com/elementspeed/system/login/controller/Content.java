package com.elementspeed.system.login.controller;

import java.util.Date;

import com.elementspeed.framework.common.util.DateUtil;

/**
 * 首页内容 
 *
 */
public class Content {
	Date time;
	String text;
	String value;
	String id;
	String timeStr;
	boolean highLight;	//是否高亮显示
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isHighLight() {
		return highLight;
	}
	public void setHighLight(boolean highLight) {
		this.highLight = highLight;
	}
	public String getTimeStr() {
		timeStr = DateUtil.convertDateToString(getTime());
		return timeStr;
	}
	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
	
}
