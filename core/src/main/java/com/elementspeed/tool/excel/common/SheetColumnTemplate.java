package com.elementspeed.tool.excel.common;

import java.io.Serializable;

/**
 * XML配置文件对应的Excel中的列模板定义(导入导出使用)
 * 
 * @author sai.deng
 * 
 */
public class SheetColumnTemplate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8293947995097656966L;
	private String outputColumn; // 对应PO的属性
	private String valueColumn; // 对应Excel的哪一列
	private String columnCaption;
	private String columnWidth;
	private String type;
	private String format;
	private Boolean formula;

	public String getOutputColumn() {
		return outputColumn;
	}

	public void setOutputColumn(String outputColumn) {
		this.outputColumn = outputColumn;
	}

	public String getValueColumn() {
		return valueColumn;
	}

	public void setValueColumn(String valueColumn) {
		this.valueColumn = valueColumn;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Boolean getFormula() {
		return formula;
	}

	public void setFormula(Boolean formula) {
		this.formula = formula;
	}

}
