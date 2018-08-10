package com.elementspeed.framework.common.sn.generator.service;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import com.elementspeed.common.datadictionary.DataDictionary;
import com.elementspeed.framework.base.dao.BaseEntity;
import com.elementspeed.framework.base.exception.BOException;
import com.elementspeed.framework.common.sn.generator.dto.SnGeneratorDto;
import com.elementspeed.framework.common.sn.generator.service.impl.SnGeneratorService;
import com.elementspeed.framework.common.util.SpringContextUtil;
import com.elementspeed.framework.common.util.StringUtil;
import com.elementspeed.mapper.system.dao.SysSeriaNoDao;
import com.elementspeed.mapper.system.entity.SysSeriaNo;

/**
 * @ClassName: SnGeneratorServiceImpl
 * @Description: TODO(新的单号生成器Impl)
 * @author masn
 * @date 2018年8月10日 下午12:29:04
 */
@Service
public class SnGeneratorServiceImpl implements SnGeneratorService {
	@Resource
	protected SysSeriaNoDao sysSeriaNoDao;

	@Override
	public String generate(SnGeneratorDto snGeneratorDto) throws BOException {
		synchronized (SnGeneratorServiceImpl.class) {
			try {
				int snNo = getMinSn(snGeneratorDto);
				String currentPeriod = snGeneratorDto.getCurrentPeriod() == null?"":snGeneratorDto.getCurrentPeriod();
				SysSeriaNo sn = sysSeriaNoDao.getSn(snGeneratorDto.getTableName());			
				//第一次使用流水号
				if (sn == null) {
					insert(snGeneratorDto, snNo, currentPeriod);
				}
				//本周期内第一次使用流水号
				else if (validateCurrentPeriod(currentPeriod, sn)) {
					update(snNo, currentPeriod, sn);
				}
				//使用同一周期内的流水号
				else {
					snNo = sn.getSerialNo() + 1;
					validateSn(snNo, snGeneratorDto);
					sysSeriaNoDao.increment(sn.getId());
				}
				//前缀 + 当前周期 + 流水号
				String prefix = snGeneratorDto.getPrefix() == null?"":snGeneratorDto.getPrefix();
				return prefix + currentPeriod + getSnNo(snNo, snGeneratorDto);	
			} catch (BOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	private void update(int snNo, String currentPeriod, SysSeriaNo sn) {
		sn.setSerialNo(snNo);
		if (RequestContextHolder.getRequestAttributes() != null) {
			sn.setLastModifyInfo();
		} else {
			sn.setLastModifyTime(new Date());
			sn.setLastModifyUserId(DataDictionary.SYSTEM_USER_ID);
		}
		sn.setBeginDate(currentPeriod);
		sysSeriaNoDao.update(sn);
	}

	private void insert(SnGeneratorDto snGeneratorDto, int snNo,
			String currentPeriod) {
		SysSeriaNo seriaNo = new SysSeriaNo();
		if (RequestContextHolder.getRequestAttributes() != null) {
			seriaNo.setCreateInfo();
		} else {
			seriaNo.setId(UUID.randomUUID().toString());
			seriaNo.setCreateTime(new Date());
			seriaNo.setLastModifyTime(new Date());
			seriaNo.setCreateUserId(DataDictionary.SYSTEM_USER_ID);
			seriaNo.setLastModifyUserId(DataDictionary.SYSTEM_USER_ID);
			seriaNo.setDeleteFlag(BaseEntity.DELETE_FLAG_FALSE);
		}
		seriaNo.setTableName(snGeneratorDto.getTableName());
		seriaNo.setSerialNo(snNo);
		seriaNo.setBeginDate(currentPeriod);
		sysSeriaNoDao.create(seriaNo);
	}
	
	/**
	 * 获取流水号.
	 * 默认实现：流水号长度是getMaxSn().length, 长度不足的流水号前以0填充
	 * @param snNo	流水号数字
	 * @return
	 */
	protected String getSnNo(int snNo, SnGeneratorDto snGeneratorDto) {
		int maxLength = (snGeneratorDto.getMaxSn() + "").length();
		int currentSnLength = (snNo + "").length();
		
		String result = snNo + "";
		for(int i = 1; i <= maxLength - currentSnLength; i++) {
			result = "0" + result;
		}
		
		return result;
	}
	
	/**
	 * 流水号校验
	 * @param snNo
	 * @param snGeneratorDto
	 * @throws BOException
	 */
	protected void validateSn(int snNo, SnGeneratorDto snGeneratorDto) throws BOException {
		//校验本周期流水号是否溢出
		if (snNo > snGeneratorDto.getMaxSn())
			throw new BOException(SpringContextUtil.getMessage("common.serial.overflow")
					+ snGeneratorDto.getCurrentPeriod() + SpringContextUtil.getMessage("common.max.serial")
					+ snGeneratorDto.getMaxSn());
	}

	private boolean validateCurrentPeriod(String currentPeriod, SysSeriaNo sn) {
		return StringUtil.isNotEqual(currentPeriod, sn.getBeginDate());
	}

	private int getMinSn(SnGeneratorDto snGeneratorDto) {
		return snGeneratorDto.getMinSn() == null ? 1 : snGeneratorDto.getMinSn().intValue();
	}
	
}