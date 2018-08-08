package com.elementspeed.system.newregister.service.printHelper;

import com.elementspeed.framework.common.util.PdfUtil;
import com.elementspeed.framework.common.util.StringUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * 打印签字区域
 */
public class SignPrinter extends PdfPrinter {
	
	private String fax;
	
	public SignPrinter(String fax){
		this.fax = fax;
	}

	@Override
	protected void print(Document document, boolean isReturn) throws DocumentException {
		// 创建一个有3列的表格
		PdfPTable table = new PdfPTable(3);
		// 设置表格大小为可用空白区域的100%
		table.setWidthPercentage(100);
		Font font = PdfUtil.getNormalFont(10);
		
		PdfPCell cell1 = new PdfPCell(new Paragraph("采购员：", font));
		cell1.setTop(5);
		PdfUtil.setCellNoBorder(cell1);
		table.addCell(cell1);
		
		PdfPCell cell2 = new PdfPCell(new Paragraph("审批人：", font));
		cell2.setTop(5);
		PdfUtil.setCellNoBorder(cell2);
		table.addCell(cell2);
		
		PdfPCell cell3 = new PdfPCell(new Paragraph("供应商签收：", font));
		cell3.setTop(5);
		PdfUtil.setCellNoBorder(cell3);
		table.addCell(cell3);
		
		PdfUtil.addBlankLine(document);
		document.add(table);
		
		document.add(new Paragraph(" 传真："+StringUtil.decode(fax), font));
	}
}
