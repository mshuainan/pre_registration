package com.elementspeed.tool.excel.util;

import java.text.DecimalFormat;

import com.elementspeed.framework.common.util.StringUtil;

public class DecimalFormatUtil {
	
	public static String format(String s) {
		try {
			double d = Double.parseDouble(s);
			java.text.DecimalFormat df = new java.text.DecimalFormat("#");
			return df.format(d);
		} catch (Exception e) {
			return s;
		}
	}
	
	/**
	 * 保留2位小数并做四舍五入，对于没有小数点的直接取整
	 * @author sai.deng on 2012-4-15
	 * @param d
	 * @return
	 */
	public static String format2(double d) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
		return df.format(d);
	}
	
	public static String format6(double d) { 
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.######");
		return df.format(d);
	}
	
	public static String format6(String s) { 
		try {
			double d = Double.parseDouble(s);
			java.text.DecimalFormat df = new java.text.DecimalFormat("#.######");
			return df.format(d);
		} catch (Exception e) {
			return s;
		}
		
	}
	
	public static String format(Number number,String pattern){
		 if(StringUtil.isEmpty(pattern)){
			 pattern = ".##";
		 }
		 DecimalFormat a = new DecimalFormat(pattern); 
		 a.applyPattern("##");
		 return a.format(number);
	}
	
	public static void main(String[] args) {
		System.out.println(format6("4"));
		System.out.println(format6("4.0"));
		System.out.println(format6("201210-SZ-001"));
		
//		System.out.println(format6(25.02));
//		System.out.println(format6(256956.3567));
//		System.out.println(format6(256956.35676));
//		System.out.println(format6(256956.356467));
//		System.out.println(format6(256956666.35677874));
	}
}
