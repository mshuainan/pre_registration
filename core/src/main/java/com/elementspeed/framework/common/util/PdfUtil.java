package com.elementspeed.framework.common.util;

import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * pdf Util
 *
 */
public class PdfUtil {
	
	public final static int ROW_COUNT = 30;		//每页显示10行
	
	final static public Document createDocument() {
		return new Document(PageSize.A4);
	}
	
	final static public BaseFont getBaseFont() {
		try {
			//处理中文问题
			return BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	final static public Font getNormalFont(int fontSize) {
		return new Font(getBaseFont(), fontSize, Font.NORMAL);
	}
	
	/**
	 * 添加N个空白行
	 * @param document
	 * @param n
	 * @throws DocumentException
	 */
	final static public void addBlankLine(Document document, int n) throws DocumentException {
		for(int i = 0; i < n; i++) {
			document.add(new Paragraph("\n"));
		}
	}
	
	/**
	 * 添加一个空行
	 * @param document
	 * @throws DocumentException
	 */
	final static public void addBlankLine(Document document) throws DocumentException {
		document.add(new Paragraph("\n"));
	}
	
	/**
	 * 添加页眉
	 * @param document
	 * @param font 字体
	 * @param logo
	 * @param logAlign log位置
	 * @param content 文本
	 * @param conAlign 文本位置
	 */
	final static public void headerContent(Document document, Font font, Image logo, int logAlign, String content, int conAlign) throws DocumentException {
		//创建两个单元格
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		
		PdfPCell logCell = new PdfPCell(logo);
		logCell.setHorizontalAlignment(logAlign);
		PdfUtil.setBorderWidthBottom(logCell);
		table.addCell(logCell);
		
		Paragraph noticeTitle = new Paragraph(content, font);
		PdfPCell contCell = new PdfPCell(noticeTitle);
		contCell.setHorizontalAlignment(conAlign);
		PdfUtil.setBorderWidthBottom(contCell);
		table.addCell(contCell);
		
		document.add(table);
	}
	
	/**
	 * 添加一行带下划线的信息
	 * @param document
	 * @param font
	 * @param content
	 * @throws DocumentException 
	 */
	final static public void addLineWithContent(Document document, Font font, String content) throws DocumentException {
		Paragraph noticeTitle = new Paragraph(content, font);
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		PdfPCell cell = new PdfPCell(noticeTitle);
		PdfUtil.setBorderWidthBottom(cell);
		table.addCell(cell);
		document.add(table);
	}
	
	/**
	 * 设置单元格上边框
	 * @param cell
	 */
	final static public void setCellBorderTop(PdfPCell cell) {
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setBorderWidthTop(0.5f);
	}
	
	/**
	 * 设置单元格下边框
	 * @param cell
	 */
	final static public void setBorderWidthBottom(PdfPCell cell) {
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setBorderWidthBottom(0.5f);
	}
	
	/**
	 * 设置单元格下边框
	 * @param cell
	 */
	final static public void setCellCellStyle(PdfPCell cell, Integer align) {
		cell.setPaddingTop(4f);
		cell.setPaddingBottom(4f);
		cell.setHorizontalAlignment(align==null?Element.ALIGN_CENTER:align);
		cell.setBorderWidth(0.5f);
	}
	
	/**
	 * 设置单元格边框
	 * @param cell
	 */
	final static public void setCellNoBorder(PdfPCell cell) {
		cell.setBorder(PdfPCell.NO_BORDER);
	}
	
	/**
	 * 设置单元格右边框
	 * @param cell
	 */
	final static public void setBorderWidthRight(PdfPCell cell) {
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setBorderWidthRight(0.5f);
	}
	
	/**
	 * 设置单元格左右边框
	 * @param cell
	 */
	final static public void setBorderWidthRightAndLeft(PdfPCell cell) {
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setBorderWidthRight(0.5f);
		cell.setBorderWidthLeft(0.5f);
	}
	
	/**
	 * 设置单元格右下左边框
	 * @param cell
	 */
	final static public void setBorderWidthRBL(PdfPCell cell) {
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setBorderWidthRight(0.5f);
	    cell.setBorderWidthBottom(0.5f);
		cell.setBorderWidthLeft(0.5f);
	}
	
	/**
	 * 设置单元格右下边框
	 * @param cell
	 */
	final static public void setBorderWidthRB(PdfPCell cell) {
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setBorderWidthRight(0.5f);
		cell.setBorderWidthBottom(0.5f);
	}
	
}
