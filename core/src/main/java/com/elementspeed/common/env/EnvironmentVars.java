package com.elementspeed.common.env;
/***
 * 部署环境有关的配置
 * @author LI ZL
 */
public class EnvironmentVars {
	private static EnvironmentVars environmentVars = new EnvironmentVars();
	
	private EnvironmentVars() {
	}
	
	public static EnvironmentVars getInstance(){
		return environmentVars;
	}
	
	/***
	 * 根据KEY 取值
	 * @return
	 */
	public static String getValueByKey(String key) {
		return CommonPropertiesLoad.getValueByKey(key);
	}
	/**
	 * 根据Key取值，如果为空，返回默认值
	 * @param key 关键元素
	 * @param defaultV 默认值
	 * @return 对应值
	 */
	public static String getValueByKey(String key,String defaultV) {
		return CommonPropertiesLoad.getValueByKey(key, defaultV);
	}
	
/*	public static String getSystemName() {
		return CommonPropertiesLoad.getValueByKey("system.name");
	}*/	
	
	public static String getSystemCopyRight() {
		return CommonPropertiesLoad.getValueByKey("system.copyright");
	}	

	public static String getServicePath() {
		return CommonPropertiesLoad.getValueByKey("system.servicePath");
	}	
	
	public static String getIntfHostPrefix() {
		return CommonPropertiesLoad.getValueByKey("system.intfserver.prefix");
	}
	
	public static String getUpPath() {
		return CommonPropertiesLoad.getValueByKey("path.attachment.upload") + "/";
	}
	
	public static String getLogoPath() {
		return CommonPropertiesLoad.getValueByKey("path.dlv.logo");
	}
	
	public static String getApkDownLoadPath() {
		return CommonPropertiesLoad.getValueByKey("path.apk.download");
	}
	
	public static String getTemplatePath() {
		return CommonPropertiesLoad.getValueByKey("path.template");
	}
	
	/**
	 * 激活超期天数
	 * @return
	 */
	public static String getRegisterOverdueDay() {
		return CommonPropertiesLoad.getValueByKey("system.register.overdue.day");
	}
	/**
	 * 获密码错误锁定账户次数
	 * @return
	 */
	public static String getLockUser() {
		return CommonPropertiesLoad.getValueByKey("system.lock.user.account");
	}
	/**
	 * 获取密码错误账户锁定自动解锁时间（分钟）
	 * @return
	 */
	public static String getUnlockMinute() {
		return CommonPropertiesLoad.getValueByKey("system.unlock.minute");
	}
	
	/**
	 * 文件服务器 IP
	 * @return
	 */
	public static String getFileIp() {
		return CommonPropertiesLoad.getValueByKey("system.file.service.ip");
	}
	/**
	 * 文件服务器端口
	 * @return
	 */
	public static String getFilePort() {
		return CommonPropertiesLoad.getValueByKey("system.file.service.port");
	}
	/**
	 * 文件服务器用户
	 * @return
	 */
	public static String getFileUserPwd() {
		return CommonPropertiesLoad.getValueByKey("system.file.service.pwd");
	}
	/**
	 * 文件服务器用户密码
	 * @return
	 */
	public static String getFileUser() {
		return CommonPropertiesLoad.getValueByKey("system.file.service.user");
	}
	/**
	 * 文件服务器url路径
	 * @return
	 */
	public static String getFileUrl() {
		return CommonPropertiesLoad.getValueByKey("system.file.service.serverPath");
	}
	/**
	 * 是否使用文件服务器 0不使用，1使用
	 * @return
	 */
	public static String getFileUseFlag() {
		return CommonPropertiesLoad.getValueByKey("system.file.service.useflag");
	}
	/**
	 * 文件服务器图片以外文件夹（文件服务器配置映射的文件夹：相对路径，默认跟在服务器配置路径后面）
	 * @return
	 */
	public static String getDocumentFile() {
		return CommonPropertiesLoad.getValueByKey("system.file.service.documentfile");
	}
	
	/**
	 * 文件服务器图片文件夹（文件服务器配置映射的文件夹：相对路径，默认跟在服务器配置路径后面）
	 * @return
	 */
	public static String getImgFile() {
		return CommonPropertiesLoad.getValueByKey("system.file.service.imgfile");
	}
	/**
	 * 文件服务器文件上传下载临时路径
	 * @return
	 */
	public static String getTempFile() {
		return CommonPropertiesLoad.getValueByKey("system.file.service.temppath");
	}
	
	
	/**
	 * 获取审核天数
	 * @return
	 */
	public static String getRegisterCheckDay() {
		return CommonPropertiesLoad.getValueByKey("system.register.check.day");
	}

	public static String getMallServicePath() {
		return CommonPropertiesLoad.getValueByKey("mall.servicePath");
	}
	
	/**
	 * 商城附件上传路径
	 * 
	 * @return
	 */
	public static String getMallUpPath() {
		return CommonPropertiesLoad.getValueByKey("mall.attachment.upload.path") + "/";
	}
	
	/**
	 * @Title: getDomian
	 * @Description: TODO(获取Domian)
	 * @author masn
	 * @date 2018年1月26日 下午4:18:53
	 * @return String  返回类型
	 * @throws
	 */
	public static String getDomian() {
		return CommonPropertiesLoad.getValueByKey("system.domain");
	}
}
