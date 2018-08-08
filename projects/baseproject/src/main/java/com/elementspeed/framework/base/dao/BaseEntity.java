package com.elementspeed.framework.base.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.elementspeed.framework.common.util.ContainerUtil;
import com.elementspeed.framework.common.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 804135727939015248L;
	
	protected String id;
	protected String createUserId;
	protected Date createTime;
	protected String lastModifyUserId;
	protected Date lastModifyTime;
	protected Integer deleteFlag;
	
	protected String sort;					//排序字段对应的bean属性名，  多个字段","隔开 如  name, email
	protected String order;                 //排序类型 和sort属性一一对应 值为 asc desc 如 asc, desc  则按name升序email降序排列 
	protected String orderSql;				//生成排序sql
	
	public static final int DELETE_FLAG_TRUE = 1;
	public static final int DELETE_FLAG_FALSE = 0;

	public static final String DELETE_FLAG = "deleteFlag";		//删除标记字段
	
	public void setCreateInfo() {
		setId(UUID.randomUUID().toString());
		setCreateTime(new Date());
		setLastModifyTime(new Date());
		setCreateUserId("-1");
		setLastModifyUserId("-1");
		setDeleteFlag(DELETE_FLAG_FALSE);
	}
	
	public void setLastModifyInfo() {
		setLastModifyTime(new Date());
		setLastModifyUserId("-1");
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLastModifyUserId() {
		return lastModifyUserId;
	}

	public void setLastModifyUserId(String lastModifyUserId) {
		this.lastModifyUserId = lastModifyUserId;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	public String getOrderSql() {
		String orderSql = "";
		
		List<String> sortList = ContainerUtil.string2List(StringUtil.field2Column(sort));
		List<String> orderList = ContainerUtil.string2List(order);
		
		if(ContainerUtil.isEmpty(orderList) || ContainerUtil.isEmpty(sortList)){
			return orderSql;
		}
		int sortListSize = sortList.size();
		for(int i = 0; i < sortListSize; i++) {
			String column = sortList.get(i);
			//过滤前台形如 basein.PID的字段 避免数据库报无效字符
			if(sortList.get(i).contains(".")) {
				column = sortList.get(i).substring(sortList.get(i).lastIndexOf('.') + 1);
			}
			orderSql += column  + " " + orderList.get(i) + "," ;
		}
		return  StringUtil.removeLast(orderSql);
	}
}