package com.elementspeed.mapper.mdata.entity;

import com.elementspeed.framework.base.dao.BaseEntity;

/**
 * @ClassName: SysDomicile
 * @Description: TODO(居住地POJO)
 * @author masn
 * @date 2018年8月4日 下午3:00:03
 */
@SuppressWarnings("serial")
public class SysDomicile extends BaseEntity {
	/** 学校ID */
	private String schoolId;
	/** 区域ID */
	private String districtId;
	/** 居住地编码 */
	private String domicileCode;
	/** 居住地名称 */
	private String domicileName;
	/** 居住地详细地址 */
	private String domicileAddress;
	/** 1:户口本地段,2:购置商品房 */
	private Integer domicileType;

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getDomicileCode() {
		return domicileCode;
	}

	public void setDomicileCode(String domicileCode) {
		this.domicileCode = domicileCode;
	}

	public String getDomicileName() {
		return domicileName;
	}

	public void setDomicileName(String domicileName) {
		this.domicileName = domicileName;
	}

	public String getDomicileAddress() {
		return domicileAddress;
	}

	public void setDomicileAddress(String domicileAddress) {
		this.domicileAddress = domicileAddress;
	}

	public Integer getDomicileType() {
		return domicileType;
	}

	public void setDomicileType(Integer domicileType) {
		this.domicileType = domicileType;
	}

}