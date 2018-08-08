// 初始化多附件文档
function FileInput(tableName, tlId, busAction, btnId,inputFiles,
	fileInputDialogId,multFile,showUpload,showDelete,allowedFileExtensions, maxFileSize,btnName,isShowTable) {
	this.tableName = tableName;// 表名
	this.tlId = tlId; // id
	this.busAction = busAction;// 操作
	this.btnId = btnId;// 上传按钮id
	this.inputFiles = inputFiles; // 返回id的隐藏域
	this.fileInputDialogId = fileInputDialogId;// 弹窗id
	this.multFile = multFile;//input上传文件
	this.allowedFileExtensions = allowedFileExtensions; //允许的文件扩展名
	this.maxFileSize = maxFileSize; //最大允许的文件大小
	this.showUpload = showUpload ;//是否显示上传按钮
	this.showDelete = showDelete;//是否显示删除按钮
	this.list = new Array();
	this.initialPreviewHtml = new Array();
	this.initialPreviewConfigHtml = new Array();
	//this.inputFilebodyNone ="";
	this.deleteAnduploadBtn = {};
	this.btnName = btnName || 'common.uploadFile';
	this.isShowTable = isShowTable;
}

FileInput.prototype.initFileInputFiles = function(fileInputParm) {
	if(fileInputParm.showUpload == null || fileInputParm.showUpload == undefined){
		fileInputParm.showUpload = true;
	}
	if(fileInputParm.showDelete== null || fileInputParm.showDelete == undefined){
		fileInputParm.showDelete = true;
	}
	if(fileInputParm.btnName != null && fileInputParm.btnName != ''){
		this.btnName = fileInputParm.btnName;
	}
	/*fileInputParm.isShowTable = false;*/
	//给上传按钮绑定事件,如果有才绑定事件
	if(fileInputParm.isShowTable){
		
	}else{
		$('#' + fileInputParm.btnId).bind('click',function() {
			fileInputParm.toView();
		});
	}
	//缩略图按钮展示控制
	if(!this.showUpload){
		this.deleteAnduploadBtn.actionUpload = '';
		//this.inputFilebodyNone = "inputFilebodyNone";
	}
	if(!this.showDelete){
		this.deleteAnduploadBtn.actionDelete = '';
	}
	if(fileInputParm.tlId){
		ajaxExec(
				'/common/fileupload/getFiles',
				{
					tableName : fileInputParm.tableName,
					tlId : fileInputParm.tlId,
					busAction : fileInputParm.busAction
				},
				function(result) {
					result = result.resultMap.list
					var filesId = "";
					// 拼接初始化图片参数
					if (result) {
						for (var i = 0; i < result.length; i++) {
							var rowData = result[i];
							fileInputParm.initialPreviewHtml[i] = "<div class='file-preview-other'>" +
						    "<h2><i class='glyphicon glyphicon-file'></i></h2>" +
							"<a href=\"javascript:;\" onclick=\"FileUtil.download('"+rowData.url+"')\">"+I18N.getText('common.downloadFile')+"</a>" + "</div>";
							fileInputParm.initialPreviewConfigHtml[i] = {
								caption : rowData.fileName, // 展示的文件名
								width : '120px',
								key : rowData.id, // 删除是Ajax向后台传递的参数
								extra : {
									id : rowData.id
								}
							};
							filesId += rowData.id + ",";
							fileInputParm.list.push(rowData.id);
						}
						// 图片id
						$("#" + fileInputParm.inputFiles).val(filesId);
						// 按钮显示文件个数
						//如果上传的位置存在表格，则调用另一种方法,如果没有定义该按钮，则不处理按钮
						fileInputParm.showBtn(result);
					}
					fileInputParm.initFileInput(fileInputParm);
				}, false);
	}else{
		// 按钮显示文件个数
		fileInputParm.showBtn(this.list);
		fileInputParm.initFileInput(fileInputParm);
	}
};

FileInput.prototype.initFileInput = function(fileInputParm) {
	var defaultAllowFileExt = [ 'jpg', 'jpeg', 'png', 'gif', 'doc', 'docx', 'xls', 'xlsx', 'pdf', 'ppt', 'txt', 'zip', 'rar'];
	var obj = {
		language : language,
		uploadUrl : getSecurityUrl('/common/fileupload/multFileUpload?tableName='
				+ fileInputParm.tableName
				+ '&tlId='
				+ fileInputParm.tlId
				+ '&busAction='
				+ fileInputParm.busAction),
		allowedFileExtensions : fileInputParm.allowedFileExtensions || defaultAllowFileExt,
		overwriteInitial : false,// 不覆盖已存在的图片
		maxFileSize : fileInputParm.maxFileSize || 10000,
		maxFilesNum : 10,
		showUpload : this.showUpload, //是否显示上传按钮,跟随图片的那个
		layoutTemplates:fileInputParm.deleteAnduploadBtn,
		showRemove : false,
		showBrowse : this.showUpload ,	//是否显示选择按钮
		showCaption: this.showUpload ,//是否显示下面的input框
		//dropZoneEnabled: this.showUpload,//是否显示拖拽区域
		//previewClass:this.inputFilebodyNone,
		//slugCallback : function(filename) {
		//	return filename.replace('(', '_').replace(']', '_');
		//},
		initialPreviewFileType : 'html',
		initialPreviewAsData : true,
		initialPreview : fileInputParm.initialPreviewHtml,
		initialPreviewConfig : fileInputParm.initialPreviewConfigHtml,
		deleteUrl : getSecurityUrl('/common/fileupload/deleteFile'),
		browseOnZoneClick : true
	}
	$("#"+fileInputParm.multFile).fileinput(obj);
	// 图片上传虚线框显示异常
	$(".file-drop-zone > .clearfix").css("overflow", "visible");
	// 附件上传成功后回调
	$("#"+fileInputParm.multFile).on('fileuploaded', function(outData, pid, i) {
		var filesId = "";
		var response = pid.response;
		if (!response.success) {
			Msg.showError(response.message);
			return;
		}
		fileInputParm.list.push(response.initialPreviewConfig[0].key);
		fileInputParm.showBtn(fileInputParm.list);
		for (var i = 0; i < fileInputParm.list.length; i++) {
			filesId += fileInputParm.list[i] + ",";
		}
		$("#" + fileInputParm.inputFiles).val(filesId);
	});
	// 上传文件删除(后)
	$('#'+fileInputParm.multFile).on('filedeleted',
		function(event, id, index) {
			for (var i = 0; i < fileInputParm.list.length; i++) {
				if (fileInputParm.list[i] == id) {
					fileInputParm.list.splice(i, 1);
					fileInputParm.showBtn(fileInputParm.list);
				}
			}
			var filesId = "";
			for (var i = 0; i < fileInputParm.list.length; i++) {
				filesId += fileInputParm.list[i] + ",";
			}
			$("#" + fileInputParm.inputFiles).val(filesId);
		});
};

// 弹窗
FileInput.prototype.toView = function() {
	$('#' + this.fileInputDialogId).dialog('open');
	$('#' + this.fileInputDialogId).dialog('autoSizeMax', {
		window : true
	});
	// 预览弹窗优先级
	$("#kvFileinputModal").css("z-index", 9999);
}

FileInput.prototype.showBtn = function(result){
	if(this.btnId==null || this.btnId==''){
		return;
	}
	if(this.isShowTable){
		$(window.parent.document).find('[id="'+this.btnId+'"]').html(
			I18N.getText(this.btnName) + "(" + result.length + ")");
	}else{
		$("#" + this.btnId).html(
			I18N.getText(this.btnName) + "(" + result.length + ")");
	}
}