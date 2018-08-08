package com.elementspeed.system.newregister.service.printHelper;

import com.elementspeed.framework.common.util.PdfUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * pdf页脚
 */
public class PdfFooter extends PdfPageEventHelper {
	int totalPage = 0;
	
	public PdfFooter(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public void onEndPage(PdfWriter writer, Document document) {
		//在文档的每个页面中，必须定义一个Ractangle矩形，其中参数为art
		Rectangle rect = new Rectangle(36, 54, 559, 788);
        rect.setBorderColor(BaseColor.BLACK);
        writer.setBoxSize("art", rect);
		
		ColumnText.showTextAligned(writer.getDirectContent(),
				Element.ALIGN_CENTER,
				new Phrase("第 " + writer.getPageNumber() + "页   共" + totalPage + "页", PdfUtil.getNormalFont(8)),
				rect.getRight() - 35, rect.getBottom() - 18, 0);
	}
}
