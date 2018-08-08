package com.elementspeed.system.newregister.service.printHelper;

import com.elementspeed.framework.common.util.PdfUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;

/**
 * @ClassName: NoticePrinter
 * @Description: TODO(打印注意事项)
 * @author masn
 * @date 2018年8月7日 下午1:10:02
 */
public class NoticePrinter extends PdfPrinter {
	
	@Override
	protected void print(Document document, boolean isReturn) throws DocumentException {
		Font font = PdfUtil.getNormalFont(8);
		Paragraph p1 = new Paragraph(NoticeContent.getNotice1(), font);
		Paragraph p2 = new Paragraph(NoticeContent.getNotice2(), font);
		Paragraph p3 = new Paragraph(NoticeContent.getNotice3(), font);
		Paragraph p4 = new Paragraph(NoticeContent.getNotice4(), font);
		
		PdfUtil.addLineWithContent(document, font, NoticeContent.getNoticeTitle());
		document.add(p1);
		document.add(p2);
		document.add(p3);
		document.add(p4);
	}
}
