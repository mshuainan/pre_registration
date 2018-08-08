package com.elementspeed.framework.common.sn;

import java.util.List;

import com.elementspeed.framework.base.exception.BOException;
import com.elementspeed.framework.common.sn.generator.IGenerator;

/**
 * SRM业务单号生成引擎
 *
 */
public abstract class SNGeneratorEngine {
	//单号分隔符, 如果一个单号需要多个IGenerator, 则每个IGenerator生成的单号间以splitStr分隔
//	private final String SPLITSTR = "-";

	
	/**
	 * SRM业务单号生成器集合, 每个单号可由多个生成器共同完成
	 * @return
	 */
	abstract public List<IGenerator> getGeneratorList();
	
	/**
	 * 生成流水号：分发给各个子序列 Generator 生成
	 * @return
	 * @throws BOException 
	 */
	public String generate() throws BOException {
//		List<String> snList = new LinkedList<String>();
//		List<IGenerator> generatorList = getGeneratorList();
//		for(IGenerator generator : generatorList) {
//			snList.add(generator.generate());
//		}
//		
//		return ContainerUtil.convert2String(snList, SPLITSTR);
		
		String result = "";
		List<IGenerator> generatorList = getGeneratorList();
		for(IGenerator generator : generatorList) {
			result += generator.generate();
		}
		
		return result;
	}
}
