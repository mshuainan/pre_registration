package com.elementspeed.framework.common.sn.generator;
import com.elementspeed.framework.base.exception.BOException;
/**
 * SRM业务单号生成器
 *
 */
public interface IGenerator  {	
	/**
	 * 生成子序列串
	 * @param formatStr
	 * @return
	 * @throws BOException 
	 */
	String generate() throws BOException;
}
