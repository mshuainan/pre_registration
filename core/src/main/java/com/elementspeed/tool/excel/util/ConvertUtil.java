package com.elementspeed.tool.excel.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConvertUtil {
	private static final Log log = LogFactory.getLog(ConvertUtil.class);
	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
	
	public static Date toDate(String dateStr) {
		return toDate(dateStr, DATE_FORMAT_YYYY_MM_DD);
	}

	public static Date toDate(String dateStr, String dateFormat) {
		if ((dateStr == null) || ("".equals(dateStr.trim()))) {
			return null;
		}
		SimpleDateFormat f = new SimpleDateFormat(dateFormat);
		try {
			return f.parse(dateStr);
		} catch (ParseException e) {
		}
		return null;
	}

	public static String toString(Date date) {
		return toString(date, DATE_FORMAT_YYYY_MM_DD);
	}

	public static String toString(Date date, String dateFormat) {
		if (date == null) {
			return "";
		}
		if (dateFormat == null) {
			dateFormat = DATE_FORMAT_YYYY_MM_DD;
		}

		SimpleDateFormat f = new SimpleDateFormat(dateFormat);
		return f.format(date);
	}

	public static Timestamp toTimestamp(String dateStr) {
		return toTimestamp(dateStr, DATE_FORMAT_YYYY_MM_DD);
	}

	public static Timestamp toTimestamp(String dateStr, String format) {
		if ((dateStr == null) || (dateStr.trim().equals(""))) {
			return null;
		}
		if (format == null) {
			format = DATE_FORMAT_YYYY_MM_DD;
		}
		if (log.isDebugEnabled()) {
			log.debug(":::[toTimestamp]format->" + format);
		}
		DateFormat sdf = new SimpleDateFormat(format);
		try {
			return new Timestamp(sdf.parse(dateStr).getTime());
		} catch (ParseException e) {
			if (log.isDebugEnabled()) {
				log.debug("Invalid date format ->[" + dateStr + "]");
			}
		}
		return null;
	}
}
