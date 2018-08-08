package com.elementspeed.tool.excel.common;

import java.util.List;

public class SheetHeadTemplate {

	private String columnCaption;     	//列头名称
	private String columnWidth;			//列宽
	private boolean isMerge;			//是否合并单元格
	private int columnIndex;			//单元格列索引
	private int rowIndex;				//单元格行索引
	private int coslpan;				//跨几列
	private int rowspan;				//跨几行
	private int totalRows;				//总共占几行
	private boolean isValid; //是否校验
	private List<String> validList;//校验数据
	
	public SheetHeadTemplate(String columnCaption, String columnWidth, boolean isMerge, int columnIndex, int coslpan, int rowIndex, 
			int rowspan) {
		super();
		this.columnCaption = columnCaption;
		this.columnWidth = columnWidth;
		this.isMerge = isMerge;
		this.columnIndex = columnIndex;
		this.rowIndex = rowIndex;
		this.coslpan = coslpan;
		this.rowspan = rowspan;
	}
	
	
	
	public SheetHeadTemplate(String columnCaption, String columnWidth, boolean isMerge, int columnIndex, int rowIndex,
			int coslpan, int rowspan, int totalRows, boolean isValid, List<String> validList) {
		super();
		this.columnCaption = columnCaption;
		this.columnWidth = columnWidth;
		this.isMerge = isMerge;
		this.columnIndex = columnIndex;
		this.rowIndex = rowIndex;
		this.coslpan = coslpan;
		this.rowspan = rowspan;
		this.totalRows = totalRows;
		this.isValid = isValid;
		this.validList = validList;
	}



	public SheetHeadTemplate(String columnCaption, String columnWidth, int columnIndex, int rowIndex) {
		super();
		this.columnCaption = columnCaption;
		this.columnWidth = columnWidth;
		this.columnIndex = columnIndex;
		this.rowIndex = rowIndex;
	}
	
	public String getColumnCaption() {
		return columnCaption;
	}

	public void setColumnCaption(String columnCaption) {
		this.columnCaption = columnCaption;
	}

	public String getColumnWidth() {
		return columnWidth;
	}

	public void setColumnWidth(String columnWidth) {
		this.columnWidth = columnWidth;
	}
	
	public boolean isMerge() {
		return isMerge;
	}

	public void setMerge(boolean isMerge) {
		this.isMerge = isMerge;
	}

	public int getCoslpan() {
		return coslpan;
	}

	public void setCoslpan(int coslpan) {
		this.coslpan = coslpan;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public List<String> getValidList() {
		return validList;
	}

	public void setValidList(List<String> validList) {
		this.validList = validList;
	}
	
	

}
