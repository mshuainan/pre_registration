package com.elementspeed.framework.base.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomTimestampEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.support.RequestContext;

import com.elementspeed.framework.base.service.BaseService;
import com.elementspeed.framework.common.util.BeanUtil;
import com.elementspeed.framework.common.util.DateUtil;

/**
 * 基础controller
 *
 */
@ControllerAdvice
@Controller
public class BaseCtrl {
	public final static Logger log = Logger.getLogger(BaseCtrl.class);
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		//去掉参数的前后空格
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        //转换时间,日期类型请用date,时间类型请用timestamp,否则转换出错
		SimpleDateFormat fmt1 = new SimpleDateFormat(DateUtil.DATE_FORMAT);  
		SimpleDateFormat fmt2 = new SimpleDateFormat(DateUtil.DATE_FORMAT_HMS);  
		SimpleDateFormat fmt3 = new SimpleDateFormat(DateUtil.DATE_FORMAT_HM);  
		binder.registerCustomEditor(Date.class, new CustomDateEditor(fmt1, true));   //true:允许输入空值，false:不能为空值
		binder.registerCustomEditor(Timestamp.class, new CustomTimestampEditor(new SimpleDateFormat[]{fmt2,fmt3}, true));
    }
	
	/**
	 * 执行service.methodName方法, args是methodName的参数
	 * @param service
	 * @param methodName
	 * @param args
	 * @return ExecuteResult
	 */
	protected ExecuteResult execute(BaseService service, String methodName, Object... args) {
		try {
			Method m = BeanUtil.findDeclaredMethodWithMinimalParameters(service.getClass(), methodName);
			m.invoke(service, args);
			return new ExecuteResult(true);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return new ExecuteResult(false, getAllException());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return new ExecuteResult(false, getAllException());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return boException(e);
		}
	}

	private ExecuteResult boException(InvocationTargetException e) {
		if (e.getMessage() != null && e.getMessage().indexOf("Exception") == -1) {
			return new ExecuteResult(false, e.getMessage());
		} 
		else if (e.getTargetException() != null && e.getTargetException().getMessage().indexOf("Exception") == -1) {
			return new ExecuteResult(false, e.getTargetException().getMessage());
		}
		return new ExecuteResult(false, getAllException());
	}
	/**
	 * 可以自定义成功后的返回消息
	 * @param service
	 * @param methodName
	 * @param successMsg
	 * @param args
	 * @return
	 */
	protected ExecuteResult executeWithMsg(BaseService service, String methodName, String successMsg, Object... args) {
		try {
			Method m = BeanUtil.findDeclaredMethodWithMinimalParameters(service.getClass(), methodName);
			m.invoke(service, args);
			return new ExecuteResult(true, successMsg);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return new ExecuteResult(false, getAllException());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return new ExecuteResult(false, getAllException());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			log.info(e.getTargetException().getMessage());
			return boException(e);
		}
	}
	
	/**
	 * 该方法通常用于controller与页面交互.
	 * <br>
	 * 执行service.methodName方法, args是methodName的参数.
	 * 返回结果ExecuteResult中将包含resultMap.
	 * 
	 * @param resultMap
	 * @param service
	 * @param methodName
	 * @param args
	 * @return
	 */
	protected ExecuteResult execute(Map<String, ?> resultMap, BaseService service, String methodName, Object... args) {
		try {
			Method m = BeanUtil.findDeclaredMethodWithMinimalParameters(service.getClass(), methodName);
			m.invoke(service, args);
			return new ExecuteResult(true, resultMap);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return new ExecuteResult(false, getAllException());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return new ExecuteResult(false, getAllException());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			log.info(e.getTargetException().getMessage());
			return boException(e);
		}
	}
	
	/**
	 * 该方法通常用于controller与页面交互.
	 * <br>
	 * 执行service.methodName方法, 并将methodName方法的结果添加到ExecuteResult中.
	 * <br>
	 * 返回结果ExecuteResult中将包含resultMap, key:resultMapKey, value:service.methodName(args...)
	 * 
	 * @param resultMapKey
	 * @param service
	 * @param methodName
	 * @param args	methodName的参数.
	 * @return
	 */
	protected ExecuteResult execute(String resultMapKey, BaseService service, String methodName, Object... args) {
		try {
			Method m = BeanUtil.findDeclaredMethodWithMinimalParameters(service.getClass(), methodName);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put(resultMapKey, m.invoke(service, args));
			return new ExecuteResult(true, resultMap);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return new ExecuteResult(false,getAllException());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return new ExecuteResult(false, getAllException());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			log.info(e.getTargetException().getMessage());
			return boException(e);
		}
	}
	/***
	 * 返回当前用户的国家，地区设置
	 * @param request
	 * @return
	 */
	protected Locale getCurrentLocale(HttpServletRequest request) {
		RequestContext requestContext = new RequestContext(request);
		return requestContext.getLocale();
	}
	
	public static String getAllException() {
		return "提示";
	}
}
