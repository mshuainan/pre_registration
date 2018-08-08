package com.elementspeed.tool.excel.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class DateUtil {

	/**
	 * 将一个字符串转换为日期
	 * sai.deng on 2013-12-23 下午2:45:54
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date toDate(String dateStr, String format) {
		DateFormat df = null;
		try{
			if (Pattern.matches("^[0-9]{2,4}-[0-9]{1,2}-[0-9]{1,2}$", dateStr)) {
				df = new SimpleDateFormat(format);
			} else {
				format = "EEE MMM dd HH:mm:ss zzz yyyy";
				df = new SimpleDateFormat(format, Locale.US);
			}
			return df.parse(dateStr);
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 将一个字符串转换为日期
	 * sai.deng on 2013-12-23 下午2:46:45
	 * @param dateStr
	 * @return
	 */
	public static Date toDate(String dateStr) {
		return toDate(dateStr, "yyyy-MM-dd");
	}
}
