package com.elementspeed.system.usermgt.service.impl.sn;

import java.util.ArrayList;
import java.util.List;

import com.elementspeed.framework.common.sn.SNGeneratorEngine;
import com.elementspeed.framework.common.sn.generator.IGenerator;
import com.elementspeed.framework.common.util.SpringContextUtil;

/**
 * 组织序号生成器
 *
 */
public class UserSnGeneratorEngine extends SNGeneratorEngine {

	@Override
	public List<IGenerator> getGeneratorList() {
		List<IGenerator> result = new ArrayList<IGenerator>();
		result.add(SpringContextUtil.getBean("userSnGenerator", IGenerator.class));
		return result;
	}
}
