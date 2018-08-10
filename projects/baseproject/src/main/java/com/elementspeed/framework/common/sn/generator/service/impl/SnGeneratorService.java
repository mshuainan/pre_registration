package com.elementspeed.framework.common.sn.generator.service.impl;

import com.elementspeed.framework.base.exception.BOException;
import com.elementspeed.framework.common.sn.generator.dto.SnGeneratorDto;

/**
 * @ClassName: SnGeneratorService
 * @Description: TODO(单号生成器Service)
 * @author masn
 * @date 2018年8月10日 下午12:28:51
 */
public interface SnGeneratorService {
	
	public String generate(SnGeneratorDto snGeneratorDto) throws BOException;
	
}
