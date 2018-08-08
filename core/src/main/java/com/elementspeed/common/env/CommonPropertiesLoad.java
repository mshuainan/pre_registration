package com.elementspeed.common.env;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.elementspeed.common.util.OrderedProperties;
import com.elementspeed.framework.common.util.StringUtil;

/**
 * 
 * 读取properties
 * 
 * @author szl
 * @datetime 2017年1月10日下午6:34:05
 */
public class CommonPropertiesLoad {
	private final static Log log = LogFactory.getLog(CommonPropertiesLoad.class);
	private static String serverPath = System.getProperty("catalina.home"); // 获取tomcat的路径
	private static CommonPropertiesLoad commonPropertiesLoad = new CommonPropertiesLoad();
	private static final String[] PROJECT_FILE_NAME = new String[] {
			"classpath*:prop/srm.properties",
			"classpath*:prop/srm-ext.properties", "classpath*:prop/srm-ext-project.properties"};
	private static final String SYSTEM_PATH = File.separator + "prop" + File.separator;
	private static Map<String, String> commonPropertiesMap = null;

	public static CommonPropertiesLoad getInstance() {
		return commonPropertiesLoad;
	}

	/**
	 * 服务器启动加载
	 * 
	 * @author szl
	 * @datetime 2017年1月10日下午6:34:55
	 */
	public static void init() {
		log.debug("start CommonPropertiesLoad");
		PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
		try {
			// 使用spring的属性工具处理
			OrderedProperties p = new OrderedProperties();
			commonPropertiesMap = new HashMap<String, String>();
			for (String propPath : PROJECT_FILE_NAME) {
				Resource[] resources = resourceLoader.getResources(propPath);
				for (Resource resource : resources) {
					String fileName = resource.getFilename();
					// 获取服务器路径
					String filePath = serverPath + SYSTEM_PATH + fileName;
					log.info("filePath = " + filePath);
					File file = new File(filePath);
					if (StringUtil.isNotEmpty(serverPath)) { //tomcat.home路径不为空，直接以tomcat目录为准
						// 文件不存在
						if (!file.exists()) {
							File propDir = new File(serverPath + SYSTEM_PATH);
							if (!propDir.exists()) {
								propDir.mkdir();
							}
							p.store(filePath, resource.getInputStream());
							p = new OrderedProperties(filePath);
							setPropertiesToMap(p);
						} else {// 文件存在
							p = new OrderedProperties(filePath);
							setPropertiesToMap(p);
						}
					} else { //若tomcat.home目录为空，则直接读取项目配置文件
						PropertiesLoaderUtils.fillProperties(p, resource);
						setPropertiesToMap(p);
					}
				}
			}
			log.debug("end CommonPropertiesLoad");
		} catch (java.io.IOException e) {
			e.printStackTrace();
			log.error("读取配置文件出错！" + e.getMessage());
		}

	}

	/**
	 * 把properties属性放入map当中
	 * 
	 * @param p
	 * @author szl
	 * @datetime 2017年1月13日上午11:22:13
	 */
	public static void setPropertiesToMap(Properties p) {
		if (p.size() > 0) {
			Iterator<Object> it = p.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = p.getProperty(key);
				commonPropertiesMap.put(key, value);
			}
		}
	}

	/***
	 * 根据KEY 取值
	 * 
	 * @return
	 */
	public static String getValueByKey(String key) {
		if (commonPropertiesMap != null) {
			return commonPropertiesMap.get(key);
		} else {
			return null;
		}
	}

	/**
	 * 根据Key取值，如果为空，返回默认值
	 * 
	 * @param key
	 *            关键元素
	 * @param defaultV
	 *            默认值
	 * @return 对应值
	 */
	public static String getValueByKey(String key, String defaultV) {
		String value = null;
		if (commonPropertiesMap != null) {
			value = commonPropertiesMap.get(key);
			if (value == null) {
				return defaultV;
			}
			return value;
		}
		return defaultV;
	}

}
