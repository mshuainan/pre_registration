package com.elementspeed.framework.common.sn.generator.impl;

import java.util.Date;

import com.elementspeed.framework.base.exception.BOException;
import com.elementspeed.framework.common.sn.generator.IGenerator;
import com.elementspeed.framework.common.util.DateUtil;

/**
 * 单号生成器(日期部分)
 *
 */
public class DateGenerator implements IGenerator {

	@Override
	public String generate() throws BOException {
		return DateUtil.convertDateToString(new Date(), DateUtil.DATE_FORMAT_YM);
	}
}
