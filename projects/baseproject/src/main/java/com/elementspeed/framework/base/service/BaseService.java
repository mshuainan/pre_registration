package com.elementspeed.framework.base.service;

import java.io.IOException;
import java.io.InputStream;

import com.elementspeed.framework.base.exception.BOException;
import com.elementspeed.framework.common.SRMFile;
import com.elementspeed.framework.common.sn.generator.IGenerator;
import com.elementspeed.mapper.system.entity.SysFile;

/**
 * 基础service, 所有service 都应当继承 BaseService
 *
 */
public interface BaseService extends IGenerator {

	/**
	 * 保存文件
	 * srcInputStream
	 * @param srcInputStream	到保存的输出流
	 * @param srcFileName		源文件名称
	 * @throws IOException
	 * @throws BOException
	 * @return SRMFile
	 */
	SRMFile saveFile(InputStream srcInputStream, String srcFileName,String... suffixsArgs) throws IOException, BOException;
	
	
	/**
	 * 保存文件
	 * srcInputStream
	 * @param srcInputStream	到保存的输出流
	 * @param srcFileName		源文件名称
	 * @throws IOException
	 * @throws BOException
	 * @return SRMFile
	 */
	SysFile saveFile2(InputStream srcInputStream, String srcFileName) throws IOException, BOException;
	
	/**
	 * 得到商城上传文件路径
	 * @param vendorCode 供应商编码
	 * @param moduleName 模块名称，例如：商品模块：product
	 * @return
	 */
	public String getMallUploadPath(String vendorCode, String moduleName);
	
}
