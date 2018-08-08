package com.elementspeed.framework.common;


/**
 * 项目版本控制器, 在每个jsp引入的静态文件中都应当后缀   ?version=<%=ersion %>">, 以便 发布新版本时使老版的静态文件失效.
 * 开发时, 可使用getDebugVersion()清除所有缓存.
 * <ul>
 * 例: &lt;script src="/ElementSpeedStatic/js/thirdparties/jquery.form2json.js?version=<%=version %>">&lt;/script>
 */
public class Version {

	private static long version;
	
	static {
		version = System.currentTimeMillis();
	}
	
	/**
	 * 获取静态资源版本号
	 * @return
	 */
	final public static long getVersionNo() {
		return AppProp.isDebug() ? System.currentTimeMillis() : version;
	}
}
