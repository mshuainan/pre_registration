package com.elementspeed.mapper.mdata.entity;

import com.elementspeed.framework.base.dao.BaseEntity;

/**
 * @ClassName: SysSchool
 * @Description: TODO(学校POJO)
 * @author masn
 * @date 2018年8月4日 下午3:00:25
 */
@SuppressWarnings("serial")
public class SysSchool extends BaseEntity {
	/** 区域ID */
	private String districtId;
	/** 学校编码 */
	private String schoolCode;
	/** 学校名称 */
	private String schoolName;
	/** 学校地址(MAX100) */
	private String schoolAddress;

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolAddress() {
		return schoolAddress;
	}

	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}

}