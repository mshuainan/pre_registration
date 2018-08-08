package com.elementspeed.tool.excel.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.elementspeed.common.env.CommonPropertiesLoad;
import com.elementspeed.framework.common.util.StringUtil;
import com.elementspeed.tool.excel.common.RefletHelper;
import com.elementspeed.tool.excel.common.SheetColumnTemplate;
import com.elementspeed.tool.excel.common.SheetHeadInfo;
import com.elementspeed.tool.excel.common.SheetHeadTemplate;
import com.elementspeed.tool.excel.common.SheetInfo;
import com.elementspeed.tool.excel.util.ConvertUtil;
import com.elementspeed.tool.excel.util.ExcelUtil;
import com.elementspeed.tool.excel.util.XmlUtil;

public class ExcelWriter {
	private static final Log logger = LogFactory.getLog(ExcelWriter.class);
	private static final int WIDTH_UNIT = 256; // 设置列宽单位
	private static final String PATH_PREFIX = "config/excel/";
	private static final String EXPORT_NAME = "/export.xml";

	/**
	 * 导出动态列头的excel
	 * 
	 * @param fileName
	 * @param sheetName
	 * @param title
	 * @param sheetInfo
	 * @param headInfo
	 * @param dataRows
	 * @param os
	 * @throws RuntimeException
	 * @throws IOException
	 */
	public static void writeDynamicColumnExcel(String fileName,
			String sheetName, String title, SheetInfo sheetInfo,
			SheetHeadInfo headInfo, List<?> dataRows, OutputStream os)
					throws RuntimeException, IOException {
		writeDynamicColumnExcelCommon(fileName, sheetName, title, sheetInfo,
				headInfo, dataRows, os);
	}

