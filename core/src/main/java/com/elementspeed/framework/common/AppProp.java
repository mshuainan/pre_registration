package com.elementspeed.framework.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.elementspeed.common.env.EnvironmentProfiles;
import com.elementspeed.framework.common.AppProp.ConfProp.HtmlEdtiorConf;
import com.elementspeed.framework.common.AppProp.ConfProp.SystemConf;
import com.elementspeed.framework.common.AppProp.ConfProp.ViewOptConf;
import com.elementspeed.framework.common.AppProp.ConfProp.ViewShowConf;
import com.elementspeed.framework.common.util.PropertiesUtil;

/**
 * 读取资源，从jar包和各类工程读取
 * 应用配置，读取应用配置，并允许工程进行覆盖
 *
 */
public class AppProp {

	static private Properties prop;
	
	final static private String[] ENV_FILE_NAME = new String[]{"classpath*:prop/appConf.properties",
			"classpath*:prop/appConf.module.properties","classpath*:prop/appConf.project.properties"
		};
		final static private String[] ENV_FILE_NAME_DEVELOPMENT = new String[]{"classpath*:prop/appConf.properties","classpath*:prop/appConf.development.properties",
			"classpath*:prop/appConf.development.module.properties","classpath*:prop/appConf.development.project.properties"
		};
		final static private String[] ENV_FILE_NAME_FUNCTIONAL = new String[]{"classpath*:prop/appConf.properties","classpath*:prop/appConf.functional.properties",
			"classpath*:prop/appConf.functional.project.properties"
		};

	
	public static final String FILE_LOG_DIR = "file.log.dir";
	
	/**
	 * 配置项
	 *
	 */
	interface ConfProp {

		public String getValue();
		
		/**
		 * 系统配置
		 *
		 */
		enum SystemConf implements ConfProp {
			//系统启动模式
			DEBUGE("framework.debug");
			
			private String value;
			
			private SystemConf(String value) {
				this.value = value;
			}

			@Override
			public String getValue() {
				return value;
			}

			public void setValue(String value) {
				this.value = value;
			}
		}
		
		/**
		 * 组件类型配置
		 */
		enum ViewOptConf implements ConfProp {

			// 信息弹出框样式
			MESSAGE_WINDOW_TYPE("framework.msgType"),
			//信息弹出框消失时间
			MESSAGE_WINDOW_HIDE_TIME("framework.msgType.timeout");
			
			private String value;
			
			private ViewOptConf(String value) {
				this.value = value;
			}

			@Override
			public String getValue() {
				return value;
			}

			public void setValue(String value) {
				this.value = value;
			}
		}
		
		/**
		 * 组件样式配置
		 */
		enum ViewShowConf implements ConfProp {
			// 翻译栏样式
			PAGINATIONTYPE("framework.PaginationType"),
			//按钮位置
			COMMONBTNALIGN("framework.CommonBtnAlign"),
			//主题风格
			THEMES("framework.themes");
			
			private String value;
			
			private ViewShowConf(String value) {
				this.value = value;
			}
			
			@Override
			public String getValue() {
				return value;
			}

			public void setValue(String value) {
				this.value = value;
			}
		}
		
		enum HtmlEdtiorConf implements ConfProp {
			// 翻译栏样式
			IMGTYPE("htmleditor.imgtype"),
			//按钮位置
			IMGSIZE("htmleditor.imgsize");
			
			private String value;
			
			private HtmlEdtiorConf(String value) {
				this.value = value;
			}
			
			@Override
			public String getValue() {
				return value;
			}

			public void setValue(String value) {
				this.value = value;
			}
		}
	}
	
	
	public static void init() {
		try {
			load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void load() throws IOException {
		
		try {
			String[] paths = ENV_FILE_NAME;
			if(EnvironmentProfiles.getProfileAsSystemProperty().equals(EnvironmentProfiles.DEVELOPMENT)){
				paths = ENV_FILE_NAME_DEVELOPMENT;
			}
			if (EnvironmentProfiles.FUNCTIONAL_TEST.equals(EnvironmentProfiles.getProfileAsSystemProperty())) { 
				paths = ENV_FILE_NAME_FUNCTIONAL;
			}
			
			if(prop==null) {
				prop = new Properties();
			}
			//使用spring的属性工具处理
			PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
			for(String propPath : paths){
				Resource[] resources = resourceLoader.getResources(propPath);
				for(Resource resource : resources){
					PropertiesLoaderUtils.fillProperties(prop, resource);
				}
			} 
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (java.io.IOException e)   {
			e.printStackTrace();  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String getPropValue(ConfProp confProp) {
		return PropertiesUtil.getPropValue(prop, confProp.getValue());
	}
	
	/**
	 * 根据Key值获取
	 * @param key
	 * @param defaultV
	 * @return
	 */
	public static String getPropValue(String key,String defaultV) {
		return PropertiesUtil.getPropValue(prop, key,defaultV);
	}
	
	/**
	 * 是否以debugger模式启动
	 * @return
	 */
	final public static boolean isDebug() {
		return Boolean.parseBoolean(getPropValue(SystemConf.DEBUGE));
	}
	
	/**
	 * 是否以debugger模式启动
	 * @return
	 */
	final public static boolean isLdap() {
		return Boolean.parseBoolean(getPropValue("login.ldap","false"));
	}
	
	
	/**
	 * 翻页控件展现型式
	 * @return
	 */
	final public static String getPageinationType() {
		return getPropValue(ViewShowConf.PAGINATIONTYPE);
	}
	
	/**
	 * 全局按钮的位置
	 * @return
	 */
	final public static String getCommonBtnAlign() {
		return getPropValue(ViewShowConf.COMMONBTNALIGN);
	}
	
	/**
	 * 全局主题风格
	 * @return
	 */
	final public static String getThemes() {
		return getPropValue(ViewShowConf.THEMES) ; 
	}
	
	/**
	 * 设置系统主题风格
	 * @param value
	 */
	final public static void setTheme(String value) {
		PropertiesUtil.setPropValue(prop, ViewShowConf.THEMES.getValue(), value);
	}
	
	/**
	 * 获取信息提示窗口样式
	 * @return
	 */
	final public static String getShowMessageType() {
		return getPropValue(ViewOptConf.MESSAGE_WINDOW_TYPE);
	}
	
	/**
	 * 获取隐藏信息弹出框时间
	 * @return
	 */
	final public static String getMessageTimeout() {
		return getPropValue(ViewOptConf.MESSAGE_WINDOW_HIDE_TIME);
	}
	
	final public static String getHtmlEditorImgType() {
		return getPropValue(HtmlEdtiorConf.IMGTYPE);
	}
	
	final public static String getHtmlEditorImgSize() {
		return getPropValue(HtmlEdtiorConf.IMGSIZE);
	}
	
	/**
	 * 恢复默认配置
	 */
	final public static void reset(){
		try {
			load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
