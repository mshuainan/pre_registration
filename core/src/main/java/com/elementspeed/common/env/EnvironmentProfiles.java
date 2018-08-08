package com.elementspeed.common.env;

import com.elementspeed.framework.common.util.StringUtil;

/**
 * Spring profile 常用方法与profile名称。
 * 
 * @author calvin
 */
public class EnvironmentProfiles {

	public static final String ACTIVE_PROFILE = "spring.profiles.active";
	public static final String DEFAULT_PROFILE = "spring.profiles.default";

	public static final String PRODUCTION = "production";
	public static final String DEVELOPMENT = "development";
	public static final String UNIT_TEST = "test";
	public static final String FUNCTIONAL_TEST = "functional";

	/**
	 * 在jetty开发下，在spring监听初始化时调用设置
	 */
	public static void setProfileAsSystemProperty(String profile) {
		//jetty启动
		System.setProperty(ACTIVE_PROFILE, profile);
	}
	
	/**
	 * 在发布容器下，tomcat不配置激活属性，所以设置默认属性
	 * @param profile
	 */
	public static void setProfileAsSystemProperty2(String profile) {
		if (StringUtil.isEmpty(System.getProperty(ACTIVE_PROFILE))) {
			//tomcat启动
			System.setProperty(DEFAULT_PROFILE, profile);
		}
	}
	
	/**
	 * 开发模式下使用“激活属性”，发布模式下使用“默认属性”
	 * 主要是为了解决jetty开发和tomcat发布情况下，对策略模式不同的读取。
	 * jetty配置激活属性，tomcat配置默认属性。优先读取激活属性，这样在开发下使用jetty，在发布下使用tomcat就不冲突
	 */
	public static String getProfileAsSystemProperty() {
		if(StringUtil.isEmpty(System.getProperty(ACTIVE_PROFILE))){
			return System.getProperty(DEFAULT_PROFILE);
		}
		return System.getProperty(ACTIVE_PROFILE);
	}
}
