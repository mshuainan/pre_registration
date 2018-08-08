package com.elementspeed.framework.common;

/**
 * 附件上传后, 应当将上传结果转换为SRMFile
 *
 */
public class SRMFile {
	private String path; // 上传后的文件路径
	private String originalFilename; // 文件原名
	/**
	 * 服务器存名|即新的文件名，用于通用附件表
	 * 
	 * @author sai.deng
	 */
	private String serverFileName;
	private int fileSize; //文件大小，单位KB

	/**
	 * @param path
	 *            上传后的文件路径
	 * @param originalFilename
	 *            文件原名
	 */
	public SRMFile(String path, String originalFilename) {
		this.path = path;
		this.originalFilename = originalFilename;
	}

	public SRMFile(String path, String originalFilename, String serverFileName) {
		super();
		this.path = path;
		this.originalFilename = originalFilename;
		this.serverFileName = serverFileName;
	}
	
	public SRMFile(String path, String originalFilename, String serverFileName,
			int fileSize) {
		super();
		this.path = path;
		this.originalFilename = originalFilename;
		this.serverFileName = serverFileName;
		this.fileSize = fileSize;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public String getServerFileName() {
		return serverFileName;
	}

	public void setServerFileName(String serverFileName) {
		this.serverFileName = serverFileName;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	
}