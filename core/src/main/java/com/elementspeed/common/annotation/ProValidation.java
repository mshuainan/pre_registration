package com.elementspeed.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 基本属性校验
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ProValidation {	
	String text();						//属性含义
	boolean notEmpty() default false;	//是否非空
	int maxLength() default 0;			//最大长度
}
