package com.elementspeed.common.datadictionary;


/**
 * 系统公用状态数据字典, 各业务的私有数据字典可继承DataDictionary
 *
 */
public class DataDictionary {
	
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