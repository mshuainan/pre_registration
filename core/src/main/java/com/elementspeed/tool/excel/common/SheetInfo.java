package com.elementspeed.tool.excel.common;

import java.io.Serializable;
import java.util.List;

/**
 * Excel工作表信息,
 * 
 * @author sai.deng
 * 
 */
public class SheetInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5898416492573374687L;
	private int sheetIndex;
	private int startRow;
	private int endRow;
	private String clazz;
	private int startCol;
	private List<SheetColumnTemplate> columnTemplates;
	private List<SheetHeadTemplate> headTemplates;

	public int getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public int getStartCol() {
		return startCol;
	}

	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}

	public List<SheetColumnTemplate> getColumnTemplates() {
		return columnTemplates;
	}

	public void setColumnTemplates(List<SheetColumnTemplate> columnTemplates) {
		this.columnTemplates = columnTemplates;
	}

	public List<SheetHeadTemplate> getHeadTemplates() {
		return headTemplates;
	}

	public void setHeadTemplates(List<SheetHeadTemplate> headTemplates) {
		this.headTemplates = headTemplates;
	}

}
