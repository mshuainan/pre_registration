package com.elementspeed.framework.base.dao;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageInfo;

/**
 * 分页封装类 用于做分页查询的基础类，封装了一些分页的相关属性
 */
public class PageResults<T> {

	// 当前页号
	private int page;

	// 每页显示数
	private int pageSize;

	// 总页数
	private int pageCount;

	// 总条数
	private long total;

	// 数据记录
	private List<T> rows = new ArrayList<T>();

	/**
	 * @param rows
	 * @param pageNo   当前页号
	 * @param pageSize 每页显示记录数
	 */
	public PageResults(PageInfo<T> pageInfo) {
		this.page = pageInfo.getPageNum();
		this.pageSize = pageInfo.getPageSize();
		this.pageCount = pageInfo.getPages();
		this.rows = pageInfo.getList();
		this.total = pageInfo.getTotal();
	}

	public PageResults() {
		
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