	private static void writeDynamicColumnExcelCommon(String fileName,
			String sheetName, String title, SheetInfo sheetInfo,
			SheetHeadInfo headInfo, List<?> dataRows, OutputStream os) {
		int maxRow=sheetInfo.getEndRow();
		List<SheetColumnTemplate> columnTemplates = sheetInfo
				.getColumnTemplates();
		Workbook wb = null;
		try {
			int totle = dataRows==null?0:dataRows.size();// 获取List集合的size
			int avg = totle / maxRow;
			wb = createWorkbook(fileName, wb);
			for (int i = 0; i < avg + 1; i++) {
				
				CellStyle unlockedCellStyle = wb.createCellStyle();  
		        unlockedCellStyle.setLocked(false);
		        
				Sheet sheet = wb.createSheet(sheetName == null || sheetName
						.equals("") ? fileName + (i + 1) : sheetName.trim()
						+ (i + 1));
				sheet.setDefaultColumnWidth(18);
				CreationHelper createHelper = wb.getCreationHelper();
				CellStyle dateStyle_Y_M_D_H_M_S = wb.createCellStyle();
				setDateStyle(createHelper, dateStyle_Y_M_D_H_M_S,
						ConvertUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS, wb);

				CellStyle dateStyle_Y_M_D = wb.createCellStyle();
				setDateStyle(createHelper, dateStyle_Y_M_D,
						ConvertUtil.DATE_FORMAT_YYYY_MM_DD, wb);
				int startRow = 0;
				int startColumn = sheetInfo.getStartCol(); // 开始列索引
				// 导出表标题设置
				if(StringUtil.isNotEmpty(title)){
					setSheetTitle(title, columnTemplates, wb, sheet, startRow,
							startColumn);
					// 导出表头设置
					startRow++;
				}
				int num = i * maxRow;
				int headRowIndex = setDynamicSheetHead(headInfo, wb, sheet,
						startRow,maxRow);
				if (dataRows != null && dataRows.size() > 0) {
					// 导出表数据设置
					setSheetDatas(dataRows, columnTemplates, wb, sheet,
							createHelper, dateStyle_Y_M_D_H_M_S,
							dateStyle_Y_M_D, startColumn, headRowIndex, num,
							maxRow);
				}
			}
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e.getCause());
			throw new RuntimeException(e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error(e, e.getCause());
				}
			}
		}
	}

	/**
	 * 不需要ExcelConfig.xml，只需要写export.xml在config/excel/your packageName/export.xml
	 * @author sai.deng on 2016年4月12日 下午3:00:27
	 * @param packageName
	 * @param taskId
	 * @param fileName
	 * @param sheetName
	 * @param title
	 * @param dataRows
	 * @param os
	 * @throws RuntimeException
	 * @throws IOException
	 */
	public static void writeExcelByPackage(String packageName, String taskId, String fileName,
			String sheetName, String title, List<?> dataRows, OutputStream os) throws RuntimeException, IOException {
		String path = PATH_PREFIX + packageName + EXPORT_NAME;
		writeExcelByXmlCommon(taskId, fileName, sheetName, title, dataRows, os, path);
	}
	
	private static void writeExcelByXmlCommon(String taskId, String fileName,
			String sheetName, String title, List<?> dataRows, OutputStream os,
			String path) {
		SheetInfo sheetInfo = XmlUtil.readExcelImportExportTemplateXml(path, taskId);
		List<SheetColumnTemplate> columnTemplates = sheetInfo.getColumnTemplates();
		Workbook wb = null;
		try {  
			wb = createWorkbook(fileName, wb);
		    Sheet sheet = wb.createSheet((sheetName==null||sheetName.equals(""))?"sheet1":sheetName.trim());
		    sheet.setDefaultColumnWidth(18);
		    CreationHelper createHelper = wb.getCreationHelper();
		    CellStyle dateStyle_Y_M_D_H_M_S = wb.createCellStyle();
		    setDateStyle(createHelper, dateStyle_Y_M_D_H_M_S, ConvertUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS,wb);
		    
		    CellStyle dateStyle_Y_M_D = wb.createCellStyle();
		    setDateStyle(createHelper, dateStyle_Y_M_D, ConvertUtil.DATE_FORMAT_YYYY_MM_DD,wb);
		    int startRow = 0; 
		    int startColumn = sheetInfo.getStartCol(); //开始列索引  
		    //导出表标题设置
		    setSheetTitle(title, columnTemplates, wb, sheet, startRow, startColumn);				
		    //导出表头设置
		    startRow++;
		    int headRowIndex = setSheetHead(columnTemplates, wb, sheet, startRow, startColumn);
		    if(dataRows != null && dataRows.size() > 0) {
		    	//导出表数据设置
		    	setSheetDatas(dataRows, columnTemplates, wb, sheet,
						createHelper, dateStyle_Y_M_D_H_M_S, dateStyle_Y_M_D, startColumn, headRowIndex);
		    }
		    wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e.getCause());
			throw new RuntimeException(e);
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error(e, e.getCause());
				}
			}
		}
	}
	
	
	
	/**
	 * 导出excel并直接输出，适用于大部分场景（不需要手动再扩展的情况）
	 * xml配置文件的路径必须在：config/excel/目录下
	 * @param excelParam
	 */
	public static void writeExcelByPackage(ExcelParam excelParam) {
		Workbook wb = writeExcelByPackage2(excelParam);
		if (wb == null) {
			return;
		}
		OutputStream os = excelParam.getOs();
		if (os == null) {
			logger.error("OutputStream is null，please check input param.");
			return;
		}
		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ExcelUtil.closeOutputStream(os);
	}
	
	/**
	 * 导出excel时仅设置相关单元格数据，并返回Workbook，并不输出，为后续手动扩展做准备
	 * xml配置文件的路径必须在：config/excel/目录下
	 * @param excelParam
	 * @return
	 */
	public static Workbook writeExcelByPackage2(ExcelParam excelParam) {
		String path = PATH_PREFIX + excelParam.getPackageName() + EXPORT_NAME;
		return writeExcelCommon(excelParam.getTaskId(), excelParam.getFileName(), 
				excelParam.getSheetName(), excelParam.getTitle(), excelParam.getDataRows(),
				excelParam.getTableHeadStartRow(), path);
	}

	/**
	 * 导出excel时仅设置相关单元格数据，并返回Workbook，并不输出，为后续手动扩展做准备
	 * 
	 * @author sai.deng on 2015年8月31日 下午10:10:25
	 * @param bindId
	 * @param taskId
	 * @param fileName
	 * @param sheetName
	 * @param title
	 * @param dataRows
	 * @param tableHeadStartRow
	 *            表头开始行索引
	 * @throws RuntimeException
	 */
	public static Workbook writeExcelByCustom(String bindId, String taskId,
			String fileName, String sheetName, String title, List<?> dataRows,
			Integer tableHeadStartRow) throws RuntimeException {
		String path = XmlUtil.readExcelConfigXml(bindId);
		return writeExcelCommon(taskId, fileName, sheetName, title, dataRows,
				tableHeadStartRow, path);
	}

	private static Workbook writeExcelCommon(String taskId, String fileName,
			String sheetName, String title, List<?> dataRows,
			Integer tableHeadStartRow, String path) {
		SheetInfo sheetInfo = XmlUtil.readExcelImportExportTemplateXml(path,
				taskId);
		//  每个工作表格最多存储65536条数据（注：excel表格一个工作表可以存储65536条）
		int maxRow = sheetInfo.getEndRow();
		List<SheetColumnTemplate> columnTemplates = sheetInfo.getColumnTemplates();
		Workbook wb = null;
		try {
			int totle = dataRows.size();// 获取List集合的size
			int avg = totle / maxRow;
			wb = createWorkbook(fileName, wb);
			for (int i = 0; i < avg + 1; i++) {
		        long startTime=System.currentTimeMillis();
		        System.out.println("开始 sheet"+i+":"+startTime); 
		        Sheet sheet = wb.createSheet(sheetName == null || sheetName
						.equals("") ? title + " -" + (i + 1) : sheetName
						.trim() + " -" + (i + 1));
				sheet.setDefaultColumnWidth(18);
				CreationHelper createHelper = wb.getCreationHelper();
				CellStyle dateStyleHMS = wb.createCellStyle();
				setDateStyle(createHelper, dateStyleHMS,
						ConvertUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS, wb);

				CellStyle dateStyleYMD = wb.createCellStyle();
				setDateStyle(createHelper, dateStyleYMD,
						ConvertUtil.DATE_FORMAT_YYYY_MM_DD, wb);
				int startRow = 0;
				int startColumn = sheetInfo.getStartCol(); // 开始列索引
				// 导出表头设置
				if (tableHeadStartRow != null
						&& tableHeadStartRow.equals(Integer.MIN_VALUE)) {
					tableHeadStartRow = 0;
				} else {
					if (tableHeadStartRow == null
							|| tableHeadStartRow.intValue() < 1) {
						tableHeadStartRow = 1;
					}
					// 导出表标题设置
					setSheetTitle(title, columnTemplates, wb, sheet, startRow,
							startColumn);
				}
				int num = i * maxRow;
				int headRowIndex = setSheetHead(columnTemplates, wb, sheet,
						tableHeadStartRow, startColumn);
				if (dataRows != null && dataRows.size() > 0) {
					// 导出表数据设置
					setSheetDatas(dataRows, columnTemplates, wb, sheet,
							createHelper, dateStyleHMS,
							dateStyleYMD, startColumn, headRowIndex, num,
							maxRow);
				}
				long endTime=System.currentTimeMillis();
				System.out.println("结束 sheet"+i+":"+endTime); 
				System.out.println("耗时："+(endTime-startTime));
			}
			return wb;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e.getCause());
			throw new RuntimeException(e);
		}
	}

	private static void setSheetDatas(List<?> dataRows,
			List<SheetColumnTemplate> columnTemplates, Workbook wb,
			Sheet sheet, CreationHelper createHelper,
			CellStyle dateStyleHMS, CellStyle dateStyleYMD,
			int startColumn, int headRowIndex,CellStyle unlockedCellStyle ) throws NoSuchFieldException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		Row row;
		Cell cell;
		int columnIndex;
		Map<String, CellStyle> dateCellStyleMap = new HashMap<String, CellStyle>();
		CellStyle dataStyle = wb.createCellStyle();
		setCellDataStyle(dataStyle, wb);
		setCellDataStyle(unlockedCellStyle, wb);
		int totalRows = dataRows.size();
		for (int rowIndex = 0; rowIndex < totalRows; rowIndex++) {
			Object obj = dataRows.get(rowIndex);
			row = sheet.createRow(headRowIndex + rowIndex + 1);
			columnIndex = startColumn;
			for (SheetColumnTemplate columnTemplate : columnTemplates) {
				cell = row.createCell(columnIndex);
				String fieldName = columnTemplate.getOutputColumn();
				if (StringUtil.isEmpty(fieldName)) {
					continue;
				}
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				// Field field = obj.getClass().getDeclaredField(fieldName);
				// 先从当前类获取定义的字段，若没找到，递归从父类一直往上找，直到找到为止
				Field field = RefletHelper.getDeclaredField(obj.getClass(),
						fieldName);
				// Method getMethod = obj.getClass().getMethod(getMethodName,
				// new Class[]{});
				Method getMethod = RefletHelper.getMethod(obj.getClass(),
						getMethodName);
				Object getMethodValue = getMethod.invoke(obj, new Object[] {});
				if (getMethodValue == null
						|| getMethodValue.toString().equals("")) {
					if (columnTemplate.getType().equalsIgnoreCase("Double")) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					} else {
						cell.setCellType(Cell.CELL_TYPE_BLANK);
					}
					cell.setCellValue("");
				} else {
					setCellValue(dateStyleHMS, dateStyleYMD, cell,
							columnTemplate, getMethodValue, createHelper, wb,
							field.getType().getSimpleName(), dateCellStyleMap);
				}
				// 隐藏的列不可编辑 added by szl on 2017-01-16
				exportExcelIsHidden(sheet, cell, columnTemplate,wb);
				columnIndex++;
			}
		}
	}

	private static void setSheetDatas(List<?> dataRows,
			List<SheetColumnTemplate> columnTemplates, Workbook wb,
			Sheet sheet, CreationHelper createHelper,
			CellStyle dateStyle_Y_M_D_H_M_S, CellStyle dateStyle_Y_M_D,
			int startColumn, int headRowIndex) throws NoSuchFieldException,
			NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Row row;
		Cell cell;
		int columnIndex;
		Map<String, CellStyle> dateCellStyleMap = new HashMap<String, CellStyle>();
		CellStyle dataStyle = wb.createCellStyle();		    	
		setCellDataStyle(dataStyle,wb);
		int totalRows = dataRows.size();
		for(int rowIndex=0; rowIndex < totalRows; rowIndex++) {  
			Object obj = dataRows.get(rowIndex);
			row = sheet.createRow( headRowIndex + rowIndex + 1 );  
			columnIndex = startColumn;
			for (SheetColumnTemplate columnTemplate : columnTemplates) {
				cell = row.createCell(columnIndex);
				cell.setCellStyle(dataStyle); 
				String fieldName = columnTemplate.getOutputColumn();
				if(StringUtil.isEmpty(fieldName))
					continue;
				String getMethodName ="get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
//				Field field = obj.getClass().getDeclaredField(fieldName);
				//先从当前类获取定义的字段，若没找到，递归从父类一直往上找，直到找到为止
				Field field = RefletHelper.getDeclaredField(obj.getClass(), fieldName);
//				Method getMethod = obj.getClass().getMethod(getMethodName, new Class[]{});
				Method getMethod = RefletHelper.getMethod(obj.getClass(), getMethodName);
				Object getMethodValue = getMethod.invoke(obj, new Object[]{});
				if (getMethodValue == null || getMethodValue.toString().equals("")) {
					if (columnTemplate.getType().equalsIgnoreCase("Double")) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					} else {
						cell.setCellType(Cell.CELL_TYPE_BLANK);
					}
					cell.setCellValue("");
				} else {
					setCellValue(dateStyle_Y_M_D_H_M_S, dateStyle_Y_M_D,
							cell, columnTemplate, getMethodValue, createHelper, wb, 
							field.getType().getSimpleName(), dateCellStyleMap);
				}
				columnIndex++;
			}
		}
	}
	/**
	 * 导出多个Sheet的Excel
	 * @param packageName 包名路径
	 * @param taskId 任务ID
	 * @param fileName 文件名称
	 * @param titleMap 标题
	 * @param dataMap 导出数据集合
	 * @param sheetNameMap sheet名称
	 * @param tableHeadStartRow 表头开始行
	 */
	public static Workbook writeExcelWithMultSheet(String packageName,
			String taskId, String fileName, Map<String, String> titleMap,
			Map<String, List<?>> dataMap, Map<String, String> sheetNameMap,
			Integer tableHeadStartRow) throws RuntimeException {
		String path = PATH_PREFIX + packageName+ EXPORT_NAME;
		SheetInfo sheetInfo = XmlUtil.readExcelImportExportTemplateXml(path,
				taskId);
		List<SheetColumnTemplate> columnTemplates = sheetInfo
				.getColumnTemplates();
		Workbook wb = null;
		try {
			wb = createWorkbook(fileName, wb);
			Iterator<Entry<String, List<?>>> it = dataMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, List<?>> entry = it.next();
				List<?> dataRows = entry.getValue(); // 导出列表数据
				String key = entry.getKey(); // sheetName
				String title = titleMap.get(key); // 第一行标题
				String sheetName = sheetNameMap.get(key); // sheetName
				CellStyle unlockedCellStyle = wb.createCellStyle();  
		        unlockedCellStyle.setLocked(false);
				// 创建Sheet
				Sheet sheet = wb
						.createSheet(StringUtil.isEmpty(sheetName) ? "sheet1"
								: sheetName.trim());
				sheet.setDefaultColumnWidth(18);
				CreationHelper createHelper = wb.getCreationHelper();
				CellStyle dateStyle_Y_M_D_H_M_S = wb.createCellStyle();
				setDateStyle(createHelper, dateStyle_Y_M_D_H_M_S,
						ConvertUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS, wb);
				CellStyle dateStyle_Y_M_D = wb.createCellStyle();
				setDateStyle(createHelper, dateStyle_Y_M_D,
						ConvertUtil.DATE_FORMAT_YYYY_MM_DD, wb);
				int startRow = 0;
				int startColumn = sheetInfo.getStartCol(); // 开始列索引
				// 导出表头设置
				if (tableHeadStartRow != null
						&& tableHeadStartRow.equals(Integer.MIN_VALUE)) {
					tableHeadStartRow = 0;
				} else if (tableHeadStartRow == null
						|| tableHeadStartRow.intValue() < 1) {
					tableHeadStartRow = 1;
				}
				setSheetTitle(title, columnTemplates, wb, sheet, startRow,
						startColumn); // 导出表标题设置
				int headRowIndex = setSheetHead(columnTemplates, wb, sheet,
						tableHeadStartRow, startColumn);
				if (dataRows != null && dataRows.size() > 0) {
					// 导出表数据设置
					setSheetDatas(dataRows, columnTemplates, wb, sheet,
							createHelper, dateStyle_Y_M_D_H_M_S,
							dateStyle_Y_M_D, startColumn, headRowIndex,unlockedCellStyle);
				}
			}
			return wb;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e.getCause());
			throw new RuntimeException(e);
		}
	}

	/**
	 * 导出多个Sheet的Excel
	 * 
	 * @param dataRows
	 * @param columnTemplates
	 * @param wb
	 * @param sheet
	 * @param createHelper
	 * @param hmsStyle
	 * @param ymdStyle
	 * @param startColumn
	 * @param headRowIndex
	 * @param num
	 * @param mus
	 * @throws NoSuchFieldException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @author szl
	 * @datetime 2017年1月12日下午2:01:39
	 */
	private static void setSheetDatas(List<?> dataRows,
			List<SheetColumnTemplate> columnTemplates, Workbook wb,
			Sheet sheet, CreationHelper createHelper,
			CellStyle hmsStyle, CellStyle ymdStyle,
			int startColumn, int headRowIndex, int num, int maxRow)
			throws NoSuchFieldException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		Row row;
		Cell cell;
		int columnIndex;
		Map<String, CellStyle> dateCellStyleMap = new HashMap<String, CellStyle>();
		CellStyle dataStyle = wb.createCellStyle();
		setCellDataStyle(dataStyle, wb);
		int totalRows = dataRows.size();
		int index = 0;
		for (int rowIndex = num; rowIndex < totalRows; rowIndex++) {
			if (index == maxRow) {// 判断index == mus的时候跳出当前for循环
				break;
			}
			Object obj = dataRows.get(rowIndex);
			row = sheet.createRow(headRowIndex + index + 1);
			columnIndex = startColumn;
			for (SheetColumnTemplate columnTemplate : columnTemplates) {

				cell = row.createCell(columnIndex);
				String fieldName = columnTemplate.getOutputColumn();
				if (StringUtil.isEmpty(fieldName)) {
					continue;
				}
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				// Field field = obj.getClass().getDeclaredField(fieldName);
				// 先从当前类获取定义的字段，若没找到，递归从父类一直往上找，直到找到为止
				Field field = RefletHelper.getDeclaredField(obj.getClass(),
						fieldName);
				// Method getMethod = obj.getClass().getMethod(getMethodName,
				// new Class[]{});
				Method getMethod = RefletHelper.getMethod(obj.getClass(),
						getMethodName);
				Object getMethodValue = getMethod.invoke(obj, new Object[] {});
				if (getMethodValue == null
						|| getMethodValue.toString().equals("")) {
					if (columnTemplate.getType().equalsIgnoreCase("Double")) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					} else {
						cell.setCellType(Cell.CELL_TYPE_BLANK);
					}
					cell.setCellValue("");
				} else {
					setCellValue(dataStyle, ymdStyle, cell,
							columnTemplate, getMethodValue, createHelper, wb,
							field.getType().getSimpleName(), dateCellStyleMap);
				}
				exportExcelIsHidden(sheet, cell, columnTemplate,wb);
				columnIndex++;
			}
			index++;
		}
	}

	/**
	 * 导出Excel是否隐藏（true:是，false:否）
	 * 
	 * @param sheet
	 * @param cell
	 * @param isHidden
	 * @param columnTemplate
	 * @author szl
	 * @param unlockedCellStyle 
	 * @param dataStyle 
	 * @param wb 
	 * @datetime 2017年1月17日下午3:50:29
	 */
	private static void exportExcelIsHidden(Sheet sheet, Cell cell,
			SheetColumnTemplate columnTemplate, Workbook wb) {
		// 设置xml里的属性 width="0"
		CellStyle style = cell.getCellStyle();
		String width = columnTemplate.getColumnWidth();
		if (StringUtil.isNotEmpty(width) && Integer.parseInt(width) == 0) {
			// 设置xml里ValueColumn="A"对应excelA列
			String output = columnTemplate.getValueColumn();
			if (StringUtil.isNotEmpty(output)) {
				sheet.protectSheet(CommonPropertiesLoad.getValueByKey("export.excel.password"));//password
				// 得到列索引
				int hiddenIndex = ExcelUtil.getColumnIndex(output);
				sheet.setColumnHidden(hiddenIndex, true);
				style.setLocked(true);
				setCellDataStyle(style,wb);
				cell.setCellStyle(style);
			}
		} else {
			style.setLocked(false);
			setCellDataStyle(style,wb);
			cell.setCellStyle(style);
		}
	}

	private static int setDynamicSheetHead(SheetHeadInfo headInfo, Workbook wb,
			Sheet sheet, int rowIndex, int maxRow) {
		Row row;
		Cell cell;
		for (int i = 0; i < headInfo.getHeadRows(); i++) {
			row = sheet.createRow(rowIndex + i);
			CellStyle headStyle = wb.createCellStyle();
			for (SheetHeadTemplate columnTemplate : headInfo.getHeadTemplates().get(i)) {
				setColumnWidth(sheet, columnTemplate.getColumnIndex(), columnTemplate.getColumnWidth().trim());
				cell = row.createCell(columnTemplate.getColumnIndex());
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(columnTemplate.getColumnCaption());
				if (columnTemplate.isMerge()) {
					sheet.addMergedRegion(new CellRangeAddress(columnTemplate
							.getRowIndex(), columnTemplate.getRowIndex()
							+ columnTemplate.getRowspan(), columnTemplate
							.getColumnIndex(), columnTemplate.getColumnIndex()
							+ columnTemplate.getCoslpan()));
				}
				if (columnTemplate.isValid()) {
					//如果是设置列表，要设置一下
					DVConstraint constraint = DVConstraint.createExplicitListConstraint(columnTemplate.getValidList().toArray(new String[]{}));
			        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列  
			        CellRangeAddressList regions = new CellRangeAddressList(rowIndex,  
			                maxRow, columnTemplate
							.getColumnIndex(), columnTemplate.getColumnIndex()); 
			     // 数据有效性对象  
			        HSSFDataValidation data_validation_list = new HSSFDataValidation(  
			                regions, constraint);  
			        sheet.addValidationData(data_validation_list);  
				}
				headStyle.setAlignment(CellStyle.ALIGN_CENTER); // 居中
				setBorder(headStyle);
				setHeadStyle(headStyle, wb);
				cell.setCellStyle(headStyle);
			}
		}
		return headInfo.getHeadRows();
	}

	/**
	 * 设置表头
	 * @param columnTemplates
	 * @param wb
	 * @param sheet
	 * @param tableHeadStartRow
	 * @param startColumn
	 * @return
	 */
	private static int setSheetHead(List<SheetColumnTemplate> columnTemplates,
			Workbook wb, Sheet sheet, int tableHeadStartRow, int startColumn) {
		Row row;
		Cell cell;
		int headRowIndex = tableHeadStartRow; // 表头行索引从1开始
		row = sheet.createRow(headRowIndex);
		int columnIndex = startColumn;
		CellStyle headStyle = wb.createCellStyle();
		setHeadStyle(headStyle, wb);
		for (SheetColumnTemplate columnTemplate : columnTemplates) {
			setColumnWidth(sheet, columnIndex, columnTemplate.getColumnWidth().trim());
			cell = row.createCell(columnIndex);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(columnTemplate.getColumnCaption());
			cell.setCellStyle(headStyle);
			columnIndex++;
		}
		return headRowIndex;
	}

	/**
	 * 设置列宽
	 * @param sheet
	 * @param columnIndex
	 * @param width
	 */
	private static void setColumnWidth(Sheet sheet, int columnIndex, String width) {
		// setColumnWidth(columnIndex, width)，其中width：以一个字符的1/256的宽度作为一个单位，所以需要256*设置宽度
		// http://poi.apache.org/apidocs/org/apache/poi/ss/usermodel/Sheet.html#setColumnWidth(int,%20int)
		if (width != null && width.length() > 0) {
			sheet.setColumnWidth(columnIndex, WIDTH_UNIT * Integer.parseInt(width));
		} else {
			sheet.setColumnWidth(columnIndex, WIDTH_UNIT * 18);
		}
	}

	/**
	 * 设置工作表标题
	 * 
	 * @author sai.deng on 2012-10-21
	 * @param title
	 * @param columnTemplates
	 * @param wb
	 * @param sheet
	 * @param startRow
	 * @param startColumn
	 */
	private static void setSheetTitle(String title,
			List<SheetColumnTemplate> columnTemplates, Workbook wb,
			Sheet sheet, int startRow, int startColumn) {
		Row row;
		Cell cell;
		row = sheet.createRow(startRow);
		cell = row.createCell(startColumn);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(title);
		// 合并单元格
		sheet.addMergedRegion(new CellRangeAddress(startRow, // first row
																// (0-based)
				startRow, // last row (0-based)
				startColumn, // first column (0-based)
				columnTemplates.size() - 1 + startColumn // last column
															// (0-based)
		));
		CellStyle titleStyle = wb.createCellStyle();
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		setBorder(titleStyle);
		cell.setCellStyle(titleStyle);
		setFont(wb, titleStyle, 20);
	}

	private static Workbook createWorkbook(String fileName, Workbook wb) {
		String fileExtName = fileName.substring(fileName.lastIndexOf("."));
		if (fileExtName.equalsIgnoreCase(".xls")) {
			wb = new HSSFWorkbook();
		} else if (fileExtName.equalsIgnoreCase(".xlsx")) {
			wb = new XSSFWorkbook();
		} else {
			throw new RuntimeException("invalid excel file name.");
		}
		return wb;
	}

	private static void setDateStyle(CreationHelper createHelper,
			CellStyle dateStyle, String dateFormat, Workbook wb) {
		dateStyle.setDataFormat(createHelper.createDataFormat().getFormat(
				dateFormat));
		setCellDataStyle(dateStyle, wb);
	}

	/**
	 * 设置表头样式
	 * 
	 * @author sai.deng on 2012-10-21
	 * @param headStyle
	 */
	private static void setHeadStyle(CellStyle headStyle, Workbook wb) {
		setBorder(headStyle);
		// 表头加灰色底部
		headStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		setFont(wb, headStyle, 11);
	}

	public static void setFont(Workbook wb, CellStyle cellStyle, int fontSize) {
		// Create a new font and alter it.
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) fontSize);
		font.setFontName("宋体");

		cellStyle.setFont(font);
	}

	private static void setCellDataStyle(CellStyle cellStyle, Workbook wb) {
		setBorder(cellStyle);
		setFont(wb, cellStyle, 10);
	}

	public static void setBorder(CellStyle cellStyle) {
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
	}

	private static void setCellValue(CellStyle hms,
			CellStyle ymd, Cell cell,
			SheetColumnTemplate columnTemplate, Object getMethodValue,
			CreationHelper createHelper, Workbook wb, String fieldTypeName,
			Map<String, CellStyle> dateCellStyleMap) {
		if (columnTemplate.getType().equalsIgnoreCase("Date")
				|| columnTemplate.getType().equalsIgnoreCase("Timestamp")
				|| fieldTypeName.equalsIgnoreCase("Date")
				|| fieldTypeName.equalsIgnoreCase("Timestamp")) {
			String format = columnTemplate.getFormat();
			if (format != null && format.length() > 0) {
				format = format.trim();
				CellStyle cellStyle = null;
				if (!dateCellStyleMap.containsKey(format)) {
					cellStyle = wb.createCellStyle();
					setDateStyle(createHelper, cellStyle, format, wb);
					dateCellStyleMap.put(format, cellStyle);
				} else {
					cellStyle = dateCellStyleMap.get(format);
				}
				cell.setCellStyle(cellStyle);
				if (getMethodValue instanceof Date) {
					Date d = (Date) getMethodValue;
					cell.setCellValue(d);
				} else if (getMethodValue instanceof Timestamp) {
					Timestamp d = (Timestamp) getMethodValue;
					cell.setCellValue(d);
				}
			} else if (getMethodValue instanceof Date) {
				Date d = (Date) getMethodValue;
				cell.setCellValue(d);
				cell.setCellStyle(hms);
			} else if (getMethodValue instanceof Timestamp) {
				Timestamp d = (Timestamp) getMethodValue;
				cell.setCellValue(d);
				cell.setCellStyle(hms);
			} else if (getMethodValue instanceof String) {
				String d = (String) getMethodValue;
				cell.setCellValue(d);
			}
		} else if (columnTemplate.getType().equalsIgnoreCase("String")) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(getMethodValue.toString());
		} else if (columnTemplate.getType().equalsIgnoreCase("Double")) {
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(Double.valueOf(getMethodValue.toString()));
		} else if (columnTemplate.getType().equalsIgnoreCase("Integer")
				|| columnTemplate.getType().equalsIgnoreCase("int")) {
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(Integer.valueOf(getMethodValue.toString()));
		}
	}
}
