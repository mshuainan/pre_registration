package com.elementspeed.system.newregister.service.printHelper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.elementspeed.framework.common.util.DateUtil;
import com.elementspeed.framework.common.util.PdfUtil;
import com.elementspeed.framework.common.util.StringUtil;
import com.elementspeed.mapper.system.entity.SysPreRegister;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * @ClassName: RegPrinter
 * @Description: TODO(打印报名信息)
 * @author masn
 * @date 2018年8月7日 上午9:56:21
 */
public class RegPrinter extends PdfPrinter {
	
	private SysPreRegister preRegister;	//待打印的数据, list中的类型是订单明细或子组件
	
	public RegPrinter(SysPreRegister sysPreRegister) {
		this.preRegister = sysPreRegister;
	}
	
	@Override
	protected void print(Document document, boolean isReturn) throws DocumentException, MalformedURLException, IOException {
		Font font = PdfUtil.getNormalFont(9);
		createContent(document, font);
		//在表格上添加一个空行
		PdfUtil.addBlankLine(document);
		//document.add(new Paragraph("\b", font2));
	}
	
	/**
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @Title: createContent
	 * @Description: TODO(创建内容)
	 * @author masn
	 * @date 2018年8月7日 上午10:38:04
	 * @return void  返回类型
	 * @throws
	 */
	private void createContent(Document document, Font font) throws DocumentException, MalformedURLException, IOException{
		/** 创建一个有8列的表格 */
		float[] widths1 = {0.14f, 0.1f, 0.14f, 0.12f,0.12f,0.2f,0.10f,0.10f};
		PdfPTable table = new PdfPTable(widths1);
		// 设置表格大小为可用空白区域的100%
		table.setWidthPercentage(100);
		// 表格靠左对齐
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		/** 表格第一行 */
		PdfPCell cell1_1 = new PdfPCell(new Paragraph("报名号：", font));
		cell1_1.setFixedHeight(45);
		cell1_1.setVerticalAlignment(Element.ALIGN_MIDDLE); 
		PdfPCell cell1_2 = new PdfPCell(new Paragraph(preRegister.getPreOrderNo(), font));
		cell1_2.setVerticalAlignment(Element.ALIGN_MIDDLE); 
		cell1_2.setColspan(3);// 设置表格为二列
		PdfPCell cell1_3 = new PdfPCell(new Paragraph("姓名:", font));
		cell1_3.setVerticalAlignment(Element.ALIGN_MIDDLE); 
		PdfPCell cell1_4 = new PdfPCell(new Paragraph(preRegister.getStudentName(), font));
		cell1_4.setVerticalAlignment(Element.ALIGN_MIDDLE); 
		// 读取图片(参数为gif、jpg、png格式的图片都可以)，设置图片大小
        String path = new File(RegPrinter.class.getResource("/").getFile()).getAbsolutePath();
        if(StringUtil.isEmpty(preRegister.getStudentFaceUrl())){
            path = StringUtil.removeLast(path, "classes".length()) + "../static/img/xxs.jpg";
        } else {
        	path = preRegister.getStudentFaceUrl();
        }
		Image logo = Image.getInstance(path);
        // 设置图片的绝对大小，宽和高
		logo.scaleAbsolute(95f, 100f);
        // 设置图片居中显示
		logo.setAlignment(Image.MIDDLE);
        // 创建单元格,并且将单元格内容设置为图片
        PdfPCell imageCell = new PdfPCell(logo);
        imageCell.setColspan(2);// 设置表格为二列
        imageCell.setRowspan(2);// 设置表格为三行
        imageCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfUtil.setCellCellStyle(cell1_1,null);
		PdfUtil.setCellCellStyle(cell1_2,null);
		PdfUtil.setCellCellStyle(cell1_3,null);
		PdfUtil.setCellCellStyle(cell1_4,null);
		PdfUtil.setCellCellStyle(imageCell,null);
		PdfUtil.setCellCellStyle(imageCell,null);
		table.addCell(cell1_1);
		table.addCell(cell1_2);
		table.addCell(cell1_3);
		table.addCell(cell1_4);
		table.addCell(imageCell);
		
		/** 表格第二行 */
		PdfPCell cell2_1 = new PdfPCell(new Paragraph("性别:", font));
		cell2_1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell2_1.setFixedHeight(45);
		PdfPCell cell2_2 = new PdfPCell(new Paragraph(preRegister.getStudentGenderStr(), font));
		cell2_2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell cell2_3 = new PdfPCell(new Paragraph("出生日期:", font));
		cell2_3.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell cell2_4 = new PdfPCell(new Paragraph(DateUtil.convertDateToString(preRegister.getBirthDate(),DateUtil.DATE_FORMAT), font));
		cell2_4.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell cell2_5 = new PdfPCell(new Paragraph("身份证:", font));
		cell2_5.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell cell2_6 = new PdfPCell(new Paragraph(preRegister.getStudentIdentity(), font));
		cell2_6.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		PdfUtil.setCellCellStyle(cell2_1,null);
		PdfUtil.setCellCellStyle(cell2_2,null);
		PdfUtil.setCellCellStyle(cell2_3,null);
		PdfUtil.setCellCellStyle(cell2_4,null);
		PdfUtil.setCellCellStyle(cell2_5,null);
		PdfUtil.setCellCellStyle(cell2_6,null);
		table.addCell(cell2_1);
		table.addCell(cell2_2);
		table.addCell(cell2_3);
		table.addCell(cell2_4);
		table.addCell(cell2_5);
		table.addCell(cell2_6);
		
		/** 表格第三行 */
		PdfPCell cell3_1 = new PdfPCell(new Paragraph("父亲姓名:", font));
		cell3_1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell3_1.setFixedHeight(45);
		PdfPCell cell3_2 = new PdfPCell(new Paragraph(preRegister.getFatherName(), font));
		cell3_2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell cell3_3 = new PdfPCell(new Paragraph("联系方式:", font));
		cell3_3.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell cell3_4 = new PdfPCell(new Paragraph(preRegister.getFatherContactInfo(), font));
		cell3_4.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell3_4.setColspan(2);
		PdfPCell cell3_5 = new PdfPCell(new Paragraph("身份证号码:", font));
		cell3_5.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell cell3_6 = new PdfPCell(new Paragraph(preRegister.getFatherIdentity(), font));
		cell3_6.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell3_6.setColspan(2);	
		
		PdfUtil.setCellCellStyle(cell3_1,null);
		PdfUtil.setCellCellStyle(cell3_2,null);
		PdfUtil.setCellCellStyle(cell3_3,null);
		PdfUtil.setCellCellStyle(cell3_4,null);
		PdfUtil.setCellCellStyle(cell3_5,null);
		PdfUtil.setCellCellStyle(cell3_6,null);
		table.addCell(cell3_1);
		table.addCell(cell3_2);
		table.addCell(cell3_3);
		table.addCell(cell3_4);
		table.addCell(cell3_5);
		table.addCell(cell3_6);

		/** 表格第四行 */
		PdfPCell cell4_1 = new PdfPCell(new Paragraph("母亲姓名:", font));
		cell4_1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell4_1.setFixedHeight(45);
		PdfPCell cell4_2 = new PdfPCell(new Paragraph(preRegister.getFatherName(), font));
		cell4_2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell cell4_3 = new PdfPCell(new Paragraph("联系方式:", font));
		cell4_3.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell cell4_4 = new PdfPCell(new Paragraph(preRegister.getFatherContactInfo(), font));
		cell4_4.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell4_4.setColspan(2);
		PdfPCell cell4_5 = new PdfPCell(new Paragraph("身份证号码:", font));
		cell4_5.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell cell4_6 = new PdfPCell(new Paragraph(preRegister.getFatherIdentity(), font));
		cell4_6.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell4_6.setColspan(2);
		
		PdfUtil.setCellCellStyle(cell4_1,null);
		PdfUtil.setCellCellStyle(cell4_2,null);
		PdfUtil.setCellCellStyle(cell4_3,null);
		PdfUtil.setCellCellStyle(cell4_4,null);
		PdfUtil.setCellCellStyle(cell4_5,null);
		PdfUtil.setCellCellStyle(cell4_6,null);
		table.addCell(cell4_1);
		table.addCell(cell4_2);
		table.addCell(cell4_3);
		table.addCell(cell4_4);
		table.addCell(cell4_5);
		table.addCell(cell4_6);
		
		/** 表格第五行 */
		PdfPCell cell5_1 = new PdfPCell(new Paragraph("区域:", font));
		cell5_1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell5_1.setFixedHeight(45);
		PdfPCell cell5_2 = new PdfPCell(new Paragraph(preRegister.getDistrictName(), font));
		cell5_2.setColspan(2);
		cell5_2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell cell5_3 = new PdfPCell(new Paragraph("学校:", font));
		cell5_3.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell cell5_4 = new PdfPCell(new Paragraph(preRegister.getSchoolName(), font));
		cell5_4.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell5_4.setColspan(4);
		
		PdfUtil.setCellCellStyle(cell5_1,null);
		PdfUtil.setCellCellStyle(cell5_2,null);
		PdfUtil.setCellCellStyle(cell5_3,null);
		PdfUtil.setCellCellStyle(cell5_4,null);
		table.addCell(cell5_1);
		table.addCell(cell5_2);
		table.addCell(cell5_3);
		table.addCell(cell5_4);
		
		/** 表格第六行 */
		PdfPCell cell6_1 = new PdfPCell(new Paragraph("居住地址:", font));
		cell6_1.setFixedHeight(45);
		cell6_1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell cell6_2 = new PdfPCell(new Paragraph(preRegister.getDomicileAddress(), font));
		cell6_2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell6_2.setColspan(4);
		PdfPCell cell6_3 = new PdfPCell(new Paragraph("房产人称谓:", font));
		cell6_3.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell cell6_4 = new PdfPCell(new Paragraph(preRegister.getPropertyOwnerStr(), font));
		cell6_4.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell6_4.setColspan(2);
		PdfUtil.setCellCellStyle(cell6_1,null);
		PdfUtil.setCellCellStyle(cell6_2,null);
		PdfUtil.setCellCellStyle(cell6_3,null);
		PdfUtil.setCellCellStyle(cell6_4,null);
		table.addCell(cell6_1);
		table.addCell(cell6_2);
		table.addCell(cell6_3);
		table.addCell(cell6_4);
		
		/** 表格第七行 */
		PdfPCell cell7_5 = new PdfPCell(new Paragraph("房产人姓名:", font));
		cell7_5.setFixedHeight(45);
		cell7_5.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell cell7_6 = new PdfPCell(new Paragraph(preRegister.getPropertyOwnerName(), font));
		cell7_6.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		PdfPCell cell7_1 = new PdfPCell(new Paragraph("签发日期:", font));
		cell7_1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		PdfPCell cell7_2 = new PdfPCell(new Paragraph(DateUtil.convertDateToString(preRegister.getPropertySignDate(),DateUtil.DATE_FORMAT), font));
		cell7_2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell7_2.setColspan(2);
		
		PdfPCell cell7_3 = new PdfPCell(new Paragraph("房产号:", font));
		cell7_3.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		PdfPCell cell7_4 = new PdfPCell(new Paragraph(preRegister.getPropertyCode(), font));
		cell7_4.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell7_4.setColspan(2);
		
		PdfUtil.setCellCellStyle(cell7_1,null);
		PdfUtil.setCellCellStyle(cell7_2,null);
		PdfUtil.setCellCellStyle(cell7_3,null);
		PdfUtil.setCellCellStyle(cell7_4,null);
		PdfUtil.setCellCellStyle(cell7_5,null);
		PdfUtil.setCellCellStyle(cell7_6,null);
		table.addCell(cell7_5);
		table.addCell(cell7_6);
		table.addCell(cell7_1);
		table.addCell(cell7_2);
		table.addCell(cell7_3);
		table.addCell(cell7_4);
		
		/** 表格第八行 */
		PdfPCell cell8_1 = new PdfPCell(new Paragraph("学校意见:", font));
		cell8_1.setFixedHeight(100);
		cell8_1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		PdfPCell cell8_2 = new PdfPCell(new Paragraph("接待人（签名）                                                            年                         月           日", font));
		cell8_2.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell8_2.setColspan(7);
		
		PdfUtil.setCellCellStyle(cell8_1,null);
		PdfUtil.setCellCellStyle(cell8_2,null);
		table.addCell(cell8_1);
		table.addCell(cell8_2);
		document.add(table);
	}
}