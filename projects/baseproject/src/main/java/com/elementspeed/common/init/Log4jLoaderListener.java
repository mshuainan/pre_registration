package com.elementspeed.common.init;

import java.io.FileNotFoundException;

import javax.servlet.ServletContextEvent;

import org.springframework.util.Log4jConfigurer;
import org.springframework.web.context.ContextLoaderListener;

import com.elementspeed.common.env.EnvironmentProfiles;
import com.elementspeed.framework.common.util.StringUtil;

/**
 * 日志监听，同时初始化一下环境变量
 * @author pjjxj
 * 2018年1月2日
 * com.elementspeed.common.init.Log4jLoaderListener.java
 */
public class Log4jLoaderListener extends ContextLoaderListener{
	
	final static private String LOG4JPATH = "classpath:prop/log4j.properties";
	final static private String LOG4JPATH_PRODUCTION = "classpath:prop/log4j.production.properties";

	@Override
	public void contextInitialized(ServletContextEvent event) {
		String profile = event.getServletContext().getInitParameter(EnvironmentProfiles.DEFAULT_PROFILE);
		if(StringUtil.isEmpty(EnvironmentProfiles.getProfileAsSystemProperty())){
			EnvironmentProfiles.setProfileAsSystemProperty(profile);
		}
		String path = LOG4JPATH;
		if(EnvironmentProfiles.getProfileAsSystemProperty().equals(EnvironmentProfiles.PRODUCTION)){
			path = LOG4JPATH_PRODUCTION;
		}
		try {
			Log4jConfigurer.initLogging(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


}
