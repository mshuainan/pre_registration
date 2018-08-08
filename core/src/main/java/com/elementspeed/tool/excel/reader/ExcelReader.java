package com.elementspeed.tool.excel.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.elementspeed.tool.excel.common.ProjectConsts;
import com.elementspeed.tool.excel.common.RefletHelper;
import com.elementspeed.tool.excel.common.SheetColumnTemplate;
import com.elementspeed.tool.excel.common.SheetInfo;
import com.elementspeed.tool.excel.util.ConvertUtil;
import com.elementspeed.tool.excel.util.DecimalFormatUtil;
import com.elementspeed.tool.excel.util.ExcelUtil;
import com.elementspeed.tool.excel.util.XmlUtil;

public class ExcelReader {
	private static final Log log = LogFactory.getLog(ExcelReader.class);
	
	/**
	 * 读取普通的Excel
	 * @param filaPath
	 * @param taskId
	 * @param excelServerFilePath
	 * @param j 
	 * @param startCol 
	 * @return
	 * @throws RuntimeException
	 */
	public static List<List<String>> readExcel(String excelServerFilePath,int startRow, int startCol, int colNums) 
			throws RuntimeException {
		List<List<String>> dataList = null;
		InputStream is = null;
		Workbook wb = null;
		try {
			File file = new File(excelServerFilePath);
			is = new FileInputStream(file);
			wb = WorkbookFactory.create(is);
			//默认取第一张工作表
			Sheet sheet = wb.getSheetAt(0);			
			int totalRows = sheet.getPhysicalNumberOfRows();
			dataList = new ArrayList<List<String>>();
			Row row = null;
			for (int rowIndex = startRow; rowIndex < totalRows; ++rowIndex) {
				List<String> data = new ArrayList<String>();
				row = sheet.getRow(rowIndex);
				if(row == null) { 
					continue;
				}
				for(int colIndex = startCol;colIndex<colNums;colIndex++){
					data.add(String.valueOf(ExcelUtil.getCellContent(row.getCell(colIndex))));
				}
				dataList.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e, e.getCause());
			throw new RuntimeException(e);
		}finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.error(e, e.getCause());
				}
			}
		}		
		return dataList;
	}
	
	/**
	 * poi读取excel文件公用方法,支持2003，2007等格式
	 * @author sai.deng
	 * @param filaPath import.xml路径：excel/pe/newdlvplan/import_newDlvPlan.xml
	 * @param taskId 对应于具体的导入模板中配置的taskId
	 * @param excelServerFilePath 服务器excel文件的存放路径
	 */
	public static List readExcelByFielPath(String filaPath, String taskId, String excelServerFilePath) 
			throws RuntimeException {
		List objs = null;
		SheetInfo sheetInfo = XmlUtil.readExcelImportExportTemplateXml(filaPath, taskId);
		List<SheetColumnTemplate> columnTemplates = sheetInfo.getColumnTemplates();
		InputStream is = null;
		Workbook wb = null;
		try {
			File file = new File(excelServerFilePath);
			is = new FileInputStream(file);
			wb = WorkbookFactory.create(is);
			CellStyle dateStyle = setCellDateStyle(wb);
	
			//默认取第一张工作表
			Sheet sheet = wb.getSheetAt(sheetInfo.getSheetIndex());
			int totalRows = sheet.getPhysicalNumberOfRows();
			if(totalRows > sheetInfo.getEndRow()) {
				totalRows = sheetInfo.getEndRow(); //最大取配置文件的行数，如果不超过以实际的行数为准
			}
			objs = new ArrayList();
			Row row = null;
			for (int rowIndex = sheetInfo.getStartRow(); rowIndex < totalRows; ++rowIndex) {
				row = sheet.getRow(rowIndex);
				if(row == null) { 
					continue;
				}
				Object obj = Class.forName(sheetInfo.getClazz()).newInstance();
				setColumnPropertyValue(columnTemplates, row, obj, dateStyle); //通过反射动态给po的列属性赋值
				objs.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e, e.getCause());
			throw new RuntimeException(e);
		}finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.error(e, e.getCause());
				}
			}
		}		
		return objs;
	}
	
	/**
	 * poi读取excel文件公用方法,支持2003，2007等格式
	 * 注意：要求配置文件名为ExcelConfig.xml，并且编译后放在WEB-INF/classes/excel/目录下面
	 * @author sai.deng
	 * @param bindId ExecelConfig.xml中配置的bindId，对应于一个模块ID
	 * @param taskId 对应于具体的导入模板中配置的taskId
	 * @param excelServerFilePath 服务器excel文件的存放路径
	 * @return
	 * @throws InvalidExcelContentException 
	 */
	public static List readExcelByXml(String bindId, String taskId, String excelServerFilePath) throws RuntimeException {
		List objs = null;
		String path = XmlUtil.readExcelConfigXml(bindId);
		SheetInfo sheetInfo = XmlUtil.readExcelImportExportTemplateXml(path, taskId);
		List<SheetColumnTemplate> columnTemplates = sheetInfo.getColumnTemplates();
		InputStream is = null;
		Workbook wb = null;
		try {
			File file = new File(excelServerFilePath);
			is = new FileInputStream(file);
			wb = WorkbookFactory.create(is);
			CellStyle dateStyle = setCellDateStyle(wb);
	
			//默认取第一张工作表
			Sheet sheet = wb.getSheetAt(sheetInfo.getSheetIndex());			
			int totalRows = sheet.getPhysicalNumberOfRows();
			if(totalRows > sheetInfo.getEndRow()) {
				totalRows = sheetInfo.getEndRow(); //最大取配置文件的行数，如果不超过以实际的行数为准
			}
			objs = new ArrayList();
			Row row = null;
			for (int rowIndex = sheetInfo.getStartRow(); rowIndex < totalRows; ++rowIndex) {
				row = sheet.getRow(rowIndex);
				if(row == null) { 
					continue;
				}
				Object obj = Class.forName(sheetInfo.getClazz()).newInstance();
				setColumnPropertyValue(columnTemplates, row, obj, dateStyle); //通过反射动态给po的列属性赋值
				objs.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e, e.getCause());
			throw new RuntimeException(e);
		}finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.error(e, e.getCause());
				}
			}
		}		
		return objs;
	}
	
	private static CellStyle setCellDateStyle(Workbook wb) {
		//当excel日期格式使用自定义格式时，无法正常读取excel文件内容，故在内容读取前先设置excel单元格的格式为yyyy-MM-dd HH:mm:ss
		CreationHelper createHelper = wb.getCreationHelper();
		CellStyle dateStyle = wb.createCellStyle();
		dateStyle.setDataFormat(createHelper.createDataFormat().getFormat(ConvertUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
		return dateStyle;
	}
	
	/**
	 * 通过反射动态给po的列属性赋值
	 * @author sai.deng on 2012-10-13
	 * @param columnTemplates
	 * @param row
	 * @param obj
	 * @param dateStyle
	 */
	private static void setColumnPropertyValue(List<SheetColumnTemplate> columnTemplates, Row row, Object obj, CellStyle dateStyle) {
	
		Cell cell = null;
		Object cellContent = "";
		for (SheetColumnTemplate columnTemplate : columnTemplates) {
			try {
				int columnIndex = ExcelUtil.getColumnIndex(columnTemplate.getValueColumn()); //得到列索引值
				cell = row.getCell(columnIndex);
				
				String fieldName = columnTemplate.getOutputColumn();
				String setMethodName ="set"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
//				Field field = obj.getClass().getDeclaredField(fieldName);
				//先从当前类获取定义的字段，若没找到，递归从父类一直往上找，直到找到为止
				Field field = RefletHelper.getDeclaredField(obj.getClass(), fieldName);
				
//				Method method = obj.getClass().getMethod(setMethodName, field.getType());
				Method method = RefletHelper.setMethod(obj.getClass(), setMethodName, field);				
				String fieldTypeName = field.getType().getSimpleName();
				/////////////////////////////////////////////////////
				if(fieldTypeName.equals(Date.class.getSimpleName()) || fieldTypeName.equals(Timestamp.class.getSimpleName())) {
					cell.setCellStyle(dateStyle);
				}
				//注意：得到单元格的值必须在cell.setCellStyle(dateStyle)的后面
				cellContent = ExcelUtil.getCellContent(cell); 
				/////////////////////////////////////////////////////
				
				if(cellContent != null && cellContent.toString().length() > 0) {
					if(fieldTypeName.equals(String.class.getSimpleName())) {
						/**
						 * 当导入excel设置为文本类型时，若输入为：023时，调用DecimalFormatUtil.format6后，会变为:23
						 * 修正后：不进行小数倍数处理
						 */
						if(cellContent.toString().indexOf(".") != -1) {
							String newValue = DecimalFormatUtil.format6(cellContent.toString().trim());
							method.invoke(obj, new Object[] { newValue });
						} else {
							method.invoke(obj, new Object[] { cellContent.toString().trim() });
						}
					}else if(fieldTypeName.equals(Integer.class.getSimpleName())) {
						//Integer newValue = Integer.valueOf(cellContent.toString().trim());
						Integer newValue = Integer.valueOf(DecimalFormatUtil.format(cellContent.toString().trim()));
						method.invoke(obj, new Object[]{newValue});
					}else if(fieldTypeName.equals(Float.class.getSimpleName())) {
						Float newValue = Float.valueOf(cellContent.toString().trim());
						method.invoke(obj, new Object[]{newValue});
					}else if(fieldTypeName.equals(Double.class.getSimpleName())) {
						Double newValue = Double.parseDouble(cellContent.toString().trim()); 
						method.invoke(obj, new Object[]{newValue});
					}else if(fieldTypeName.equals(Date.class.getSimpleName())) {
						if(cellContent instanceof Date) {
							Date newDateValue = (Date) cellContent;
							method.invoke(obj, new Object[]{newDateValue});
						}else if(cellContent instanceof String) {    
							Timestamp newValue = null;
							if(cellContent.toString().length() > 10) {
								newValue = ConvertUtil.toTimestamp(cellContent.toString().trim(), ConvertUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
							}else { 
								newValue = ConvertUtil.toTimestamp(cellContent.toString().trim());
							} 
							method.invoke(obj, new Object[]{newValue});
						}
					}else if(fieldTypeName.equals(Timestamp.class.getSimpleName())) {
						Timestamp newValue = null;
						if(cellContent instanceof Date) {
							Date newDateValue = (Date) cellContent;
							newValue = new Timestamp(newDateValue.getTime());
						}else if(cellContent instanceof Timestamp) {    
							newValue = (Timestamp) cellContent;     
						}else if(cellContent instanceof String) {    
							if(cellContent.toString() .length() > 10) {
								newValue = ConvertUtil.toTimestamp(cellContent.toString().trim(), ConvertUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
							}else { 
								newValue = ConvertUtil.toTimestamp(cellContent.toString().trim());
							}
						}else {
							newValue = Timestamp.valueOf(cellContent.toString().trim());   
						}
						method.invoke(obj, new Object[]{newValue});
					}else if(fieldTypeName.equals(Long.class.getSimpleName())) {
						Long newValue = Long.valueOf(cellContent.toString().trim());
						method.invoke(obj, new Object[]{newValue});
					}else if(fieldTypeName.equals(Byte.class.getSimpleName())) {
						Byte newValue = Byte.valueOf(cellContent.toString().trim());
						method.invoke(obj, new Object[]{newValue});
					}
				}
			} catch (Exception e) {
				if(ProjectConsts.DEBUG) {
					e.printStackTrace();
					log.error(e, e.getCause());
				}
			}
		}
	}
	
	/**
	 * 获取导入EXCEL标题名（第一行第一列）
	 * @param filaPath
	 * @param taskId
	 * @param excelServerFilePath
	 * @return
	 * @throws RuntimeException
	 */
	public static String readExcelNameByFielPath(String excelServerFilePath) 
			throws RuntimeException {
		String name = null;
		InputStream is = null;
		Workbook wb = null;
		try {
			File file = new File(excelServerFilePath);
			is = new FileInputStream(file);
			wb = WorkbookFactory.create(is);
			//默认取第一张工作表
			Sheet sheet = wb.getSheetAt(0);
			Row row = sheet.getRow(0); 
			Cell cell = row.getCell(0);
			name = cell.getStringCellValue().trim();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e, e.getCause());
			throw new RuntimeException(e);
		}finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.error(e, e.getCause());
				}
			}
		}		
		return name;
	}
	
}
