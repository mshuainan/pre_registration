package com.elementspeed.system.newregister.service.printHelper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.elementspeed.framework.common.util.PdfUtil;
import com.elementspeed.framework.common.util.StringUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;

/**
 * @ClassName: HeaderPrinter
 * @Description: TODO(打印页眉)
 * @author masn
 * @date 2018年8月7日 上午9:41:37
 */
public class HeaderPrinter extends PdfPrinter {
	
	@Override
	protected void print(Document document, boolean isReturn) throws DocumentException {
		try {
			String path = new File(HeaderPrinter.class.getResource("/").getFile()).getAbsolutePath();
			path = StringUtil.removeLast(path, "classes".length()) + "../static/img/yongyao.jpg";
			Image logo = Image.getInstance(path);
			//控制图片大小
			logo.scalePercent(24.5f);
			PdfUtil.headerContent(document, PdfUtil.getNormalFont(6), logo, Element.ALIGN_LEFT, NoticeContent.getHeader(), Element.ALIGN_RIGHT);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
