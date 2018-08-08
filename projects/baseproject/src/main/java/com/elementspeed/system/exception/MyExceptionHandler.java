package com.elementspeed.system.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.elementspeed.framework.base.controller.BaseCtrl;
import com.elementspeed.framework.common.util.SpringContextUtil;

/**
 * 全局异常捕获统一处理
 * 
 * @author ligeng
 *
 */
public class MyExceptionHandler implements HandlerExceptionResolver {
	public final static Logger log = Logger.getLogger(MyExceptionHandler.class);

	/**
	 * 全局项目异常处理
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// TODO Auto-generated method stub
		ex.printStackTrace();
		// 上传文件大于限制大小抛出异常
		if (ex instanceof MaxUploadSizeExceededException) {
			MaxUploadSizeException(response, ex);
		}
		ModelAndView view = new ModelAndView();
		// 获取发生异常方法的返回值context包含public+返回值 类名+方法名（参数）

		String[] context = handler.toString().split(" ");
		String type = null;
		if (context.length > 0) {
			type = context[1];
		}
		request.setAttribute("errMsg", ex.getMessage()==null?"":ex.getMessage());
		
		// 返回jsp界面异常处理 if
		if (type.contains("String")) {
			return stringException(ex);
		} else {
			// ajax异常处理
			ajaxException(response, ex);
		}

		// 判断是否为ajax请求
		/* 区分ajax */
		/*
		 * boolean isAjax = request.getHeader("X-Requested-With") != null &&
		 * "XMLHttpRequest"
		 * .equals(request.getHeader("X-Requested-With").toString());
		 */
		/*
		 * if (! isAjax) { return stringException(ex); } else if(ex instanceof
		 * BOException) { boException(response, ex); }else{
		 * ajaxException(response, ex); }
		 */
		return view;
	}

	/**
	 * 上传文件过大异常
	 * 
	 * @param response
	 * @param ex
	 */
	private void MaxUploadSizeException(HttpServletResponse response,
			Exception ex) {
		try {
			response.getWriter().write(
					"{\"success\":false,\"message\":\""+SpringContextUtil.getMessage("msg.upload.file")
							+ ((MaxUploadSizeExceededException) ex)
									.getMaxUploadSize() / 1000 / 1000 + "\"}");
			log.info(ex.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	}

	/**
	 * 返回jsp界面异常
	 * 
	 * @return
	 */
	private ModelAndView stringException(Exception ex) {
		ModelAndView view1 = new ModelAndView("system/500");
		log.info(ex.getMessage());
		return view1;
	}

	/**
	 * 其他异常（多为ajax异常）
	 * 
	 * @param response
	 */
	private void ajaxException(HttpServletResponse response, Exception ex) {
		PrintWriter out = null;
		try {
			//指定返回的异常类型为json对象
			response.setContentType("application/json");
			out = response.getWriter();
			// bo异常
			if (ex.getMessage() != null
					&& ex.getMessage().indexOf("Exception") == -1) {
				out.write("{\"success\":false,\"message\":\"" + ex.getMessage()
						+ "\"}");
			} else {
				out.write("{\"success\":false,\"message\":\""
						+ BaseCtrl.getAllException() + "\"}");
			}
			log.info(ex.getMessage());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		} finally {
			out.close();
		}
	}

}
