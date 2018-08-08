package com.elementspeed.framework.common.sn.generator.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SnGeneratorDto implements Serializable {
	/**表名*/
	private String tableName;
	/**前缀*/
	private String prefix;
	/**当前周期*/
	private String currentPeriod;
	/**最小值*/
	private Integer minSn;
	/**最大值*/
	private Integer maxSn;
	
	public SnGeneratorDto() {
		super();
	}
	
	public SnGeneratorDto(String tableName, String prefix,
			String currentPeriod, Integer maxSn) {
		super();
		this.tableName = tableName;
		this.prefix = prefix;
		this.currentPeriod = currentPeriod;
		this.maxSn = maxSn;
	}

	public SnGeneratorDto(String tableName, String prefix,
			String currentPeriod, Integer minSn, Integer maxSn) {
		super();
		this.tableName = tableName;
		this.prefix = prefix;
		this.currentPeriod = currentPeriod;
		this.minSn = minSn;
		this.maxSn = maxSn;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(String currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public Integer getMinSn() {
		return minSn;
	}

	public void setMinSn(Integer minSn) {
		this.minSn = minSn;
	}

	public Integer getMaxSn() {
		if (null == maxSn) {
			maxSn = 9999;
		}
		return maxSn;
	}

	public void setMaxSn(Integer maxSn) {
		this.maxSn = maxSn;
	}
	
}
