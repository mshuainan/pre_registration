package com.elementspeed.mapper.system.entity;

import com.elementspeed.framework.base.dao.BaseEntity;

/**
 * @ClassName: SysSeriaNo
 * @Description: TODO(流水号生成器)
 * @author masn
 * @date 2018年8月10日 下午12:26:07
 */
public class SysSeriaNo extends BaseEntity {

	private static final long serialVersionUID = -2147158178257648671L;

	private String tableName; // 表名
	private String beginDate; // 日期
	private Integer serialNo; // 流水号

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
}