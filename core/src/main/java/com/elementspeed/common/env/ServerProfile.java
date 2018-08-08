package com.elementspeed.common.env;


/**
 * 读取服务器下配置文件信息
 */
public class ServerProfile {
	
	private static ServerProfile serverProFile = new ServerProfile();
	private ServerProfile() { }
	
	public static ServerProfile getInstance(){
		return serverProFile;
	}
	
	public static String getRunServerType() {
		String serverType = CommonPropertiesLoad.getValueByKey("system.running.serverType");
		return serverType != null ? serverType : "";
	}
	
}
