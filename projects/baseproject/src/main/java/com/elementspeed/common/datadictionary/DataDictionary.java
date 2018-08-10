package com.elementspeed.common.datadictionary;

/**
 * @ClassName: DataDictionary
 * @Description: TODO(系统公用状态数据字典, 各业务的私有数据字典可继承DataDictionary)
 * @author masn
 * @date 2018年8月10日 下午12:28:16
 */
public class DataDictionary {
	
	/** 系统默认的人ID和名称 */
	public static final String SYSTEM_USER_ID = "-1";
	public static final String SYSTEM_USER_NAME = "system";
	
	public enum Status {
		/**
		 * 状态标识: -1: 驳回; 0:未**; 1:已**; 2:部分**; 3:待**，4：免**
		 */
		REFUSE, NO, YES, PART, WAIT,EXEMPT;
		public int getValue() {
			return this.ordinal() - 1;
		}
	}

}