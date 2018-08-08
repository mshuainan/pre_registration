package com.elementspeed.framework.common.util.file;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import com.elementspeed.common.env.CommonPropertiesLoad;
import com.elementspeed.framework.base.exception.BOException;
import com.elementspeed.framework.common.util.DateUtil;
import com.elementspeed.framework.common.util.StringUtil;
/**
 * 文件处理帮助类
 */
public class FileUtil extends FileUtils {	
	private static boolean flag;
	/**
	 * path 指向的目标是否是文件
	 * @param path
	 * @return
	 */
	public static boolean isFile(String path) {
		File file = new File(path);
		
		return file != null ? file.isFile() : false;
	}	
	/**
	 * 客户端文件上传,固定目录和本身的文件名称即可
	 * @param files
	 * @param defdir
	 * @return
	 * @throws IOException 
	 */
	public static File savefileFix(MultipartFile files, String defdir) throws IOException {
		String sFileName = files.getOriginalFilename();
		File savefile = null;
		String saveDir = CommonPropertiesLoad.getValueByKey("path.attachment.upload") + "/" + defdir;
		createDir(saveDir, true);
		savefile = new File(saveDir,sFileName);
		files.transferTo(savefile);
		return savefile;
	}	
	/**
	 * 客户端文件上传
	 * @param files
	 * @param defdir
	 * @return
	 * @throws IOException 
	 */
	public static File savefile(MultipartFile files, String defdir) throws IOException {
		String sFileName = DateUtil.convertDateToString(new Date(), "yyyyMM") +
				System.currentTimeMillis() + "_" + files.getOriginalFilename();
		File savefile = null;
		String saveDir = CommonPropertiesLoad.getValueByKey("path.attachment.upload") +  DateUtil.getCurrentYearMonth() + "/" + defdir;
		createDir(saveDir, true);
		savefile = new File(saveDir,sFileName);
		files.transferTo(savefile);
		return savefile;
	}	
	/**
	 * 创建多个文件夹
	 * 
	 * @param dir
	 * @param ignoreIfExitst
	 * @throws IOException
	 */
	public static void createDir(String dir, boolean ignoreIfExitst)
			throws IOException {
		File file = new File(dir);

		if (ignoreIfExitst && file.exists()) {
			return;
		}

		if (file.mkdirs() == false) {
			throw new IOException("Cannot create directories = " + dir);
		}
	}	
	public static String getFileSuffix(String fileName) {
		if (fileName == null || "".equals(fileName.trim())) {
			return "";
		}
		if (fileName.lastIndexOf(".") == -1) {
			return "";
		}

		return fileName.substring(fileName.lastIndexOf("."));
	}	
	public static void createFile(String filename, String content) {
		File fp = new File(filename);
		PrintWriter pfp = null;
		try {
			pfp = new PrintWriter(fp);
			pfp.print(content);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			pfp.close();
		}
	}	
	/**
	 * 获取某路径下所有文件列表 并按文件名排序
	 * @param path
	 * @return List<String>
	 * @throws IOException
	 */
	public static List<String> getAllFileName(String path) throws IOException {
		return getAllFileName(path, true);
	}	
	/**
	 * 根据后缀名获取某路径下所有文件列表 并按文件名排序
	 * @param path
	 * @param suffix 
	 * @return
	 * @throws IOException
	 */
	public static List<String > getAllFileName(String path, String suffix) throws IOException {
		return getAllFileName(path, suffix, true);
	}	
	/**
	 * 根据后缀名获取某路径下所有文件列表 并按文件名排序
	 * @param path
	 * @param isSuffix 返回的路径是否带有后缀名
	 * @return
	 * @throws IOException
	 */
	public static List<String> getAllFileName(String path, boolean isSuffix) throws IOException {
		return getAllFileName(path, null, isSuffix);
	}
	
