package com.elementspeed.common.excel;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.elementspeed.common.env.CommonPropertiesLoad;
import com.elementspeed.framework.common.util.DateUtil;
import com.elementspeed.framework.common.util.StringUtil;

/**
 * 导出Excel前预处理
 * @author sai.deng
 * @date 2015年11月6日 下午2:29:50
 */
@SuppressWarnings("serial")
public class ExcelExportTools implements Serializable {
	/**
	 * 导出Excel前的预处理，设置上下文内容
	 * @author sai.deng on 2015年11月6日 下午2:16:48
	 * @param outputFileName
	 * @param request
	 * @param response
	 */
	public static void preHandle(String outputFileName, HttpServletRequest request,
			HttpServletResponse response) {
		String encodedFileName = getEncodedFileName(request, outputFileName);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment;filename=" + encodedFileName);
	}
	
	public static String getCurrenetTimeStr() {
		return DateUtil.convertDateToString(DateUtil.getNow(), "yyyyMMdd-HHmm");
	}
	
	private static String getEncodedFileName(HttpServletRequest request, String fileName) {
		if (fileName == null) {
			return null;
		}
		String encodedOutputFileName = fileName;
		String agent = request.getHeader("user-agent");
		if (agent != null) {
			try {
				if ((agent.indexOf("Firefox") >= 0) || (agent.indexOf("Gecko") >= 0)) {
					encodedOutputFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				} else if (agent.indexOf("MSIE") >= 0) {
					encodedOutputFileName = URLEncoder.encode(fileName, "UTF-8");
				}
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
			}
		}
		return encodedOutputFileName;
	}
	/**
	 * 每个模块导出Excel条数取得，如果没有配置，则默认是65536条
	 * @param key
	 * @return
	 * @author szl
	 * @datetime 2017年1月17日上午9:56:00
	 */
	public static Integer getExportNum(String key) {
		String musStr = CommonPropertiesLoad.getValueByKey(key);
		Integer mus = 0;
		if (StringUtil.isNotEmpty(musStr)) {
			mus = Integer.parseInt(musStr);// 每个工作表格最多存储65536条数据（注：excel表格一个工作表可以存储65536条）
		} else {
			mus = Integer.parseInt(CommonPropertiesLoad.getValueByKey("excel.export.defalut.mus", "65536"));// 每个工作表格最多存储65536条数据（注：excel表格一个工作表可以存储65536条）
		}
		return mus;
	}
}
