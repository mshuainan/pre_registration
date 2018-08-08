package com.elementspeed.system.newregister.service.printHelper;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

/**
 * 打印PDF
 */
public abstract class PdfPrinter {
	
	/**
	 * 
	 * @param document
	 * @param isReturn 	是否退货单
	 * @throws DocumentException
	 */
	abstract protected void print(Document document, boolean isReturn) throws Exception;
}
