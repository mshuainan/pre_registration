package com.elementspeed.framework.base.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.elementspeed.common.env.CommonPropertiesLoad;
import com.elementspeed.common.env.EnvironmentVars;
import com.elementspeed.framework.base.dao.BaseEntity;
import com.elementspeed.framework.base.dao.PageResults;
import com.elementspeed.framework.base.exception.BOException;
import com.elementspeed.framework.common.SRMFile;
import com.elementspeed.framework.common.util.BeanUtil;
import com.elementspeed.framework.common.util.ContainerUtil;
import com.elementspeed.framework.common.util.DateUtil;
import com.elementspeed.framework.common.util.StringUtil;
import com.elementspeed.framework.common.util.file.FileUtil;
import com.elementspeed.mapper.system.entity.SysFile;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: BaseServiceImpl
 * @Description: TODO(基础service impl, 所有service impl 都应当继承 BaseServiceImpl)
 * @author masn
 * @date 2018年8月4日 下午2:50:41
 */
public abstract class BaseServiceImpl implements BaseService {
	
	private final String regEx="(.*)[`.\\[\\]:<>?|](.*)";
	
	//文件最大值
	private static Integer FILE_MAX_SIZE;

	/**
	 * 
	 * @param source
	 * @param dtoClass
	 * @return
	 */
	protected <S extends BaseEntity, T extends S> PageResults<T> convertPageResult(PageInfo<S> source, Class<T> dtoClass) {
		PageResults<S> sPageResult = new PageResults<S>(source); 
		PageResults<T> tPageResult = new PageResults<T>();
		BeanUtil.copyProperties(sPageResult, tPageResult, "page", "pageSize", "pageCount", "total");
		tPageResult.setRows(convert2Dto(sPageResult.getRows(), dtoClass));
		return tPageResult;
	}
	
