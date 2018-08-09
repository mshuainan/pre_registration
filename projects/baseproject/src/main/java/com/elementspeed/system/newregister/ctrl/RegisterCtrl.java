package com.elementspeed.system.newregister.ctrl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elementspeed.common.excel.ExcelExportTools;
import com.elementspeed.framework.base.controller.BaseCtrl;
import com.elementspeed.framework.base.controller.ExecuteResult;
import com.elementspeed.framework.base.dao.PageResults;
import com.elementspeed.framework.base.exception.BOException;
import com.elementspeed.framework.common.SRMFile;
import com.elementspeed.framework.common.util.ContainerUtil;
import com.elementspeed.mapper.system.entity.SysPreRegister;
import com.elementspeed.system.newregister.service.RegistrationService;
import com.elementspeed.tool.excel.writer.ExcelParam;
import com.elementspeed.tool.excel.writer.ExcelWriter;

/**
 * @ClassName: RegisterCtrl
 * @Description: TODO(报名注册Ctrl)
 * @author masn
 * @date 2018年8月3日 下午10:13:05
 */
@RequestMapping("portalroot/newRegister")
@Controller
public class RegisterCtrl extends BaseCtrl {
	
	private static Logger logger = LoggerFactory.getLogger(RegisterCtrl.class);
	
	@Resource
	RegistrationService registrationService;
	
	/**
	 * @Title: toRegister
	 * @Description: TODO(进入报名页面)
	 * @author masn
	 * @date 2018年8月3日 下午10:13:18
	 * @return String  返回类型
	 * @throws
	 */
	@RequestMapping("/toRegister")
	public String index(@ModelAttribute("sysPreRegister") SysPreRegister sysPreRegister,Model model,HttpServletRequest request) {	
		return "/system/register";
	}
	
	/**
	 * @Title: register
	 * @Description: TODO(保存报名)
	 * @author masn
	 * @date 2018年8月3日 下午10:13:35
	 * @return ExecuteResult  返回类型
	 * @throws
	 */
	@RequestMapping("/register")
	@ResponseBody
	public ExecuteResult register(@ModelAttribute("registerInfo") SysPreRegister sysPreRegister) {
		/** 学生头像上传 */
		if(sysPreRegister.getStudentFace() != null && sysPreRegister.getStudentFace().getSize() > 0) {
			try {
				String reg = "(?i).+?\\.(jpg|jpeg|png|JPG|JPEG|PNG)";
				if(!sysPreRegister.getStudentFace().getOriginalFilename().matches(reg)) {
					throw new RuntimeException("附件上传失败, 只支持以下格式附件：jpeg、png、jpg");
				}
				SRMFile studentFace = registrationService.saveFile(sysPreRegister.getStudentFace().getInputStream(), sysPreRegister.getStudentFace().getOriginalFilename());
				sysPreRegister.setStudentFaceName(studentFace.getOriginalFilename());
				sysPreRegister.setStudentFaceUrl(studentFace.getPath());
			} catch (Exception e) {
				e.printStackTrace();
				return new ExecuteResult(false, e.getMessage());
			}
		}/* else {
			return new ExecuteResult(false, "请上传学生头像！");
		}*/
		/** 户口本&购房合同上传 */
		if(sysPreRegister.getPropertyFile() != null && sysPreRegister.getPropertyFile().getSize() > 0) {
			try {
				String reg = "(?i).+?\\.(jpg|jpeg|png|JPG|JPEG|PNG)";
				if(!sysPreRegister.getPropertyFile().getOriginalFilename().matches(reg)) {
					throw new RuntimeException("附件上传失败, 只支持以下格式附件：jpeg、png、jpg");
				}
				SRMFile propertyFile = registrationService.saveFile(sysPreRegister.getPropertyFile().getInputStream(), sysPreRegister.getPropertyFile().getOriginalFilename());
				sysPreRegister.setPropertyFileName(propertyFile.getOriginalFilename());
				sysPreRegister.setPropertyFileUrl(propertyFile.getPath());
			} catch (Exception e) {
				e.printStackTrace();
				return new ExecuteResult(false, e.getMessage());
			}
		}
		return registrationService.register(sysPreRegister);
	}
	
