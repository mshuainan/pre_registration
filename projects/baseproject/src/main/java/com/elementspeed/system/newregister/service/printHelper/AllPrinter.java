package com.elementspeed.system.newregister.service.printHelper;

import java.util.LinkedList;
import java.util.List;

import com.itextpdf.text.Document;

/**
 * @ClassName: AllPrinter
 * @Description: TODO(pdf打印容器类)
 * @author masn
 * @date 2018年8月7日 上午9:41:14
 */
public class AllPrinter {

	List<PdfPrinter> printList = new LinkedList<PdfPrinter>();
	
	public void addPrinter(PdfPrinter printer) {
		printList.add(printer);
	}
	
	public void print(Document document, boolean isReturn) throws Exception {
		for (PdfPrinter pdfPrinter : printList) {
			pdfPrinter.print(document, isReturn);
		}
	}
}
