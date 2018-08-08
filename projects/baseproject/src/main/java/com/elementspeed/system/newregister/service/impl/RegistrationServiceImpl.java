package com.elementspeed.system.newregister.service.impl;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elementspeed.common.datadictionary.DataDictionary.Status;
import com.elementspeed.framework.base.controller.ExecuteResult;
import com.elementspeed.framework.base.dao.PageResults;
import com.elementspeed.framework.base.service.BaseServiceImpl;
import com.elementspeed.framework.common.util.BeanUtil;
import com.elementspeed.framework.common.util.DateUtil;
import com.elementspeed.framework.common.util.PdfUtil;
import com.elementspeed.framework.common.util.StringUtil;
import com.elementspeed.mapper.mdata.entity.SysDomicile;
import com.elementspeed.mapper.system.dao.SysPreRegisterDao;
import com.elementspeed.mapper.system.entity.SysPreRegister;
import com.elementspeed.system.mdata.service.SysDomicileService;
import com.elementspeed.system.newregister.service.RegistrationService;
import com.elementspeed.system.newregister.service.printHelper.AllPrinter;
import com.elementspeed.system.newregister.service.printHelper.NoticePrinter;
import com.elementspeed.system.newregister.service.printHelper.PdfFooter;
import com.elementspeed.system.newregister.service.printHelper.RegPrinter;
import com.elementspeed.system.newregister.service.printHelper.TitlePrinter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @ClassName: RegistrationServiceImpl
 * @Description: TODO(报名注册Impl)
 * @author masn
 * @date 2018年8月3日 下午10:12:42
 */
@Service
public class RegistrationServiceImpl extends BaseServiceImpl implements RegistrationService {
	/** 报名单号前缀 */
	private String orderNoPrefix  = "NO.";
	@Resource
	private SysPreRegisterDao sysPreRegisterDao;
	@Resource
	private SysDomicileService sysDomicileService;
	
	@Override
	public ExecuteResult register(SysPreRegister sysPreRegister){
		if(sysPreRegister.getDomicileType() == Status.YES.getValue()){
			sysPreRegister.setPropertyOwner(null);
			sysPreRegister.setPropertyOwnerName(null);
			sysPreRegister.setPropertySignDate(null);
			sysPreRegister.setPropertyCode(null);
		}
		ExecuteResult result = new ExecuteResult(true,"注册成功！");
		try {
			System.out.println("------------------" + sysPreRegister + "----------------------");
			SysPreRegister oldStudent = sysPreRegisterDao.findByStuIdCard(sysPreRegister.getStudentIdentity());
			if(oldStudent != null){
				SysDomicile sysDomicile = sysDomicileService.findById(
						StringUtil.isEmpty(sysPreRegister.getDomicileId1()) ? sysPreRegister.getDomicileId2() :sysPreRegister.getDomicileId1());
				sysPreRegister.setDomicileId(sysDomicile.getId());
				sysPreRegister.setDomicileCode(sysDomicile.getDomicileCode());
				sysPreRegister.setDomicileName(sysDomicile.getDomicileName());
				BeanUtil.copyPropertyNotNull(sysPreRegister, oldStudent);
				if(sysPreRegister.getDomicileType() == Status.YES.getValue()){
					oldStudent.setPropertyOwner(null);
					oldStudent.setPropertyOwnerName(null);
					oldStudent.setPropertySignDate(null);
					oldStudent.setPropertyCode(null);
				}
				oldStudent.setLastModifyInfo();
				sysPreRegisterDao.update(oldStudent);
			} else {
				sysPreRegister.setCreateInfo();
				sysPreRegister.setPreOrderNo(orderNoPrefix + DateUtil.convertDateToString(DateUtil.getNow(), DateUtil.DATE_FORMAT_YMDHS));
				SysDomicile sysDomicile = sysDomicileService.findById(
						StringUtil.isEmpty(sysPreRegister.getDomicileId1()) ? sysPreRegister.getDomicileId2() :sysPreRegister.getDomicileId1());
				sysPreRegister.setDomicileId(sysDomicile.getId());
				sysPreRegister.setDomicileCode(sysDomicile.getDomicileCode());
				sysPreRegister.setDomicileName(sysDomicile.getDomicileName());
				sysPreRegisterDao.insert(sysPreRegister);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			result.setSuccess(false);
			result.setMessage("操作失败,请刷新页面重新填写或联系管理员！");
		}
		return result;
	}
	
	@Override
	public PageResults<SysPreRegister> query(SysPreRegister sysPreRegister, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageInfo<SysPreRegister> pageInfo = new PageInfo<SysPreRegister>(sysPreRegisterDao.query(sysPreRegister));
		return convertPageResult(pageInfo, SysPreRegister.class);
	}	
	
	@Override
	public SysPreRegister findById(String id) {
		return sysPreRegisterDao.findById(id);
	}
	
	@Override
	public SysPreRegister findByStuIdCard(String idCard) {
		return sysPreRegisterDao.findByStuIdCard(idCard);
	}
	
	@Override
	public List<SysPreRegister> exportRegisterInfo(List<String> registerIds) {
		List<SysPreRegister> sysPreRegisters = new ArrayList<SysPreRegister>();
		for(String userId : registerIds) { 
			SysPreRegister sysPreRegister = sysPreRegisterDao.findById(userId);
			sysPreRegisters.add(sysPreRegister);
		}
		return sysPreRegisters;
	}	
	
	@Override
	public List<SysPreRegister> exportByQuery(SysPreRegister sysPreRegister) {
		List<SysPreRegister> sysPreRegisters = sysPreRegisterDao.query(sysPreRegister);
		return sysPreRegisters;
	}
	
	@Override
	public void expRegPdf(SysPreRegister sysPreRegister, OutputStream os) throws Exception {
		Document document = PdfUtil.createDocument();
		try {
			PdfWriter writer = PdfWriter.getInstance(document, os);
			int totalPage = 1;
			PdfFooter footer = new PdfFooter(totalPage);
			writer.setPageEvent(footer);
			document.open();
			AllPrinter printer = new AllPrinter();
			//printer.addPrinter(new HeaderPrinter());	//页眉
			printer.addPrinter(new TitlePrinter());		//标题
			printer.addPrinter(new RegPrinter(sysPreRegister));
			printer.addPrinter(new NoticePrinter());
			//printer.addPrinter(new SignPrinter(sysPreRegister.getStudentIdentity()));
			printer.print(document, true);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException("导出文件失败！");
		}finally{
			document.close();
		}
	}
}