	/**
	 * @Title: index
	 * @Description: TODO(进入报名列表)
	 * @author masn
	 * @date 2018年8月4日 下午1:31:05
	 * @return String  返回类型
	 * @throws
	 */
	@RequestMapping("/index")
	public String index(@ModelAttribute("sysPreRegister") SysPreRegister sysPreRegister, Model model) {
		return "registerMgt/registerList";
	}	
	
	/**
	 * 
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public PageResults<SysPreRegister> query(SysPreRegister sysPreRegister,@RequestParam("page") int pageNo, @RequestParam("rows") int pageSize) {
		return registrationService.query(sysPreRegister, pageNo, pageSize);
	}

	/**
	 * @Title: exportRegisterInfo
	 * @Description: TODO(勾选导出)
	 * @author masn
	 * @date 2018年8月4日 上午10:39:32
	 * @return void  返回类型
	 * @throws
	 */
	@RequestMapping(value = "/exportRegisterInfo/{registerIds}")
	@ResponseBody
	public void exportRegisterInfo(@PathVariable("registerIds") String registerIds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SysPreRegister> dtoList = registrationService.exportRegisterInfo(ContainerUtil.string2List(registerIds));
		Integer tableHeadStartRow = 1;
		String outputFileName = "学生报名登记表-" + ExcelExportTools.getCurrenetTimeStr() + ".xls";
		ExcelExportTools.preHandle(outputFileName, request, response);
		OutputStream os = response.getOutputStream();
		ExcelParam excelParam = new ExcelParam("/export/register", "exportRegisterInfo", outputFileName,"", "学生报名登记表", dtoList, tableHeadStartRow, os);
		ExcelWriter.writeExcelByPackage(excelParam);
	}	

	/**
	 * @Title: exportByQuery
	 * @Description: TODO(默认按照查询条件导出)
	 * @author masn
	 * @date 2018年8月4日 上午10:48:10
	 * @return void  返回类型
	 * @throws
	 */
	@RequestMapping(value = "/exportByQuery")
	@ResponseBody
	public void exportByQuery(SysPreRegister sysPreRegister, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SysPreRegister> dtoList = registrationService.exportByQuery(sysPreRegister);
		Integer tableHeadStartRow = 1;
		String outputFileName = "学生报名登记表-" + ExcelExportTools.getCurrenetTimeStr() + ".xls";
		ExcelExportTools.preHandle(outputFileName, request, response);
		OutputStream os = response.getOutputStream();
		ExcelParam excelParam = new ExcelParam("/export/register", "exportRegisterInfo", outputFileName,"", "学生报名登记表", dtoList, tableHeadStartRow, os);
		ExcelWriter.writeExcelByPackage(excelParam);
	}
	
	/**
	 * @Title: expRegPdf
	 * @Description: TODO(导出报名表)
	 * @author masn
	 * @date 2018年8月6日 下午11:32:54
	 * @return void  返回类型
	 * @throws
	 */
	@RequestMapping(value = "/expRegPdfValid/{idCard}")
	@ResponseBody
	public ExecuteResult expRegPdfValid(@PathVariable("idCard") String idCard, HttpServletRequest request, HttpServletResponse response) {
		ExecuteResult result =  new ExecuteResult(true, "导出成功！注意浏览器下载的附件！");
		logger.info("导出报名表pdf验证, idCard = " + idCard);
		SysPreRegister sysRegister = registrationService.findByStuIdCard(idCard);
		if(sysRegister == null){
			return new ExecuteResult(false, "身份信息可能未注册或填写正确！");
		}
		return result;
	}
	
	/**
	 * @Title: expRegPdf
	 * @Description: TODO(导出报名表)
	 * @author masn
	 * @date 2018年8月6日 下午11:32:54
	 * @return void  返回类型
	 * @throws
	 */
    @RequestMapping(value = "/expRegPdf/{idCard}")
	public void expRegPdf(@PathVariable("idCard") String idCard, HttpServletRequest request, HttpServletResponse response) {
		logger.info("导出报名表pdf, idCard = " + idCard);
		OutputStream os = null;
		SysPreRegister sysRegister = registrationService.findByStuIdCard(idCard);
		try {
			os = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename=\"" + sysRegister.getPreOrderNo() + ".pdf\"");
			response.setContentType("application/pdf");
			response.setCharacterEncoding("UTF-8");
			registrationService.expRegPdf(sysRegister, os);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.toString());
			throw new BOException(e.toString());
		} finally {
			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("导出报名表" + ExcelExportTools.getCurrenetTimeStr() + "pdf成功！");
	}
}