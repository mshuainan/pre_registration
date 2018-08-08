package com.elementspeed.common.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elementspeed.tool.excel.writer.ExcelWriter;

@SuppressWarnings("serial")
public class ReportExportHelper implements Serializable {
	private static Logger logger = LoggerFactory.getLogger(ReportExportHelper.class);
//	private static final int WIDTH_UNIT = 256; //设置列宽单位
	
	/**
	 * 合并单元格，列合并
	 * @author sai.deng on 2015年5月24日 下午10:04:04
	 * @param sheet
	 * @param firstColumn
	 * @param lastColumn
	 * @param rowIndex
	 */
	protected static void setMergeColumn(Sheet sheet, int firstColumn, int lastColumn, int rowIndex) {
		//合并单元格 
		sheet.addMergedRegion(new CellRangeAddress(
				rowIndex, //first row (0-based)
				rowIndex, //last row  (0-based)
				firstColumn, //first column (0-based)
				lastColumn  //last column  (0-based)
		)); 
	}
	
	/**
	 * 设置合并单元格的样式(列)
	 * @author sai.deng on 2015年5月22日 上午10:15:05
	 * @param row
	 * @param cellStyle
	 * @param startIndex
	 * @param endIndex
	 */
	protected static void setMergeCellStyle(Row row, CellStyle cellStyle, int startIndex, int endIndex) {
		Cell cell;
		for (int i = startIndex; i <= endIndex; i++) {
			cell = row.createCell((short) i); 
			cell.setCellValue("");
			cell.setCellStyle(cellStyle);   
		}
	}
	
	/**
	 * 导出总价
	 * @param wb
	 */
	public static void exportTotal(Workbook wb,double totalPrice) {
		Sheet sheet = wb.getSheetAt(0); 
		CellStyle labelStyle = getLabelStyle(wb);
		CellStyle valueStyle = getValueStyle(wb);
		
		//第1行
		createRow1Total(sheet, labelStyle, valueStyle, totalPrice);
	}

	private static void createRow1Total(Sheet sheet,
			CellStyle labelStyle, CellStyle valueStyle , double totalPrice) {
		Row row;
		Cell cell;
		int rowIndex = 1;//第2行
		row = sheet.createRow(rowIndex);
		cell = row.createCell(0); //第1列 
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue("当前合计：");
		cell.setCellStyle(labelStyle);
		
		cell = row.createCell(1); //第2列
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(totalPrice);
		cell.setCellStyle(valueStyle);
	}

	private static CellStyle getValueStyle(Workbook wb) {
		CellStyle valueStyle = wb.createCellStyle();		
		valueStyle.setAlignment(CellStyle.ALIGN_LEFT);
		valueStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		ExcelWriter.setBorder(valueStyle);
		ExcelWriter.setFont(wb, valueStyle, 11);
		return valueStyle;
	}

	private static CellStyle getLabelStyle(Workbook wb) {
		CellStyle labelStyle = wb.createCellStyle();		
		labelStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		labelStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		ExcelWriter.setBorder(labelStyle);
		ExcelWriter.setFont(wb, labelStyle, 11);
		return labelStyle;
	}


	

	public static void closeOutputStream(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	
}
