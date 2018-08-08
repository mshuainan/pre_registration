package com.elementspeed.system.newregister.service.printHelper;

import com.elementspeed.framework.common.util.DateUtil;
import com.elementspeed.framework.common.util.PdfUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;

/**
 * @ClassName: TitlePrinter
 * @Description: TODO(打印title)
 * @author masn
 * @date 2018年8月7日 下午1:12:00
 */
public class TitlePrinter extends PdfPrinter {
	
	@Override
	protected void print(Document document, boolean isReturn) throws DocumentException {
		String titleStr = "淮上实验小学" + DateUtil.getCurrentYear() + "年秋季招生新生报名表";
		Paragraph title = new Paragraph(titleStr, new Font(PdfUtil.getBaseFont(), 15, Font.BOLD));
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);
		PdfUtil.addBlankLine(document, 2);
	}
}
