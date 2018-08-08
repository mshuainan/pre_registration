package com.elementspeed.common.file.htmleditor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.elementspeed.common.env.EnvironmentVars;
import com.elementspeed.framework.common.AppProp;
import com.elementspeed.framework.common.util.DateUtil;
import com.elementspeed.framework.common.util.SpringContextUtil;
import com.elementspeed.framework.common.util.file.FileUtil;

@Controller
@RequestMapping("/common/upload/atta")
public class UploadAttaCtrl {

	
	/**
	 * 文件上传
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 * @return json response
	 */
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fileUpload(
			@RequestParam("imgFile") MultipartFile imgFile,
			HttpServletRequest request, HttpServletResponse response) {
		// 定义允许上传的文件扩展名
		try {
			String extString = AppProp.getHtmlEditorImgType();

			String fileName = imgFile.getOriginalFilename();
			InputStream inputStream = imgFile.getInputStream();
			// 检查扩展名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(extString.split(",")).contains(fileExt)) {
				return getError(SpringContextUtil.getMessage("msg.format.file") + extString);
			}
			
			String sFileName = DateUtil.convertDateToString(new Date(),
					"yyyyMM") + System.currentTimeMillis() + "." + fileExt ;
			if (inputStream.available() / 1000 / 1000 > Integer.parseInt(AppProp.getHtmlEditorImgSize())) {
				return getError(SpringContextUtil.getMessage("msg.upload.file") + Integer.parseInt(AppProp.getHtmlEditorImgSize()) + "M");
			}
			String path = EnvironmentVars.getUpPath() + DateUtil.getCurrentYearMonth() + "/"
					+ getModuleName() + "/" + sFileName;
			File file = new File(path);
			FileUtil.copyInputStreamToFile(imgFile.getInputStream(), file);

			Map<String, Object> succMap = new HashMap<String, Object>();
			succMap.put("error", 0);
			succMap.put("url", request.getContextPath() + path);
			return succMap;
		} catch (IOException e1) {
			e1.printStackTrace();
			return getError(SpringContextUtil.getMessage("msg.upload.failed"));
		}
	}

	private Map<String, Object> getError(String errorMsg) {
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("error", 1);
		errorMap.put("message", errorMsg);
		return errorMap;
	}
	
	public String getModuleName() {
		return this.getClass().getSimpleName();
	}
}
