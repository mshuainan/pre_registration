package com.elementspeed.system.newregister.service.printHelper;


import com.elementspeed.framework.common.util.DateUtil;
import com.elementspeed.framework.common.util.PdfUtil;
import com.elementspeed.framework.common.util.StringUtil;
import com.elementspeed.mapper.system.entity.SysPreRegister;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * 打印采购列表表头
 */
public class RegPrinter1 extends PdfPrinter {

	private SysPreRegister sysPreRegister;
	
	public RegPrinter1(SysPreRegister sysPreRegister) {
		this.sysPreRegister = sysPreRegister;
	}

	@Override
	protected void print(Document document, boolean isReturn) throws DocumentException {
		// 创建一个有9列的表格
		float[] widths = {0.24f, 0.34f, 0.22f, 0.2f};
		// 创建一个有2列的表格
		PdfPTable table = new PdfPTable(widths);
		// 设置表格大小为可用空白区域的100%
		table.setWidthPercentage(100);
		// 表格靠左对齐
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		Font font = PdfUtil.getNormalFont(10);
		PdfPCell cell1_1 = new PdfPCell(new Paragraph("采购单号：" + sysPreRegister.getStudentName(), font));
		PdfPCell cell1_2 = new PdfPCell(new Paragraph("厂家名称：" + sysPreRegister.getStudentName(), font));
		PdfPCell cell2_1 = new PdfPCell(new Paragraph("供应商编码：" + sysPreRegister.getStudentName(), font));
		PdfPCell cell2_2 = new PdfPCell(new Paragraph("编号：", font));
		//供应商信息
		PdfPCell cell3_1 = new PdfPCell(new Paragraph("联络人：" + StringUtil.decode(sysPreRegister.getStudentName()), font));
		PdfPCell cell3_2 = new PdfPCell(new Paragraph("电话：" + StringUtil.decode(sysPreRegister.getStudentName()), font));
		PdfPCell cell4_1 = new PdfPCell(new Paragraph("传真：" + StringUtil.decode(sysPreRegister.getStudentName()), font));
		PdfPCell cell4_2 = new PdfPCell(new Paragraph("采购日期：" + DateUtil.convertDateToString(sysPreRegister.getBirthDate()), font));
		PdfUtil.setCellNoBorder(cell1_1);
		PdfUtil.setCellNoBorder(cell1_2);
		PdfUtil.setCellNoBorder(cell2_1);		
		PdfUtil.setCellNoBorder(cell2_2);
		PdfUtil.setCellNoBorder(cell3_1);
		PdfUtil.setCellNoBorder(cell3_2);
		PdfUtil.setCellNoBorder(cell4_1);
		PdfUtil.setCellNoBorder(cell4_2);
		table.addCell(cell1_1);
		table.addCell(cell1_2);
		table.addCell(cell2_1);
		table.addCell(cell2_2);
		table.addCell(cell3_1);
		table.addCell(cell3_2);
		table.addCell(cell4_1);
		table.addCell(cell4_2);
		
		document.add(table);
	}
}
