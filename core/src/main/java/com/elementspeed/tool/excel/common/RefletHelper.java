package com.elementspeed.tool.excel.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * @author sai.deng
 * @date 2015年11月5日 下午11:25:28
 */
@SuppressWarnings("serial")
public class RefletHelper implements Serializable {

	/**
	 * 得到定义的字段，若当前类没有，则递归从父类找，直到找到为止
	 * @author sai.deng on 2015年11月5日 下午10:47:52
	 * @param clazz
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 */
	public static Field getDeclaredField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			Class<?> superClazz = clazz.getSuperclass();
			if (superClazz == null) {
				throw new NoSuchFieldException(e.getMessage());
			}
			return getDeclaredField(superClazz, fieldName);
		}
		return field;
	}
	
	/**
	 * 得到定义的get方法，若当前类没有，则递归从父类找，直到找到为止
	 * @author sai.deng on 2015年11月5日 下午11:21:28
	 * @param clazz
	 * @param getMethodName
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static Method getMethod(Class<?> clazz, String getMethodName) throws NoSuchMethodException {
		Method method = null;
		try {
			method = clazz.getMethod(getMethodName, new Class[] {});
		} catch (NoSuchMethodException e) {
			Class<?> superClazz = clazz.getSuperclass();
			if (superClazz == null) {
				throw new NoSuchMethodException(e.getMessage());
			}
			return getMethod(superClazz, getMethodName);
		}
		return method;
	}
	

	/**
	 * 得到定义的set方法，若当前类没有，则递归从父类找，直到找到为止
	 * @author sai.deng on 2015年11月6日 上午9:16:56
	 * @param clazz
	 * @param setMethodName
	 * @param field
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static Method setMethod(Class<?> clazz, String setMethodName, Field field) throws NoSuchMethodException {
		Method method = null;
		try {
			method = clazz.getMethod(setMethodName, field.getType());
		} catch (NoSuchMethodException e) {
			Class<?> superClazz = clazz.getSuperclass();
			if (superClazz == null) {
				throw new NoSuchMethodException(e.getMessage());
			}
			return setMethod(superClazz, setMethodName, field);
		}
		return method;
	}
	
}
