package com.elementspeed.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.elementspeed.framework.common.util.DateUtil;

public class StringValidator {

	public static boolean isDate(String str) {
		return isDate(str, DateUtil.DATE_FORMAT);
	}
	
	/**
	 * 验证日期字符串是否正确
	 * 
	 * @param @param str 如'2014-10-22'，'2014-10-22 12:12'
	 * @param @param formatStr
	 * @param @return
	 * @throws
	 */
	public static boolean isDate(String str, String formatStr) {
		boolean idD = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd yyyy/MM/dd HH:mm区分大小写；
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			idD = false;
		}
		return idD;
	}

}
