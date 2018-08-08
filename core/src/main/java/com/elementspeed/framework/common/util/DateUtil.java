package com.elementspeed.framework.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import com.elementspeed.tool.excel.util.ConvertUtil;

/**
 *
 * @author xiongl
 *
 */
public class DateUtil {
	/**
	 * 日期字符串格式yyyy-MM-dd
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	/**
	 * 日期字符串格式yyyy-MM
	 */
	public static final String DATE_FORMAT_Y_M = "yyyy-MM";
	/**
	 * 日期字符串格式MM-dd
	 */
	public static final String DATE_FORMAT_MM_DD = "MM-dd";
	/**
	 * 日期字符串格式yyyMMddHHmmss
	 */
	public static final String DATE_FORMAT_YMDHS = "yyyyMMddHHmmss";
	/**
	 * 日期字符串格式yyyMMdd
	 */
	public static final String DATE_FORMAT_YMD = "yyyyMMdd";
	/**
	 * 日期字符串格式yyMMdd
	 */
	public static final String DATE_FORMAT_YYMMDD = "yyMMdd";
	/**
	 * 日期字符串格式yyMM
	 */
	public static final String DATE_FORMAT_YYMM = "yyMM";
	/**
	 * 日期字符串格式yyyMM
	 */
	public static final String DATE_FORMAT_YM = "yyyyMM";
	
	/**
	 * 日期字符串格式yyyy
	 */
	public static final String DATE_FORMAT_YY = "yyyy";
	
	/**
	 * 日期字符串格式yyyy-MM-dd HH
	 */
	public static final String DATE_FORMAT_H = "yyyy-MM-dd HH";
	/**
	 * 日期字符串格式yyyy-MM-dd HH:mm
	 */
	public static final String DATE_FORMAT_HM = "yyyy-MM-dd HH:mm";
	/**
	 * 日期字符串格式yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATE_FORMAT_HMS = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_FORMAT_HHMMSS = "HH:mm:ss";

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 将给定日期按照默认日期格式解析成字符串
	 *
	 * @param date
	 *            给定日期
	 * @return 解析后的日期字符串
	 */
	public static String convertDateToString(Date date) {
		if (date != null) {
			return convertDateToString(date, DATE_FORMAT);
		} else {
			return "";
		}
	}

