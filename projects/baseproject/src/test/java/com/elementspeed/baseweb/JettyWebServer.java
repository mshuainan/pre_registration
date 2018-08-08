package com.elementspeed.baseweb;

import org.eclipse.jetty.server.Server;

import com.elementspeed.test.jetty.JettyFactory;
import com.elementspeed.test.spring.Profiles;

/**
 * 使用jetty嵌入式服务
 * @author pengj
 * @date 2016年3月4日
 * @path com.elementspeed.baseweb.JettyWebServer.java
 */
public class JettyWebServer {
	
	public final static int PORT = 8080;
	public final static String CONTEXTPATH = "/register";
	public final static String[] TLD_JAR_NAMES = new String[]{"spring-webmvc"};

	public static void main(String[] args) throws Exception{
		/* 设置spring的profile */
		Profiles.setProfileAsSystemProperty(Profiles.PRODUCTION);
		
		/* 启动JETTY */
		Server server = JettyFactory.createServerInSource(PORT, CONTEXTPATH,new String[]{
				"src/main/webapp",
				"../baseproject/src/main/webapp"
				});
		JettyFactory.setTldJarNames(server, TLD_JAR_NAMES);
		
		try{
			server.start();
			System.out.println("[INFO] Server running at http://localhost:" + PORT + CONTEXTPATH);
			System.out.println("[HINT] Hit Enter to reload the application quickly");
			
			while(true){
				char ch = (char) System.in.read();
				if(ch == '\n'){
					JettyFactory.reloadContext(server);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
