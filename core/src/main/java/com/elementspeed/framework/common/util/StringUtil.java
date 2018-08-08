package com.elementspeed.framework.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * 
 */
public class StringUtil extends org.apache.commons.lang3.StringUtils {

	/**
	 * 判断字符串数组是否为空
	 *
	 * @param strs
	 * @return 不空返回true
	 */
	final public static boolean isNotEmpty(String[] strs) {
		return strs != null && strs.length != 0;
	}
	
	/**
	 *
	 * @param strs
	 * @return
	 */
	final public static boolean isEmpty(String[] strs) {
		return !isNotEmpty(strs);
	}
	
	/**
	 * 判断两个字符串是否相等
	 *
	 * @param str1
	 * @param str2
	 * @return 相等返回true
	 */
	final public static boolean isEqual(String str1, String str2) {
		if (isEmpty(str1) && isEmpty(str2)) {
			return true;
		} else if (isEmpty(str1) || isEmpty(str2)) {
			return false;
		} else {
			return str1.trim().equals(str2.trim());
		}
	}

	final public static boolean isNotEqual(String str1, String str2) {
		return !isEqual(str1, str2);
	}
	

	/**
	 * 判断两个字符串是否相等, 不区分大小写
	 *
	 * @param str1
	 * @param str2
	 * @return 相等返回true
	 */
	final public static boolean isEqualIgnoreCase(String str1, String str2) {
		if (isEmpty(str1) && isEmpty(str2)) {
			return true;
		} else if (isEmpty(str1) || isEmpty(str2)) {
			return false;
		} else {
			return str1.trim().equalsIgnoreCase(str2.trim());
		}
	}
	
	/**
	 * 移除最后n个字符
	 * @param str
	 * @param n
	 * @return
	 */
	final public static String removeLast(String str, int n) {
		if(isEmpty(str)) {
			return str;
		} else if(n >= str.length()) {
			return "";
		}
		
		return str.substring(0, str.length() - n);
	}
	
	/**
	 * 移除最后一个字符
	 * @param str
	 * @return
	 */
	final public static String removeLast(String str) {
		if(isEmpty(str)) {
			return str;
		}
		
		return str.substring(0, str.length() - 1);
	}
	
	/**
	 * 去除所有空白,包括中间的
	 */
	final public static String removeAllSpace(String str) {
		if (isEmpty(str)) {
			return "";
		}

		return str.replaceAll("\\s+", "");
	}
	
	/**
	 * 补齐字符串<br>
	 * <p>
	 * 例: 
	 * appendChar("21000", "-", 10);
	 * <br>
	 * 结果: "21000-----"
	 * @param source
	 * @param length	字符串的占位长度
	 * @param appendStr	补齐的字符
	 * @return
	 */
	final public static String rightPadStr(String source, int length, String appendStr) {
		int strLength = 0;
		if(isNotEmpty(source)) {
			strLength = source.length();
		}
		
		if(length - strLength > 0) {
			return source + repeat(appendStr, length - strLength);
		} else {
			return source;
		}
	}
	
	/**
	 * datagrid field字段与数据库column匹配
	 * @param str
	 * @return
	 */
	final public static String field2Column(String str) {
		if(StringUtil.isEmpty(str)) {
			return "";
		}
		return str.replaceAll("[A-Z]", "_$0").toUpperCase();
	}
	

	/**
	 * 解码
	 *
	 * @param str
	 * @return
	 */
	final public static String decode(String str) {
		try {
			return isNotEmpty(str) ? URLDecoder.decode(str, "UTF-8") : "";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
	

	/**
	 * str.indexOf(subString) >= 0
	 * @param str
	 * @param subString
	 * @return
	 */
	final public static boolean hasSubString(String str, String subString) {
		if(isEmpty(str) || isEmpty(subString)) {
			return false;
		}

		return str.indexOf(subString) >= 0;
	}
	
	/**
	 * 是否为纯数字
	 * @author sai.deng on 2015年12月1日 上午9:31:22
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (isEmpty(str)) {
			return false;
		}
		
		if (str.matches("\\d*")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 是否为Double
	 * @author sai.deng on 2015年12月1日 上午9:31:06
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		if (isEmpty(str)) {
			return false;
		}
		
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String[] listToArray(List<String> emailList) {
		String[] userEmails = new String[emailList.size()];
		int index = 0;
		for (String email : emailList) {
			userEmails[index++] = email;
		}
		return userEmails;
	}

	/*
	 * 过滤json敏感字符串
	 */
	public static String jsonCharFilter(String jsonStr) {
		if(StringUtil.isEmpty(jsonStr) || jsonStr.length() ==0 ){
			return "";
		}
		if(jsonStr.indexOf("'")>0){
			jsonStr = jsonStr.replaceAll("'", "\\\\'");
		}
		if(jsonStr.indexOf("\r")>0 || jsonStr.indexOf("\n")>0){
			jsonStr = jsonStr.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
		}
		return jsonStr;
	}
	
	/**
	 * 将首字母变为大写
	 *
	 * @param str
	 * @return
	 */
	final public static String toUperFirstChar(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	final public static String toString(Object obj) {
		if(obj==null){
			return "";
		}
		
		return obj.toString();
	}
	
	
	public static void main(String[] args) {
		System.out.println(rightPadStr("21000", 10, "&nbsp;"));
		System.out.println(StringUtil.split("1111,2222,3333", ",").length);
		System.out.println(StringUtil.split("", ",").length);
		System.out.println(StringUtil.field2Column("orgCode,organization.orgName,cmpName"));
	}

}
