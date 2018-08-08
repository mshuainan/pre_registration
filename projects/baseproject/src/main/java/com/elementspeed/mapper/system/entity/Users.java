package com.elementspeed.mapper.system.entity;
import com.elementspeed.framework.base.dao.BaseEntity;
import java.util.Date;

public class Users extends BaseEntity {
	private static final long serialVersionUID = -5176970113927511986L;
	private Long uid;
	private Date createdDate;
	private Date lastModifiedDate;
	private Double version;
	private Integer isEnabled;
	private Integer isLocked;
	private Date lastLoginDate;
	private String lastLoginIp;
	private Date lockDate;
	private String srmUserPid; //SRM_USERPID  新增字段
	private Integer mallAccountFlag; //启用商城用户标识
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date date) {
		this.createdDate = date;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Double getVersion() {
		return version;
	}
	public void setVersion(Double version) {
		this.version = version;
	}
	public Integer getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Integer isEnabled) {
		this.isEnabled = isEnabled;
	}
	public Integer getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(Integer isLocked) {
		this.isLocked = isLocked;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public Date getLockDate() {
		return lockDate;
	}
	public void setLockDate(Date lockDate) {
		this.lockDate = lockDate;
	}
	public String getSrmUserPid() {
		return srmUserPid;
	}
	public void setSrmUserPid(String srmUserPid) {
		this.srmUserPid = srmUserPid;
	}
	public Integer getMallAccountFlag() {
		return mallAccountFlag;
	}
	public void setMallAccountFlag(Integer mallAccountFlag) {
		this.mallAccountFlag = mallAccountFlag;
	}
}