package com.elementspeed.tool.excel.util;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public class ExcelUtil {
	private static final Log log = LogFactory.getLog(ExcelUtil.class);
	
	/**
	 * 得到列的索引号
	 * @author sai.deng
	 * @param column
	 * @return
	 */
	public static int getColumnIndex(String column) {
		if (column == null || column.length() == 0) {
			throw new IllegalArgumentException("column is null");
		}
		int ret = 0;
		String msg = "column[" + column + "] is invalid,should in [A-IV] ";
		column = column.toUpperCase();
		if (column.length() == 1) {
			char c = column.charAt(0);
			if ((c >= 'A') && (c <= 'Z'))
				ret = c - 'A';
			else
				throw new IllegalArgumentException(msg);
		} else if (column.length() == 2) {
			int round = 0;
			char c = column.charAt(0);
			if ((c >= 'A') && (c <= 'I'))
				round = c - 'A' + 1;
			else {
				throw new IllegalArgumentException(msg);
			}
			c = column.charAt(1);
			if (round == 9) {
				if ((c >= 'A') && (c <= 'V')) {
					ret = round * 26 + c - 'A';
				}
				throw new IllegalArgumentException(msg);
			}

			if ((c >= 'A') && (c <= 'Z'))
				ret = round * 26 + c - 'A';
			else {
				throw new IllegalArgumentException(msg);
			}
		} else {
			throw new IllegalArgumentException("column[" + column
					+ "] is invalid,should in [A-IV] ");
		}
		return ret;
	}
	
	/**
	 * 该方法用来将具体的数据转换成Excel中的ABCD列
	 * @param int：需要转换成字母的数字
	 * @return column:ABCD列名称
	 * **/
    public static String excelColIndexToStr(int columnIndex) {
        if (columnIndex <= 0) {
            return null;
        }
        String columnStr = "";
        columnIndex--;
        do {
            if (columnStr.length() > 0) {
                columnIndex--;
            }
            columnStr = ((char) (columnIndex % 26 + (int) 'A')) + columnStr;
            columnIndex = (int) ((columnIndex - columnIndex % 26) / 26);
        } while (columnIndex > 0);
        return columnStr;
    }
	
	/**
	 * 得到单元格的内容
	 * @author sai.deng
	 * @param cell
	 * @return
	 */
	public static Object getCellContent(Cell cell) {
		if(cell == null) return "";
		Object content = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			String cellContent = cell.getRichStringCellValue().getString();
			if(cellContent != null) {
				cellContent = cellContent.trim();
			}
			content = cellContent;
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				content = cell.getDateCellValue();
			} else {
				content = cell.getNumericCellValue();
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			content = cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA:
			try {
				content = cell.getNumericCellValue();
			} catch (Exception e) { 
				String cc = cell.getRichStringCellValue().getString();
				if(cc != null) {
					cc = cc.trim();
				}
				content = cc;
			} 
			break;
		default:
			content = "";
		}
		return content==null?"":content;
	}


	/**
	 * 检查是否是有效的excel文件扩展名
	 * @author sai.deng on 2012-2-7
	 * @param filePath
	 * @return
	 */
	public static boolean isValidFileExt(String filePath) {
		if(filePath == null || filePath.equals(""))
			return false;
		
		if(filePath.toLowerCase().endsWith("xls".toLowerCase())
				|| filePath.toLowerCase().endsWith("xlsx".toLowerCase())) {
			return true;
		}else {
			return false;
		}	
	}
	
	public static void closeOutputStream(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
