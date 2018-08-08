package com.elementspeed.mapper.system.entity;

import java.util.Date;

import com.elementspeed.framework.base.dao.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

public class SysUserLog extends BaseEntity {

	private static final long serialVersionUID = -1445044357278457078L;

	private String userId;
	private String userName;
	private String userEmail;
	private Date firstTime;
	private Date endTime;
	private Integer count;
	private Integer userType;
	private Date logDate;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	@JsonFormat(pattern="yyyy-MM-dd kk:mm:ss",timezone = "GMT+8") 
	public Date getFirstTime() {
		return firstTime;
	}
	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd kk:mm:ss",timezone = "GMT+8") 
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8") 
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date date) {
		this.logDate = date;
	}
	
}
