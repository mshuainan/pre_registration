/**
 * 文件下载
 */
var FileUtil = {
	/**
	 * 下载
	 * @param filePath 文件路径，不能为空
	 * @param fileName 文件名，可为空
	 * @returns {Boolean} 结果
	 */
	download : function(filePath,fileName){
		if(filePath == null || filePath == ''){
			$.messager.alert('提示','文件路径不能为空','warning');
			return false;
		}
		filePath = filePath.replace(/\\/gi,'/');
		var url = getSecurityUrl('/common/download');
		var fileName = fileName == null ? "" : fileName;
		var inputs = '<input type="hidden" name="filePath" value="'+filePath+'"><input type="hidden" name="fileName" value="'+fileName+'">';
		jQuery('<form action="'+ url +'" method="post">'+inputs+'</form>')
        .appendTo('body').submit().remove();
	},
	
	/**
	 * 浏览器直接打开日志文件
	 * @param filepath 文件路径
	 * @returns
	 */
	showLog : function(filepath){ 
		var url = getSecurityUrl('/common/download/log');
		var newWindow = window.open(url, name);
		if (!newWindow) {
			return false;
		}
		var html = "";
		html += "<html><head></head><body><form id='formid' method='post' action='" + url + "'>";
		html += "<input type='hidden' name='filepath' value='" + filepath + "'/>";
		html += "</form><script type='text/javascript'>document.getElementById(\"formid\").submit()</script></body></html>";
		newWindow.document.write(html);
		return newWindow;  
	}     
}
     
