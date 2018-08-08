package com.elementspeed.common.file.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.elementspeed.common.file.dto.FileInputDto;
import com.elementspeed.framework.base.exception.BOException;
import com.elementspeed.framework.base.service.BaseService;
import com.elementspeed.mapper.system.entity.SysFile;

public interface FileUploadService extends BaseService {
	/**
	 * 多附件上传
	 * @param multFileMap
	 * @return
	 */
	public FileInputDto fileUpload(Map<String, MultipartFile> multFileMap,SysFile sysFile
			) throws IOException, BOException;
}
