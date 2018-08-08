package com.elementspeed.tool.excel.writer;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * 导出Excel的参数
 * @author sai.deng
 *
 */
@SuppressWarnings("serial")
public class ExcelParam implements Serializable {
	/* export.xml所在的目录路径 */
	private String packageName;
	/* export.xml中所配置的taskId */
	private String taskId;
	/* 导出excel的文件名 */
	private String fileName;
	/* 导出excel的工作表的名称 */
	private String sheetName;
	/* 导出的标题 */
	private String title;
	/* 导出的数据清单 */
	private List<?> dataRows;
	/* 导出excel的表头开始行 */
	private Integer tableHeadStartRow;
	/* 导出时以流的形式直接输出 */
	private OutputStream os;
	
	private String bindId;

	public ExcelParam() {
		super();
	}

	public ExcelParam(String packageName, String taskId, String fileName, String sheetName, String title,
			List<?> dataRows, Integer tableHeadStartRow) {
		super();
		this.packageName = packageName;
		this.taskId = taskId;
		this.fileName = fileName;
		this.sheetName = sheetName;
		this.title = title;
		this.dataRows = dataRows;
		this.tableHeadStartRow = tableHeadStartRow;
	}

	public ExcelParam(String packageName, String taskId, String fileName, String sheetName, String title,
			List<?> dataRows, Integer tableHeadStartRow, OutputStream os) {
		super();
		this.packageName = packageName;
		this.taskId = taskId;
		this.fileName = fileName;
		this.sheetName = sheetName;
		this.title = title;
		this.dataRows = dataRows;
		this.tableHeadStartRow = tableHeadStartRow;
		this.os = os;
	}
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<?> getDataRows() {
		return dataRows;
	}

	public void setDataRows(List<?> dataRows) {
		this.dataRows = dataRows;
	}

	public Integer getTableHeadStartRow() {
		return tableHeadStartRow;
	}

	public void setTableHeadStartRow(Integer tableHeadStartRow) {
		this.tableHeadStartRow = tableHeadStartRow;
	}

	public OutputStream getOs() {
		return os;
	}

	public void setOs(OutputStream os) {
		this.os = os;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}
	
}