	/**
	 * 根据后缀名获取某路径下所有文件列表 并按文件名排序
	 * @param path
	 * @param suffix 文件后缀名
	 * @param isSuffix 返回的路径是否带有后缀名
	 * @return List<String>
	 */
	public static List<String> getAllFileName(String path, String suffix, boolean isSuffix) throws IOException
    {
		createDir(path, true);
		List<String> fileNameList = new ArrayList<String>();
		File file = new File(path);
		String[] names = file.list();
		for(String name : names) {
			if(StringUtil.isNotEmpty(suffix) && !name.endsWith(suffix)) {
				continue;
			}
			if(!isSuffix) {
				name = name.substring(0, name.lastIndexOf("."));
			}
			fileNameList.add(name);
		}
		Collections.sort(fileNameList, new Comparator<String>() {
		    @Override
		    public int compare(String o1, String o2) {
		        return -o1.compareTo(o2);
		    }
		});
		
		return fileNameList;
    }
	
	/**
	 * 获取文件路径的文件名
	 * @param path 文件路径
	 */
	public static String getFileName(String path)
	{
		String name = path.substring(path.lastIndexOf("/")+1,path.length());
		return name;
	}
	
	/**
	 * 获取文件路径(统一替换成"/")
	 * @param path 文件路径
	 */
	public static String getFilePath(String path)
	{
		path = path.replaceAll("\\\\", "/");
		return path;
	}
	
	/**
	 * 写入到文件
	 * @param file
	 * @param content
	 * @throws Exception
	 */
	public static synchronized void writeFile(File file, String content) throws Exception {
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		if(!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter writer = null;
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
			writer = new BufferedWriter(fileWriter);
			writer.write(content);
			writer.flush();
		}catch (Exception e) {
			e.printStackTrace();
			throw new BOException(e.getMessage());
		} finally {
			if(writer != null) {
				writer.close();
			}
			if(fileWriter != null) {
				fileWriter.close();
			}
		}
	}
	
	/**
	 * 读取文件内容
	 * @param inFile
	 * @param content 删除的文本
	 * @throws Exception
	 */
	public static String readFile(File inFile) throws Exception {
		if(!inFile.exists()) {
			inFile.createNewFile();
			return "";
		}
		FileInputStream fis = new FileInputStream(inFile);
		BufferedReader in = new BufferedReader(new InputStreamReader(fis));
		
		String thisLine = "";
		String tmp = "";
		while (StringUtil.isNotEmpty(tmp = in.readLine())) {
			thisLine += tmp + "\r\n" ;
		}
		in.close();
		fis.close();
		return thisLine;
	}	
	/** 
	 * 删除目录（文件夹）以及目录下的文件 
	 * @param   filePath 被删除目录的文件路径 
	 * @return  目录删除成功返回true，否则返回false 
	 */  
	public static boolean deleteDirectory(String filePath) {  
	    //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
	    if (!filePath.endsWith(File.separator)) {  
	    	filePath = filePath + File.separator;  
	    }  
	    File dirFile = new File(filePath);  
	    //如果dir对应的文件不存在，或者不是一个目录，则退出  
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	    flag = true;  
	    //删除文件夹下的所有文件(包括子目录)  
	    File[] files = dirFile.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        //删除子文件  
	        if (files[i].isFile()) {  
	            flag = delFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        } //删除子目录  
	        else {  
	            flag = deleteDirectory(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  
	    }  
	    if (!flag) return false;  
	    //删除当前目录  
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;  
	    }  
	} 	
    /** 
     * 删除单个文件 
     * @param   filePath    被删除文件的文件名 
     * @return 单个文件删除成功返回true，否则返回false 
     */  
    public static boolean delFile(String filePath) {  
        flag = false;  
       File file = new File(filePath);  
        // 路径为文件且不为空则进行删除  
        if (file.isFile() && file.exists()) {  
            file.delete();  
            flag = true;  
        }  
        return flag;  
    }  
}