	/**
	 * 将entityList转换成dtoList, S extends BaseEntity, T extends S
	 * @param entityList
	 * @param dtoClass
	 * @return  List<T>
	 */
	protected <S extends BaseEntity, T extends S> List<T> convert2Dto(List<S> entityList, Class<T> dtoClass) {
		List<T> dtoList = new ArrayList<T>();
		
		if(ContainerUtil.isEmpty(entityList)) {
			return dtoList;
		}
		
		try {
			for (S s : entityList) {
				T dto = dtoClass.newInstance();
				BeanUtil.copyProperties(s, dto);
				dtoList.add(dto);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return dtoList;
	}
	
	/**
	 * 为entityList中的所有元素设置createInfo
	 * @param entityList
	 */
	protected void setCreateInfo(List<? extends BaseEntity> entityList) {
		if(ContainerUtil.isNotEmpty(entityList)) {
			for (BaseEntity baseEntity : entityList) {
				baseEntity.setCreateInfo();
			}
		}
	}
	
	/**
	 * 为entityList中的所有元素设置createInfo
	 * @param fkPropName
	 * @param fkValue
	 * @param entityList
	 */
	protected void setCreateInfo(String fkPropName, String fkValue, List<? extends BaseEntity> entityList) {
		if(ContainerUtil.isNotEmpty(entityList)) {
			for (BaseEntity baseEntity : entityList) {
				BeanUtil.setProperty(baseEntity, fkPropName, fkValue);
				baseEntity.setCreateInfo();
			}
		}
	}
	
	/**
	 * 为entityList中的所有元素设置createInfo
	 * @param entityList
	 */
	protected void setLastModifyInfo(List<? extends BaseEntity> entityList) {
		if(ContainerUtil.isNotEmpty(entityList)) {
			for (BaseEntity baseEntity : entityList) {
				baseEntity.setLastModifyInfo();
			}
		}
	}
	
	/**
	 * 批量insert, 执行dao.methodName(args), args中需要包含待持久化的list对象 
	 * @param dao
	 * @param methodName
	 * @param args
	 * @throws BOException 
	 */
	@SuppressWarnings("unchecked")
	protected void insertBatch(Object dao, String methodName, Object... args) throws RuntimeException {
		if(args == null) {
			return;
		}
		
		//entityList在args中的位置
		int index = 0;
		List<BaseEntity> entityList = null;
		for (Object object : args) {
			if(object instanceof List) {
				entityList = (List<BaseEntity>) object;
				break;
			}
			index++;
		}
		if(ContainerUtil.isEmpty(entityList)) {
			return;
		}
		
		Object[] argArr = args;
		Method m = BeanUtil.findDeclaredMethodWithMinimalParameters(dao.getClass(), methodName);
		//批量insert, OFFECT条记录插入一次, 如果sql语句过长, 会导致insert失败
		final int OFFECT = 100;
		int start = 0, length = entityList.size();
		try {
			do {
				if(start + OFFECT < length) {
					argArr[index] = entityList.subList(start, start + OFFECT);
				} else {
					argArr[index] = entityList.subList(start, length);
				}
	
				m.invoke(dao, args);
				start += OFFECT;
			} while(start < length);
		} catch (InvocationTargetException e) {
			//此处需要抛出 RuntimeException 否则事务不能回滚
			e.printStackTrace();
			throw new RuntimeException(e.getTargetException().getMessage());
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 保存文件.
	 * 该方法是BaseService.saveFile的默认实现.
	 * srcInputStream
	 * @param srcInputStream	到保存的输出流
	 * @param srcFileName		源文件名称
	 * @throws IOException
	 * @return SRMFile
	 * @throws BOException 
	 */
	@Override
	public SRMFile saveFile(InputStream srcInputStream, String srcFileName,String... suffixsArgs) throws IOException, BOException {
		//验证文件后缀
		if(suffixsArgs.length > 0){
			Boolean isUpload = false;
			for(String str : suffixsArgs){
				if(StringUtil.isNotEmpty(str)){
					String suffix = srcFileName.substring(srcFileName.lastIndexOf(".")+1, srcFileName.length());
					if(str.equals(suffix)){
						isUpload = true;
					}
				}
			}
			if(!isUpload){
				throw new BOException("请上传正确的文件!");
			}
		}
		//根据项目情况修改, 各项目应当通过策略模式设置上传方法
		if(StringUtil.isNotEmpty(srcFileName) && Pattern.matches(regEx, srcFileName.substring(0, srcFileName.lastIndexOf(".")))) {
			/*throw new BOException("文件名不能含有以下特殊字符：`~!@#$%^&*+=|{}':;,.<>/?￥……——【】‘；：”“’。，、？");*/
			throw new BOException("文件名不能含有以下特殊字符：`.:<>?|");
		}
		String sFileName = DateUtil.convertDateToString(new Date(), "yyyyMM") +
				System.currentTimeMillis() + "_" + srcFileName;
		if(srcInputStream.available() / 1024 / 1024 > getFileMaxSize()) {
			throw new BOException("上传文件不能大于" + getFileMaxSize() + "M");
		}
		String path = EnvironmentVars.getUpPath() +  DateUtil.getCurrentYearMonth() + "/" + getModuleName() + "/" + sFileName;
		SRMFile result = new SRMFile(path, srcFileName, sFileName, srcInputStream.available());
		File file = new File(path);
		FileUtil.copyInputStreamToFile(srcInputStream, file);
		return result;
	}
	
	/**
	 * 保存文件.
	 * 该方法是BaseService.saveFile的默认实现.
	 * srcInputStream
	 * @param srcInputStream	到保存的输出流
	 * @param srcFileName		源文件名称
	 * @throws IOException
	 * @return SRMFile
	 * @throws BOException 
	 */
	@Override
	public SysFile saveFile2(InputStream srcInputStream, String srcFileName) throws IOException, BOException {
		//根据项目情况修改, 各项目应当通过策略模式设置上传方法
		if(StringUtil.isNotEmpty(srcFileName) && Pattern.matches(regEx, srcFileName.substring(0, srcFileName.lastIndexOf(".")))) {
			/*throw new BOException("文件名不能含有以下特殊字符：`~!@#$%^&*+=|{}':;,.<>/?￥……——【】‘；：”“’。，、？");*/
			throw new BOException("文件名不能含有以下特殊字符：`.:<>?|");
		}
		String sFileName = DateUtil.convertDateToString(new Date(), "yyyyMM") +
				System.currentTimeMillis() + "_" + srcFileName;
		//菲克将文件上传大小改为50M
		if(srcInputStream.available() / 1000 / 1000 > getFileMaxSize()) {
			throw new BOException("上传文件不能大于"+getFileMaxSize()+"M");
		}
		String path = EnvironmentVars.getUpPath() +  DateUtil.getCurrentYearMonth() + "/" + getModuleName() + "/" + sFileName;
		SysFile result = new SysFile(path, srcFileName, sFileName,srcInputStream.available());
		File file = new File(path);
		FileUtil.copyInputStreamToFile(srcInputStream, file);
		
		return result;
	}
	
	/**
	 * 得到商城文件URL地址
	 * @param uploadPath
	 * @return
	 */
	public static String getMallFileUrl(String uploadPath) {
		String imageServerPath = EnvironmentVars.getValueByKey("mall.imageServerPath");
		String mallUpPath = EnvironmentVars.getMallUpPath();
		String mallServerUrl = imageServerPath + uploadPath.replace(mallUpPath, "/imgcache/");
		return mallServerUrl;
	}
	
	/**
	 * 得到商城上传文件路径
	 * @param vendorCode 供应商编码
	 * @param moduleName 模块名称，例如：商品模块：product
	 * @return
	 */
	public String getMallUploadPath(String vendorCode, String moduleName) {
		if (StringUtil.isEmpty(vendorCode) || StringUtil.isEmpty(moduleName)) {
			return null;
		}
		String mallUpPath = EnvironmentVars.getMallUpPath();
		String uploadPath = mallUpPath + vendorCode + "/" + moduleName + "/" + DateUtil.getCurrentYearMonth() + "/"
				+ DateUtil.getCurrentYYMMDD() + "/";
		return uploadPath;
	}
	
	/**
	 * 返回模块名称, 默认返回service类名
	 * @return
	 */
	public String getModuleName() {
		return this.getClass().getSimpleName();
	}
	
	/**
	 * 生成业务码, 默认返回null
	 */
	@Override
	public String generate() throws BOException {
		return null;
	}
	
	/**
	 * 抽取对象list中的id
	 * @param entityList
	 * @return
	 */
	protected List<String> getId(List<? extends BaseEntity> entityList) {
		List<String> idList = new ArrayList<String>();
		for(BaseEntity entity : entityList) {
			idList.add(entity.getId());
		}
		return idList;
	}
	
	private static Integer getFileMaxSize(){
		if(FILE_MAX_SIZE==null){
			FILE_MAX_SIZE = Integer.parseInt(CommonPropertiesLoad.getValueByKey("file_upload_max", "5"));
		}
		return FILE_MAX_SIZE;
	}
}