	/**
	 * 将给定日期按照给定日期格式解析成字符串
	 *
	 * @param date
	 *            给定日期
	 * @param format
	 *            给定日期格式
	 * @return 解析后的日期字符串
	 */
	public static String convertDateToString(Date date, String format) {
		if (date == null) {
			return "";
		}
		if (StringUtil.isEmpty(format)) {
			format = DATE_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String getCurrentYearMonth() {
		return convertDateToString(new Date(), DATE_FORMAT_YYMM);
	}
	
	public static String getCurrentYYMMDD() {
		return convertDateToString(new Date(), DATE_FORMAT_YYMMDD);
	}

	/**
	 * date和今天比较
	 * 
	 * @param date
	 * @return 0 < result :大于今天, 0:等于今天, 0 > result :小于今天
	 */
	public static int compareWithToday(Date date) {
		String targetDay = convertDateToString(date);
		String today = convertDateToString(new Date());

		return targetDay.compareTo(today);
	}

	/**
	 * 比较 d1 和 d2
	 * 
	 * @param d1
	 * @param d2
	 * @param format
	 * @return d1 < d2 -> -1, d1 = d2 -> 0, d1 > d2 -> 1s
	 */
	public static int compareDate(Date d1, Date d2, String format) {
		return convertDateToString(d1, format).compareTo(
				convertDateToString(d2, format));
	}

	/**
	 * 获取未来futureNo天的日期
	 * 
	 * @param futureNo
	 * @return 返回:String[], 元素格式:yyyy-mm-dd
	 */
	public static String[] getDayInFuture(int futureNo) {
		return getDayInFuture(new Date(), futureNo, DATE_FORMAT);
	}

	/**
	 * 获取从date开始,未来futureNo天的日期
	 * 
	 * @param date
	 * @param futureNo
	 * @param format
	 *            支持yyyy-MM-dd和mm-dd, 默认yyyy-MM-dd
	 * @return 返回:String[], 元素格式:yyyy-MM-dd
	 */
	public static String[] getDayInFuture(Date date, int futureNo, String format) {
		if (futureNo <= 0) {
			return null;
		}

		Calendar now = Calendar.getInstance();
		if (date != null) {
			now.setTime(date);
		}

		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_MONTH);
		String monthStr = month < 10 ? "0" + month : month + "";
		String dayStr = day < 10 ? "0" + day : day + "";

		String[] dayArr = new String[futureNo];
		if (StringUtil.isEqual(DATE_FORMAT_MM_DD, format)) {
			dayArr[0] = monthStr + "-" + dayStr;
		} else {
			dayArr[0] = year + "-" + monthStr + "-" + dayStr;
		}

		for (int i = 1; i < futureNo; i++) {
			now.add(Calendar.DAY_OF_MONTH, 1);
			year = now.get(Calendar.YEAR);
			month = now.get(Calendar.MONTH) + 1;
			day = now.get(Calendar.DAY_OF_MONTH);
			monthStr = month < 10 ? "0" + month : month + "";
			dayStr = day < 10 ? "0" + day : day + "";

			if (StringUtil.isEqual(DATE_FORMAT_MM_DD, format)) {
				dayArr[i] = monthStr + "-" + dayStr;
			} else {
				dayArr[i] = year + "-" + monthStr + "-" + dayStr;
			}
		}

		return dayArr;
	}

	/**
	 * 获取当前年份
	 * 
	 * @return
	 */
	public static String getCurrentYear() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.YEAR) + "";
	}

	/**
	 * 取得昨天的Date
	 * 
	 * @return
	 */
	public static Date getYesterday() {
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(GregorianCalendar.DATE, -1);
		return cal.getTime();
	}

	/***
	 * i 天前的Date
	 * 
	 * @param i
	 * @return
	 */
	public static Date getFewDaysAgo(int i) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(GregorianCalendar.DATE, -i);
		return cal.getTime();
	}

	/**
	 * 获取date i天后的日期.
	 * 
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date getNextDay(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, i);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 将一个字符串转换为日期 sai.deng on 2013-12-23 下午2:45:54
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date toDate(String dateStr, String format) {
		if (dateStr == null || "".equals(dateStr.trim())) {
			return null;
		}
		SimpleDateFormat f = new SimpleDateFormat(format);
		try {
			return f.parse(dateStr);
		} catch (ParseException e) {
		}
		return null;
	}

	public static Timestamp DateToTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	public static Timestamp getNow() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static long getCurrentTime() {
		return new Date().getTime();
	}

	public static Date getToday() {
		return new Date();
	}

	/**
	 * 将一个字符串转换为日期 sai.deng on 2013-12-23 下午2:46:45
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date toDate(String dateStr) {
		return toDate(dateStr, "yyyy-MM-dd");
	}

	/**
	 * 计算end - start相差的天数
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long differenceDay(Date start, Date end) {
		return differenceDay(convertDateToString(start),
				convertDateToString(end));
	}

	/**
	 * 计算endEateStr - startDateStr相差的天数
	 * 
	 * @param startDateStr
	 * @param endEateStr
	 * @return
	 */
	public static long differenceDay(String startDateStr, String endEateStr) {
		Date start = toDate(startDateStr);
		Date end = toDate(endEateStr);
		long time = end.getTime() - start.getTime();

		return time / (24 * 60 * 60 * 1000);
	}

	/**
	 * 计算endEateStr - startDateStr相差的天数,排除周末
	 * 
	 * @param startDateStr
	 * @param endEateStr
	 * @return
	 */
	public static long differenceWorkDay(Date start, Date end) {
		long time = end.getTime() - start.getTime();
		long days = time / (24 * 60 * 60 * 1000);
		long startTime = start.getTime();
		while (startTime < end.getTime()) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(startTime);
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
					|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				days--;
			}
			startTime += 24 * 60 * 60 * 1000;
		}
		return days;
	}

	public static Timestamp convert2Timestamp(Date date) {

		return new Timestamp(date.getTime());
	}

	/**
	 * 
	 * 功能说明：获取两个时间之间相差的月份数组
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 两个时间的相差的月份数组
	 */
	@SuppressWarnings("deprecation")
	static public String[] getAllMonths(String start, String end) {
		List<String> months = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		Date d1 = DateUtil.toDate(start);
		Date d2 = DateUtil.toDate(end);
		c1.set(d1.getYear() + 1900, d1.getMonth(), 1);
		c2.set(d2.getYear() + 1900, d2.getMonth(), 1);

		while (c1.compareTo(c2) <= 0) {
			Date ss = c1.getTime();
			String str = sdf.format(ss);
			months.add(str);
			c1.add(Calendar.MONTH, 1);// 开始日期加一个月直到等于结束日期为止
		}

		String[] str = new String[months.size()];
		for (int i = 0; i < months.size(); i++) {
			str[i] = months.get(i);
		}
		return str;
	}

	/**
	 * 对指定的日期进行增加或减少天数,正数为增加，负数为减少，例如：-1为往前推1天
	 * 
	 * @param dt
	 * @param day
	 * @return
	 */
	public static Date addDay(Date dt, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		calendar.add(Calendar.DAY_OF_YEAR, day);
		return calendar.getTime();
	}

	/**
	 * 将一个字符串转换为日期（导入excel专用） sai.deng on 2013-12-23 下午2:45:54
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date toDateX(String dateStr, String format) {
		DateFormat df = null;
		try {
			if (Pattern.matches("^[0-9]{2,4}-[0-9]{1,2}-[0-9]{1,2}$", dateStr)) {
				df = new SimpleDateFormat(format);
			} else {
				format = "EEE MMM dd HH:mm:ss zzz yyyy";
				df = new SimpleDateFormat(format, Locale.US);
			}
			return df.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <p>
	 * Description: 返回天、时、分时间差<br>
	 * Date d1=sdf.parse("2016-1-7 12:00");<br>
	 * Date d2=sdf.parse("2016-1-6 9:10");<br>
	 * 相差：1天 2小时 50分
	 * </p>
	 * 
	 * @author xiong_xyu
	 * @date 2016年1月6日
	 */
	public static String timeDifference(Date date1, Date date2, String format) {

		String returnStr = "";
		try {
			long milliseconds = (date1.getTime() - date2.getTime()) / 1000;
			int n = Integer.parseInt(milliseconds + "");
			int mm = n / 60 % 60;
			int hh = n / 60 / 60 % 24;
			int dd = n / 60 / 60 / 24;
			if (format.equals(DateUtil.DATE_FORMAT)) {
				returnStr = dd + "天";
			} else if (format.equals(DateUtil.DATE_FORMAT_H)) {
				returnStr = dd + "天" + hh + "小时";
			} else if (format.equals(DateUtil.DATE_FORMAT_HM)) {
				returnStr = dd + "天" + hh + "小时" + mm + "分";
			}
		} catch (NumberFormatException e) {
			returnStr = null;
			e.printStackTrace();
		}
		return returnStr;
	}

	/**
	 * 获取当前时间毫秒
	 * 
	 * @return
	 */
	public static long getCurrentTimeInMillis() {
		Calendar c = new GregorianCalendar();
		return c.getTimeInMillis();
	}

	/**
	 * 获取日期的年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getDateYear(Timestamp date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取日期月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getDateMonth(Timestamp date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		return c.get(Calendar.MONTH);
	}

	/**
	 * 获取日期月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getDateQuater(Timestamp date) {
		int season = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		switch (month) {
		case Calendar.JANUARY:
		case Calendar.FEBRUARY:
		case Calendar.MARCH:
			season = 1;
			break;
		case Calendar.APRIL:
		case Calendar.MAY:
		case Calendar.JUNE:
			season = 2;
			break;
		case Calendar.JULY:
		case Calendar.AUGUST:
		case Calendar.SEPTEMBER:
			season = 3;
			break;
		case Calendar.OCTOBER:
		case Calendar.NOVEMBER:
		case Calendar.DECEMBER:
			season = 4;
			break;
		default:
			break;
		}
		return season;
	}

	/**
	 * 获取当前年
	 */
	public static int getCurrentYearInt() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取当前月
	 */
	public static int getCurrentMonth() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	public static int getCurrentDate() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.DATE);
	}

	/**
	 * 获取当前小时
	 * 
	 * @return
	 */
	public static int getCurrentHour() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取当前分钟
	 * 
	 * @return
	 */
	public static int getCurrentMinute() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 获取当前秒
	 * 
	 * @return
	 */
	public static int getCurrentSecond() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.SECOND);
	}

	/**
	 * 获取当前季度
	 */
	public static int getCurrentQuarter() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.MONTH) / 3 + 1;
	}

	/**
	 * 获取上个月对应的年份
	 * 
	 * @return
	 */
	public static int getYearOfLastMonth() {
		return getCurrentMonth() - 1 == 0 ? getCurrentYearInt() - 1
				: getCurrentYearInt();
	}

	/**
	 * 获取上个季度对应的年份
	 * 
	 * @return
	 */
	public static int getYearOfLastQuarter() {
		return getCurrentQuarter() - 1 == 0 ? getCurrentYearInt() - 1
				: getCurrentYearInt();
	}

	/**
	 * 获取上个月
	 * 
	 * @return
	 */
	public static int getLastMonth() {
		return getCurrentMonth() - 1 == 0 ? 12 : getCurrentMonth() - 1;
	}

	/**
	 * 获取上个季度
	 * 
	 * @return
	 */
	public static int getLastQuarter() {
		return getCurrentQuarter() - 1 == 0 ? 4 : getCurrentQuarter() - 1;
	}

	/**
	 * 获取上一年
	 * 
	 * @return
	 */
	public static int getLastYear() {
		return getCurrentYearInt() - 1;
	}

	/**
	 * 获取指定时间的前几月或后几月
	 * 
	 * @param date
	 * @param month
	 *            待加减的月数
	 * @return
	 */
	public static java.util.Date addMonth(java.util.Date date, int month) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.MONTH, month);
		return rightNow.getTime();
	}

	public static Timestamp[] getBeginAndEndTimes_year(int addYears)
			throws Exception {
		return getBeginAndEndTimes_year(addYears, new Date());
	}

	/**
	 * 取得年的开始时间和结束时间
	 * 
	 * @param addYears
	 *            相对年份。例如：0为当前年，1为下一年，-1为上一年
	 * @return 开始时间和结束时间的数组
	 * @throws Exception
	 */
	public static Timestamp[] getBeginAndEndTimes_year(int addYears, Date date)
			throws Exception {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		c.set(Calendar.DATE, 1);
		c.set(Calendar.MONTH, 0);
		c.add(Calendar.YEAR, addYears);
		Date beginTime = sdf.parse(sdf.format(c.getTime()));
		c.add(Calendar.YEAR, 1);
		Date endTime = sdf.parse(sdf.format(c.getTime()));
		return new Timestamp[] { new Timestamp(beginTime.getTime()),
				new Timestamp(endTime.getTime()) };
	}

	/**
	 * 取得半年的开始时间和结束时间
	 * 
	 * @param addHalfYears
	 *            相对半年。例如：0为当前的半年，1为下一个半年，-1为上一个半年
	 * @return 开始时间和结束时间的数组
	 * @throws Exception
	 */
	public static Timestamp[] getBeginAndEndTimes_halfYear(int addHalfYears,
			Date date) throws Exception {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		Date beginTime = null;
		Date endTime = null;
		if (c.get(Calendar.MONTH) <= 6) {
			c.set(Calendar.MONTH, 0);
		} else {
			c.set(Calendar.MONTH, 6);
		}
		c.set(Calendar.DATE, 1);
		c.add(Calendar.MONTH, 6 * addHalfYears);
		beginTime = sdf.parse(sdf.format(c.getTime()));
		c.add(Calendar.MONTH, 6);
		endTime = sdf.parse(sdf.format(c.getTime()));
		return new Timestamp[] { new Timestamp(beginTime.getTime()),
				new Timestamp(endTime.getTime()) };
	}

	public static Timestamp[] getBeginAndEndTimes_month(int addMonths)
			throws Exception {
		return getBeginAndEndTimes_month(addMonths, new Date());
	}

	/**
	 * 取得月的开始时间和结束时间,以传入的时间作为基准
	 * 
	 * @param addMonths
	 *            相对月份。例如：0为当前月份，1为下月，-1为上月
	 * @param date
	 *            传入的时间
	 * @return 开始时间和结束时间的数组
	 * @throws Exception
	 */
	public static Timestamp[] getBeginAndEndTimes_month(int addMonths, Date date)
			throws Exception {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		Date beginTime = null;
		Date endTime = null;
		c.add(Calendar.MONTH, addMonths);
		c.set(Calendar.DATE, 1);
		beginTime = sdf.parse(sdf.format(c.getTime()));
		c.add(Calendar.MONTH, 1);
		endTime = sdf.parse(sdf.format(c.getTime()));
		return new Timestamp[] { new Timestamp(beginTime.getTime()),
				new Timestamp(endTime.getTime()) };
	}

	public static Timestamp[] getBeginAndEndTimes_quarter(int addQuarters)
			throws Exception {
		return getBeginAndEndTimes_quarter(addQuarters, new Date());
	}

	/**
	 * 取得季度的开始时间和结束时间
	 * 
	 * @param addQuarters
	 *            相对季度。例如：0为当前季度，1为下季度，-1为上季度
	 * @return 开始时间和结束时间的数组
	 * @throws Exception
	 */
	public static Timestamp[] getBeginAndEndTimes_quarter(int addQuarters,
			Date date) throws Exception {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		} else {
			c.setTime(DateUtil.getNow());
		}

		int currentM = c.get(Calendar.MONTH);

		c.set(Calendar.MONTH, currentM / 3 * 3);
		Date bt = c.getTime();
		c.set(Calendar.MONTH, currentM / 3 * 3 + 3);
		Date et = c.getTime();

		c.setTime(bt);
		c.add(Calendar.MONTH, addQuarters * 3);
		c.set(Calendar.DAY_OF_MONTH, 1);
		Date startDate = c.getTime();
		c.setTime(et);
		c.add(Calendar.MONTH, addQuarters * 3);
		c.set(Calendar.DAY_OF_MONTH, 1);
		Date endDate = c.getTime();
		return new Timestamp[] { new Timestamp(startDate.getTime()),
				new Timestamp(endDate.getTime()) };
	}

	/**
	 * 获取当前时间是一周的第几天
	 * 
	 * @return
	 */
	public static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			return -6;
		} else {
			return 2 - dayOfWeek;
		}
	}

	/**
	 * 取得周的开始时间和结束时间
	 * 
	 * @param addweek
	 *            相对周。例如：0为当前周，1为下周，-1为上周
	 * @return 开始时间和结束时间的数组
	 * @throws Exception
	 */
	public static Timestamp[] getBeginAndEndTimes_week(int addweek)
			throws Exception {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + addweek * 7);
		Date beginTime = currentDate.getTime();
		currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + addweek * 7 + 6);
		Date endTime = currentDate.getTime();
		return new Timestamp[] { new Timestamp(beginTime.getTime()),
				new Timestamp(endTime.getTime()) };
	}

	/**
	 * 
	 * @param addMonths
	 * @return
	 * @throws Exception
	 */
	public static Timestamp[] getBeginAndEndTimes_day(int addDays)
			throws Exception {
		Calendar c = Calendar.getInstance();
		Date beginTime = null;
		Date endTime = null;
		c.add(Calendar.DATE, addDays);
		beginTime = sdf.parse(sdf.format(c.getTime()));
		c.add(Calendar.DATE, 1);
		endTime = sdf.parse(sdf.format(c.getTime()));
		return new Timestamp[] { new Timestamp(beginTime.getTime()),
				new Timestamp(endTime.getTime()) };
	}

	/**
	 * 检查给定的日期是否在两个日期中间
	 *
	 * @param current
	 *            compare date
	 * @param min
	 *            min date
	 * @param max
	 *            max date
	 * @return if between min date and max date, then return true.
	 */
	public static boolean between(Date current, Date min, Date max) {
		return current.after(min) && current.before(max);
	}

	/**
	 * 获取前几个月的开始结束时间
	 * 
	 * @param addMonth
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp getMothBefore(int addMonth, int day)
			throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, addMonth);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return new Timestamp(sdf.parse(sdf.format(calendar.getTime()))
				.getTime());
	}

	/**
	 * 获取Timestamp
	 * 
	 * @param str
	 * @return
	 */
	public static Timestamp getTimestamp(String str) {
		if (StringUtil.isEmpty(str)) {
			return null;
		}

		// yyyy-MM-dd HH:mm:ss 格式
		if (str.length() > 10) {
			if (str.length() == 13) {
				return Timestamp.valueOf(str + ":00:00");
			} else if (str.length() == 16) {
				return Timestamp.valueOf(str + ":00");
			} else {
				return Timestamp.valueOf(str);
			}
		}
		// yyyy
		else if (str.length() == 4) {
			return Timestamp.valueOf(str + "-01-01 00:00:00");
		}
		// yyyy-mm
		else if (str.length() == 7) {
			return Timestamp.valueOf(str + "-01 00:00:00");
		} else {
			return Timestamp.valueOf(str + " 00:00:00");
		}
	}

	/**
	 * 将一个字符串转换为日期 sai.deng on 2013-12-23 下午2:45:54
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date toDateByImport(String dateStr, String format) {
		DateFormat df = null;
		try {
			if (Pattern.matches("^[0-9]{2,4}-[0-9]{1,2}-[0-9]{1,2}$", dateStr)) {
				df = new SimpleDateFormat(format);
			} else {
				format = "EEE MMM dd HH:mm:ss zzz yyyy";
				df = new SimpleDateFormat(format, Locale.US);
			}
			return df.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将一个字符串转换为日期 sai.deng on 2013-12-23 下午2:46:45
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date toDateByImport(String dateStr) {
		return toDateByImport(dateStr, DATE_FORMAT);
	}

	public static String getYearByDate(Date date) {
		return convertDateToString(date, "yyyy");
	}

	/**
	 * 往前推nWeek周的日期
	 * 
	 * @author sai.deng on 2016年3月25日 下午3:53:47
	 * @param nWeek
	 * @return
	 */
	public static Date getBeforeDateByNWeek(int nWeek) {
		Date dt = getMondayOfThisWeek();
		dt = addDay(dt, -7 * (nWeek - 1));
		return ConvertUtil.toDate(ConvertUtil.toString(dt));
	}

	/**
	 * 得到本周周一
	 * 
	 * @author sai.deng on 2016年3月25日 下午2:44:48
	 * @return
	 */
	public static Date getMondayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		return c.getTime();
	}

	/**
	 * 设置当前是一年当中的第多少周
	 * 
	 * @author sai.deng on 2016年3月28日 下午1:06:09
	 * @return
	 */
	public static int getWeekOfYear() {
		return getWeekOfYear(new Date());
	}

	// 获取当前时间所在年的周数
	public static int getWeekOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY); // 设置在一年中第一个星期所需最少天数，默认为1
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	// 获取当前时间所在年的最大周数
	public static int getMaxWeekNumOfYear(int year) {
		Calendar c = Calendar.getInstance();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

		return getWeekOfYear(c.getTime());
	}

	/**
	 * 得到去年的年份
	 * 
	 * @author sai.deng on 2016年3月28日 下午1:52:53
	 * @return
	 */
	public static int lastYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR, -1);
		int year = c.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 获取时间 前/后 几个月
	 * 
	 * @author xiong_xyu
	 * @date 2016年4月4日
	 */
	public static String getDateOfYearMonth(int i, DateFormat sdf) {
		Calendar c = Calendar.getInstance();
		String now = null;
		try {
			c.set(Calendar.MONTH, i + c.get(Calendar.MONTH) + 1);
			now = sdf.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}
	
	
	
	//日期相加排除周末
	 public static Date addDateByWorkDay(Date sourceDate,int adddays){
		 Calendar calendar = Calendar.getInstance();
		 if(sourceDate ==null){
			return null;
		 }
		 calendar.setTime(sourceDate);
//	        Calendar result = null;
	 	int addSub=0;
	 	if(adddays>0)
	 		addSub=1;
	 	if(adddays<0)
	 		addSub= -1;
	 	if(adddays == 0)
	 		return sourceDate;
	 	adddays=Math.abs(adddays);
        Boolean holidayFlag = false;
        for (int i = 0; i < adddays; i++){
            //把源日期加一天
        	calendar.add(Calendar.DAY_OF_MONTH, addSub);
            holidayFlag =checkHoliday(calendar);
            if(holidayFlag)
            {
               i--;
            }
        }
        return calendar.getTime();
    }
	 /**
	  * 验证是否节假日（可以手动控制）
	  * @param src
	  * @return
	  */
	 public static boolean checkHoliday(Calendar src){
		 	List <Calendar> holidayList = new ArrayList<Calendar>();
	        boolean result = false;
	       //添加节假日
	        initHolidayList(holidayList);
	        //先检查是否是周六周日(有些国家是周五周六)
	        if (src.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
	                || src.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
	        {
	            return true;
	        }
	        for (Calendar c : holidayList)
	        {
	            if (src.get(Calendar.MONTH) == c.get(Calendar.MONTH)
	                    && src.get(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH))
	            {
	                result = true;
	            }
	        }
	        return result;
	    }
 	//根据需求手动添加的节假日
    private static void initHolidayList(List <Calendar> holidayList){
        //五一劳动节
        /*Calendar may1 = Calendar.getInstance();
        may1.set(Calendar.MONTH,Calendar.MAY);
        may1.set(Calendar.DAY_OF_MONTH,1);
        holidayList.add(may1);*/
 
       /* Calendar may2 = Calendar.getInstance();
        may2.set(Calendar.MONTH,Calendar.MAY);
        may2.set(Calendar.DAY_OF_MONTH,2);
        holidayList.add(may2);*/
 
       /* Calendar may3 = Calendar.getInstance();
        may3.set(Calendar.MONTH,Calendar.MAY);
        may3.set(Calendar.DAY_OF_MONTH,3);
        holidayList.add(may3);*/
    }
	
	public static void main(String[] args) {
		/*Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		Calendar newDay = addDateByWorkDay(cal, -3);
		Date time = newDay.getTime();
		String addNewTime = convertDateToString(time);
		System.out.println(addNewTime);*/
		/*try {
			Calendar c = Calendar.getInstance();

			for (int i = 0; i < 12; i++) {
				c.set(Calendar.MONTH, i);
				Timestamp[] data_arry = getBeginAndEndTimes_quarter(-1);
				System.out.println(DateUtil.convertDateToString(data_arry[0]));
				System.out.println(DateUtil.convertDateToString(data_arry[1]));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
		System.out.println(getCurrentYearMonth());*/
		// System.out.println(DateUtil.getAllMonths("2014-01-09",
		// "2014-01-09").length + "--" + DateUtil.getAllMonths("2014-01-30",
		// "2014-01-30")[0]);
		// System.out.println(DateUtil.getAllMonths("2014-01-1",
		// "2014-02-10").length + "--" + DateUtil.getAllMonths("2014-01-30",
		// "2014-02-10")[1]);
		/*
		 * String[] month = DateUtil.getAllMonths("2014-01-01", "2015-02-01");
		 * for (String string : month) { System.out.println(string); }
		 */
	}

}
