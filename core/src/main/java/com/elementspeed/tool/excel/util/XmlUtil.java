package com.elementspeed.tool.excel.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.elementspeed.framework.common.util.SpringContextUtil;
import com.elementspeed.framework.common.util.StringUtil;
import com.elementspeed.tool.excel.common.ProjectConsts;
import com.elementspeed.tool.excel.common.SheetColumnTemplate;
import com.elementspeed.tool.excel.common.SheetInfo;

public class XmlUtil {
	private static final String DEFAULT_COLUMN_WIDTH = "18";

	/**
	 * 根据bindId读取excel/ExcelConfig.xml获取bindid所对应的路径
	 * @param bindId
	 * @return
	 * @throws IOException 
	 */
	public static String readExcelConfigXml(String bindId){
		PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
		String path = "";
		try{
		for(String propPath : ProjectConsts.EXCEL_CONFIG_PATH){
			Resource[] resources = resourceLoader.getResources(propPath);
			for(Resource resource : resources){
				
				DocumentBuilder db = null;
				Document document  = null;
				InputStream is = null;
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				db = dbf.newDocumentBuilder();
				is = resource.getInputStream();
				document  = db.parse(is);	
				Element rootElement = document.getDocumentElement();   
				NodeList importTemplates = rootElement.getElementsByTagName("importTemplate");  
				int length = importTemplates.getLength();
				for(int i=0;i<length;i++) {
					Element importTemplateElement = (Element) importTemplates.item(i);  
					String id = importTemplateElement.getAttribute("bindId");
					if(id == null || id.equals("")) {
						path = null;
						break;
					}
					if(id.equals(bindId)) {
						path = importTemplateElement.getAttribute("path");
						break;
					}
				}
				if(StringUtil.isNotEmpty(path)){
					break;
				}
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(StringUtil.isNotEmpty(path)){
				break;
			}
		}
		}catch(Exception e){
			
		}
//		String path = "";
//		DocumentBuilder db = null;
//		Document document  = null;
//		InputStream is = null;
//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		try {
//			db = dbf.newDocumentBuilder();
//			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(ProjectConsts.EXCEL_CONFIG_PATH);
//			document  = db.parse(is);			
//		} catch (ParserConfigurationException e1) {
//			e1.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Element rootElement = document.getDocumentElement();   
//		NodeList importTemplates = rootElement.getElementsByTagName("importTemplate");  
//		int length = importTemplates.getLength();
//		for(int i=0;i<length;i++) {
//			Element importTemplateElement = (Element) importTemplates.item(i);  
//			String id = importTemplateElement.getAttribute("bindId");
//			if(id == null || id.equals("")) {
//				path = null;
//				break;
//			}
//			if(id.equals(bindId)) {
//				path = importTemplateElement.getAttribute("path");
//				break;
//			}
//		}
//		
//		try {
//			is.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return path;
	}
	
	/**
	 * 读取path所对应的taskId的导入导出模板
	 * @param path
	 * @param taskId
	 * @return
	 */
	public static SheetInfo readExcelImportExportTemplateXml(String path, String taskId) {
		SheetInfo sheetInfo = new SheetInfo();
		DocumentBuilder db = null;
		Document document  = null;
		InputStream is = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
			db.setEntityResolver(new IgnoreDTDEntityResolver()); //忽略DTD验证
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			document  = db.parse(is);			
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element rootElement = document.getDocumentElement();   
		NodeList dataTransfers = rootElement.getElementsByTagName("DataTransfer");  
		int dataTransfersLength = dataTransfers.getLength();
		for(int i=0;i<dataTransfersLength;i++) {
			Element dataTransferElement = (Element) dataTransfers.item(i);  
			String xmlTaskId = dataTransferElement.getAttribute("taskId");
			if(xmlTaskId.equals(taskId)) {
				String sheet = dataTransferElement.getAttribute("sheet");
				if(sheet == null || sheet.equals("")) { sheet = "0"; }
				sheetInfo.setSheetIndex(Integer.parseInt(sheet));
				String startRow = dataTransferElement.getAttribute("startRow");  //导入模板使用
				if(startRow == null || startRow.equals("")) { startRow = "1"; }
				sheetInfo.setStartRow(Integer.parseInt(startRow));
				String endRow = dataTransferElement.getAttribute("endRow"); //导入模板使用
				if(endRow == null || endRow.equals("")) { endRow = "65535"; }
				sheetInfo.setEndRow(Integer.parseInt(endRow));
				sheetInfo.setClazz(dataTransferElement.getAttribute("clazz")); //导入模板使用
				
				String startCol = dataTransferElement.getAttribute("startCol"); //导出模板使用
				if(startCol == null || startCol.equals("")) { startCol = "A"; }
				sheetInfo.setStartCol(ExcelUtil.getColumnIndex(startCol==null?"":startCol.trim())); 
				
				List<SheetColumnTemplate> columnTemplates = new ArrayList<SheetColumnTemplate>();
				SheetColumnTemplate columnTemplate = null;
				NodeList columns = dataTransferElement.getElementsByTagName("column");
				int columnLength = columns.getLength();
				for(int col=0;col<columnLength;col++) {
					Element columnElement = (Element)columns.item(col);
					columnTemplate = new SheetColumnTemplate();
					columnTemplate.setOutputColumn(columnElement.getAttribute("outputColumn"));//对应PO的属性
					columnTemplate.setValueColumn(columnElement.getAttribute("valueColumn"));//对应Excel的哪一列 导入模板使用
					
					columnTemplate.setColumnCaption(SpringContextUtil.getMessage(columnElement.getAttribute("columnCaption"))); 
					if(columnElement.getAttribute("columnWidth") != null && !columnElement.getAttribute("columnWidth").equals("")) {
						columnTemplate.setColumnWidth(columnElement.getAttribute("columnWidth")); //导出模板使用
					}else {
						columnTemplate.setColumnWidth(DEFAULT_COLUMN_WIDTH);
					}
					if(columnElement.getAttribute("type") == null || columnElement.getAttribute("type").equals("")) {
						columnTemplate.setType("String");
					}else {
						columnTemplate.setType(columnElement.getAttribute("type")); //导出模板使用
					}
					columnTemplate.setFormat(columnElement.getAttribute("format")); //导出模板使用
					columnTemplates.add(columnTemplate);
				}
				sheetInfo.setColumnTemplates(columnTemplates);
				break;
			}
		}
		
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sheetInfo;
	}
	
	public static void main(String[] args) throws IOException {
		readExcelConfigXml("");
		
		
		
		
	}
	
}

class IgnoreDTDEntityResolver implements EntityResolver {
    @Override  
    public InputSource resolveEntity(String arg0, String arg1) throws SAXException, IOException {  
         return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes()));   
    }  
}  
