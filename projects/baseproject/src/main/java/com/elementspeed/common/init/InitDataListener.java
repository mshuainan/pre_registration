package com.elementspeed.common.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elementspeed.common.env.CommonPropertiesLoad;
import com.elementspeed.common.env.EnvironmentProfiles;
import com.elementspeed.framework.common.AppProp;

/**
 * 启动应用时-初始数据加载
 * @author sai.deng
 * @date 2015年12月11日 上午8:40:50
 */
public class InitDataListener implements ServletContextListener {
	private final static Logger logger = LoggerFactory.getLogger(InitDataListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("InitDataListener.contextDestroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent ctx) {
		logger.info("InitDataListener.contextInitialized start.");
		String profile = ctx.getServletContext().getInitParameter(EnvironmentProfiles.DEFAULT_PROFILE);
		logger.warn("InitDataListener  profile = " + profile);
		EnvironmentProfiles.setProfileAsSystemProperty2(profile);
		logger.warn("default profile = " + System.getProperty(EnvironmentProfiles.DEFAULT_PROFILE));
		logger.warn("active profile = " + System.getProperty(EnvironmentProfiles.ACTIVE_PROFILE));
		CommonPropertiesLoad.init();
//		EnvironmentVars.init();
		AppProp.init();
//		MailConfigUtil.init();
		reLoadInitData();
		logger.info("load server file profiles");
//		ServerProfile.readFile();
		logger.info("InitDataListener.contextInitialized finished.");
	}

	/**
	 * 加载初始化数据
	 */
	public static void reLoadInitData() {
		
	}
	
}