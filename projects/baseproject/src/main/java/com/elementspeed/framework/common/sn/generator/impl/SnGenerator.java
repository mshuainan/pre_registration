package com.elementspeed.framework.common.sn.generator.impl;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.web.context.request.RequestContextHolder;

import com.elementspeed.common.datadictionary.DataDictionary;
import com.elementspeed.framework.base.dao.BaseEntity;
import com.elementspeed.framework.base.exception.BOException;
import com.elementspeed.framework.common.sn.generator.IGenerator;
import com.elementspeed.framework.common.util.DateUtil;
import com.elementspeed.framework.common.util.SpringContextUtil;
import com.elementspeed.framework.common.util.StringUtil;
import com.elementspeed.mapper.system.dao.SysSeriaNoDao;
import com.elementspeed.mapper.system.entity.SysSeriaNo;

/**
 * @ClassName: SnGenerator
 * @Description: TODO( 流水号生成器)
 * @author masn
 * @date 2018年8月10日 下午12:26:36
 */
public abstract class SnGenerator implements IGenerator {
	
	@Resource
	protected SysSeriaNoDao sysSnDao;
	
	/**
	 * 流水号生成器, 采用抢号规则, 生成流水号后, 将其加1.
	 * <li>1. SRM系统默认同一周期内的流水号不能重复, 流水号从1开始, 至getMaxSn结束
	 * <li>2. 如果业务操作在生成流水号后失败, 将浪费一个流水号.
	 * <p>
	 * 各业务可根据需要覆写 getSnNo, getTableName, getPeriodFormat, getPeriodFormat方法.
	 */
	@Override
	public String generate() throws BOException {
		synchronized(SnGenerator.class) {
			int snNo = getMinSn();
			String currentPeriod = getCurrentPeriod();
			SysSeriaNo sn = sysSnDao.getSn(getTableName());
			
			//第一次使用流水号
			if(sn == null) {
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
				seriaNo.setTableName(getTableName());
				seriaNo.setSerialNo(snNo);
				seriaNo.setBeginDate(getCurrentPeriod());
				sysSnDao.create(seriaNo);
			}
			//本周期内第一次使用流水号
			else if(validateCurrentPeriod()) {
				sn.setSerialNo(snNo);
				if(RequestContextHolder.getRequestAttributes() != null) {
					sn.setLastModifyInfo();
				}else{
					sn.setLastModifyTime(new Date());
					sn.setLastModifyUserId(DataDictionary.SYSTEM_USER_ID);
				}
				sn.setBeginDate(currentPeriod);
				sysSnDao.update(sn);
			}
			//使用同一周期内的流水号
			else {
				snNo = sn.getSerialNo() + 1;
				validateSn(snNo, currentPeriod);
				sysSnDao.increment(sn.getId());
			}
			
			return getSnNo(snNo);
		}
	}

	/**
	 * 获取流水号.
	 * 默认实现：流水号长度是getMaxSn().length, 长度不足的流水号前以0填充
	 * @param snNo	流水号数字
	 * @return
	 */
	protected String getSnNo(int snNo) {
		int maxLength = (getMaxSn() + "").length();
		int currentSnLength = (snNo + "").length();
		
		String result = snNo + "";
		for(int i = 1; i <= maxLength - currentSnLength; i++) {
			result = "0" + result;
		}
		
		return result;
	}
	
	/**
	 * 表名(表名作为业务种类的标识, 持久化到数据库)
	 * @return
	 */
	protected abstract String getTableName();
	
	/**
	 * 周期的日期格式, 默认 yyMM, 其他业务码根据需要重写该方法
	 * @return
	 */
	protected String getPeriodFormat() {
		return DateUtil.DATE_FORMAT_YYMM;
	}
	
	/**
	 * 获取一个周期内的最大流水号, 默认9999, 其他业务码根据需要重写该方法
	 * @return
	 */
	protected int getMaxSn() {
		return 9999;
	}
	
	/**
	 * 流水号校验
	 * @param snNo
	 * @param currentPeriod
	 * @throws BOException
	 */
	protected void validateSn(int snNo, String currentPeriod) throws BOException {
		//校验本周期流水号是否溢出
		if(snNo > getMaxSn()) {
			throw new BOException(SpringContextUtil.getMessage("common.serial.overflow") + currentPeriod + SpringContextUtil.getMessage("common.max.serial") + getMaxSn());
		}
	}
	
	/**
	 * 获取当前周期字符串
	 * @return
	 */
	private String getCurrentPeriod() {
		return DateUtil.convertDateToString(new Date(), getPeriodFormat());
	}
	
	
	/**
	 * 如果流水号按照周期循环则无需覆盖 否则覆盖本方法反回false即可
	 * @return
	 */
	protected boolean validateCurrentPeriod(){
		return StringUtil.isNotEqual(getCurrentPeriod(), sysSnDao.getSn(getTableName()).getBeginDate());
	}
	
	/**
	 * 起始流水号  默认为1 其他业务根据需要重写该方法
	 * @return
	 */
	protected int getMinSn() {
		return 1;
	}
}