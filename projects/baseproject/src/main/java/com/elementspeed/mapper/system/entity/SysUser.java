package com.elementspeed.mapper.system.entity;

import java.util.Date;

import com.elementspeed.framework.base.dao.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

public class SysUser extends BaseEntity {

	private static final long serialVersionUID = 8945339634134977148L;

	private String account; // 编码
	private String name; // 姓名
	private String password; // 密码
	private String email; // 邮箱
	private String mobilePhone; // 联系电话
	private String vendorId; // 所属组织
	private String companyId; // 所属公司
	private String deptId; // 部门ID
	private String deptName; // 部门名称
	private Integer status; // 用户状态(0:禁用;1:启用;)
	private Integer registerTerm; // 是否已勾选注册协议
	private Integer cpFlag; // 是否集采权限
	private Integer errorCount; // 密码错误次数
	private String empNo; // 工号
	private Integer pwdModifyFlag; // 初始密码修改标识
	
	private String directLeaderId;//直属领导
	private String directLeaderName;//直属领导名称
	private Integer simulateLoginFlag; //SIMULATE_LOGIN_FLAG
	
	private Integer mallAccountFlag; //商城开通标识
	private Integer mallOpenFlag;    //非数据库字段，条件查询时使用
	//---微信相关字段 ---2017-10-23 LZL ---//
	private String openId; 					//微信OPEN ID
	private Integer wechatFlag;             //微信开通标识
	private String wechatVcode;             //微信校验码
	private Date vcodeEndDate;              //微信校验码有效期
	
	//登录锁定
	private Integer lockFlag;  			// 用户锁定标识
	private String lockReason;			// 锁定原因
	private Date lockTime;				//锁定时间
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Integer getRegisterTerm() {
		return registerTerm;
	}

	public void setRegisterTerm(Integer registerTerm) {
		this.registerTerm = registerTerm;
	}

	public Integer getCpFlag() {
		return cpFlag;
	}

	public void setCpFlag(Integer cpFlag) {
		this.cpFlag = cpFlag;
	}

	public Integer getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Integer getPwdModifyFlag() {
		return pwdModifyFlag;
	}

	public void setPwdModifyFlag(Integer pwdModifyFlag) {
		this.pwdModifyFlag = pwdModifyFlag;
	}
	

	public String getDirectLeaderId() {
		return directLeaderId;
	}

	public void setDirectLeaderId(String directLeaderId) {
		this.directLeaderId = directLeaderId;
	}

	public String getDirectLeaderName() {
		return directLeaderName;
	}

	public void setDirectLeaderName(String directLeaderName) {
		this.directLeaderName = directLeaderName;
	}

	public Integer getSimulateLoginFlag() {
		return simulateLoginFlag;
	}

	public void setSimulateLoginFlag(Integer simulateLoginFlag) {
		this.simulateLoginFlag = simulateLoginFlag;
	}

	@Override
	public String toString() {
		return "SysUser [account=" + account + ", name=" + name + ", password="
				+ password + ", email=" + email + ", mobilePhone="
				+ mobilePhone + ", vendorId=" + vendorId + ", companyId="
				+ companyId + ", deptId=" + deptId + ", deptName=" + deptName
				+ ", status=" + status + ", registerTerm=" + registerTerm
				+ ", cpFlag=" + cpFlag + ", errorCount=" + errorCount
				+ ", pwdModifyFlag=" + pwdModifyFlag + "]";
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getWechatFlag() {
		return wechatFlag;
	}

	public void setWechatFlag(Integer wechatFlag) {
		this.wechatFlag = wechatFlag;
	}

	public String getWechatVcode() {
		return wechatVcode;
	}

	public void setWechatVcode(String wechatVcode) {
		this.wechatVcode = wechatVcode;
	}

	public Date getVcodeEndDate() {
		return vcodeEndDate;
	}

	public void setVcodeEndDate(Date vcodeEndDate) {
		this.vcodeEndDate = vcodeEndDate;
	}

	public Integer getMallAccountFlag() {
		return mallAccountFlag;
	}
	public void setMallAccountFlag(Integer mallAccountFlag) {
		this.mallAccountFlag = mallAccountFlag;
	}

	public Integer getMallOpenFlag() {
		return mallOpenFlag;
	}

	public void setMallOpenFlag(Integer mallOpenFlag) {
		this.mallOpenFlag = mallOpenFlag;
	}

	public Integer getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(Integer lockFlag) {
		this.lockFlag = lockFlag;
	}

	public String getLockReason() {
		return lockReason;
	}

	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}
}
