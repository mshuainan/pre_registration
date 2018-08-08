package com.elementspeed.system.newregister.service.printHelper;

import com.elementspeed.framework.common.util.PdfUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

/**
 * 打印到货地址
 */
public class DlvSitePrinter extends PdfPrinter {
	
	private String deliverySite;	//到货地址
	
	public DlvSitePrinter(String deliverySite) {
		this.deliverySite = deliverySite;
	}
	
	@Override
	protected void print(Document document, boolean isReturn) throws DocumentException {
		PdfUtil.addLineWithContent(document, PdfUtil.getNormalFont(10), "送货地址：" + deliverySite);
	}
}
