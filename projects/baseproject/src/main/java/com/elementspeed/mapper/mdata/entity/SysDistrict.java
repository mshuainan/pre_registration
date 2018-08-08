package com.elementspeed.mapper.mdata.entity;

import com.elementspeed.framework.base.dao.BaseEntity;

/**
 * @ClassName: SysDistrict
 * @Description: TODO(区域表POJO)
 * @author masn
 * @date 2018年8月4日 下午2:56:03
 */
@SuppressWarnings("serial")
public class SysDistrict extends BaseEntity {
	/** 区域编码 */
	private String districtCode;
	/** 区域名称 */
	private String districtName;
	/** 邮编 */
	private String postCode;

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

}