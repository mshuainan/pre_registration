package com.elementspeed.common.file.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.elementspeed.common.file.dto.Extra;
import com.elementspeed.common.file.dto.FileInputDto;
import com.elementspeed.common.file.dto.SysFileDto;
import com.elementspeed.common.file.service.FileUploadService;
import com.elementspeed.framework.base.exception.BOException;
import com.elementspeed.framework.base.service.BaseServiceImpl;
import com.elementspeed.framework.common.util.ContainerUtil;
import com.elementspeed.framework.common.util.SpringContextUtil;
import com.elementspeed.mapper.system.dao.SysFileDao;
import com.elementspeed.mapper.system.entity.SysFile;

@Service
public class FileUploadServiceImpl extends BaseServiceImpl implements FileUploadService {

	@Resource
	private SysFileDao sysFileDao;
	
	private static String downloadFile ="common.btn.att.download";
	@Override
	public FileInputDto fileUpload(Map<String, MultipartFile> multFileMap,SysFile sysFile) throws IOException, BOException {
		FileInputDto fileInput = new FileInputDto();
		if (ContainerUtil.isNotEmpty(multFileMap)) {

			fileInput.setAppend(true);
			
			List<String> initialPreview = new ArrayList<String>();
			List<SysFileDto> initialPreviewConfig = new ArrayList<SysFileDto>();
			try {
				Set<String> mapKeys = multFileMap.keySet();
				for (String key : mapKeys) {
					MultipartFile file = multFileMap.get(key);
					if (file != null && file.getSize() > 0L) {
						//将文件上传
						
						SysFile sFile = super.saveFile2(file.getInputStream(), file.getOriginalFilename());
						
						String id = UUID.randomUUID().toString();
						
						sysFile.setFileName(sFile.getFileName());
						sysFile.setServerFileName(sFile.getServerFileName());
						sysFile.setUrl(sFile.getUrl());
						sysFile.setId(id);
						sysFile.setFileSize(sFile.getFileSize());
						sysFile.setCreateTime(new Date());
						//插入数据库
						sysFileDao.insert(sysFile);
						//用于前台页面下载文档
						String previewStr = "<div class='file-preview-other'>" +
					    "<h2><i class='glyphicon glyphicon-file'></i></h2>" +
						"<a href=\"javascript:;\" onclick=\"FileUtil.download('"+sysFile.getUrl()+"')\">"+SpringContextUtil.getMessage(downloadFile)+"</a>" + "</div>";
						initialPreview.add(previewStr);
						SysFileDto sysFileDto = new SysFileDto(sFile.getFileName(), 
								sFile.getFileSize(), "120px", id);
						Extra extra = new Extra(id);
						sysFileDto.setExtra(extra);
						initialPreviewConfig.add(sysFileDto);
					}
				}
				fileInput.setSuccess(true);
			} catch (BOException e) {
				fileInput.setSuccess(false);
				fileInput.setMessage(e.getMessage());
			}
			fileInput.setInitialPreview(initialPreview);
			fileInput.setInitialPreviewConfig(initialPreviewConfig);
		}
		
		return fileInput;
	}

}
