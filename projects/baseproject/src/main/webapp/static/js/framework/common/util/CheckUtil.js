var CheckUtil = {
	url : null,
	callBack : null,
	setStatus : function(v){
		$('#checkingForm').getCmp('checkStatus').val(v);
	},
	close:function(isDestory){
		$('#CheckingDg').dialog('close');
		if(isDestory){
			$('#CheckingDg').dialog('destroy');
		}
	},
	submit : function(){
		easyUISubimit('checkingForm', CheckUtil.url, CheckUtil.callBack);
	},
	open : function(id,url,callBack,needAtt){
		var attHtml = '';
		if(needAtt){
			attHtml = '<tr><td>附件</td><td><input name="checkAttFile" class="easyui-filebox" data-options="prompt:\'选择文件\',icons:[{iconCls:\'icon-clear\', handler: function(e){$(e.data.target).filebox(\'clear\');}}]" style="width:97%;"/></td></tr>';
		}
		CheckUtil.url = url;
		CheckUtil.callBack = callBack;
		if($('#CheckingDg').length<=0){
			var html = '<div id="CheckingDg" class="easyui-dialog dialog">' 
				+'<div class="dialog-ftitle">审核结果</div>'
				+'<form id="checkingForm" method="post" action="#" enctype="multipart/form-data">'
				+'<input type="hidden" name="checkStatus" itemId="checkStatus" value="1"/>'
				+'<input type="hidden" name="id" itemId="id">'
				+'<table cellpadding="5">'
				+'<tr>'
				+'<td colspan="2">'
				+'<input type="radio" name="dg_checkStatus" value="1" onclick="CheckUtil.setStatus(1)" checked="checked">'
				+'<label for="dg_checkStatus_0">通过</label>'
				+'<input type="radio" name="dg_checkStatus" value="-1" onclick="CheckUtil.setStatus(-1)">'
				+'<label for="dg_checkStatus_1">驳回</label>'
				+'</td>'
				+'</tr>'
				+'<tr id="tr_checkContent">'
				+'<td class="required">审核意见：</td>'
				+'<td><input type="text" id="dg_checkContent" name="checkOpinion" class="easyui-textbox" style="height:60px;width:280px" data-options="multiline:true,required:true"></td>'
				+'</tr>'
				+attHtml
				+'</table>'
				+'</form>' 
				+'</div>';
				$(document.body).append(html);
				$.parser.parse($('#CheckingDg')); 
				$('#CheckingDg').dialog({
					closed:true,
					close : true,
					title:'审核',
					modal:true,
					buttons:[{
						iconCls:'icon-ok2',
						text:I18N.getText('common.submit'),
						handler: CheckUtil.submit
					}]
				});
		}
		$('#checkingForm').form('reset');
		$('#checkingForm').getCmp('id').val(id);
		$('#checkingForm').getCmp('checkStatus').val('1');
		$('#CheckingDg').dialog({close : true});
		$('#CheckingDg').dialog('open');
	},
	/**
	 * 自定义参数
	 * @param id 对象ID
	 * @param url 提交URL
	 * @param callBack 回调函数
	 * @param needOptions 是否需要选项
	 * @param needAtt 是否需要附件
	 * @param params {dgId:'弹出窗口ID',formId:'提交FORM的ID',attFileName:'附件文件DOM name',
	 * opinionName:'意见name',opinionReq:'boolean型，意见是否为必须',title:'状态的标题信息',statusName：'状态的name'}
	 */
	openCustom : function(id,url,callBack,needOptions,needAtt,params){
		var attHtml = '';
		var optionsHtml = '';
		if(needAtt){
			attHtml = '<tr><td>附件</td><td><input name="'+params.attFileName+'" class="easyui-filebox" data-options="prompt:\'选择文件\',icons:[{iconCls:\'icon-clear\', handler: function(e){$(e.data.target).filebox(\'clear\');}}]" style="width:100%;"/></td></tr>';
		}
		if(needOptions){
			optionsHtml = '<tr>'
			+'<td colspan="2">'
			+'<input type="radio" name="dg_status" value="1" onclick="CheckUtil.setCustomStatus(1,\''+params.statusName+'\',\''+params.formId+'\')" checked="checked">'
			+'<label for="dg_status_0">通过</label>'
			+'<input type="radio" name="dg_status" value="-1" onclick="CheckUtil.setCustomStatus(-1,\''+params.statusName+'\',\''+params.formId+'\')">'
			+'<label for="dg_status_1">驳回</label>'
			+'</td>'
			+'</tr>';
		}
		var opinionHtml = '';
		if(params.opinionName!=null && params.opinionName!=''){
			var opinionClass = params.opinionReq==true?'required':'';
			opinionHtml ='<tr id="tr_content">'
			+'<td class="'+opinionClass+'">意见：</td>'
			+'<td><input type="text" id="dg_content" name="'+params.opinionName+'" class="easyui-textbox" style="height:60px;width:300px" data-options="multiline:true,required:'+params.opinionReq+'"></td>'
			+'</tr>';
		}
		CheckUtil.url = url;
		CheckUtil.callBack = callBack;
		
		if($('#'+params.dgId).length<=0){
			var html = '<div id="'+params.dgId+'" class="easyui-dialog dialog">' 
				+'<div class="dialog-ftitle">'+params.title+'</div>'
				+'<form id="'+params.formId+'" method="post" action="#" enctype="multipart/form-data">'
				+'<input type="hidden" name="'+params.statusName+'" itemId="'+params.statusName+'" value="1"/>'
				+'<input type="hidden" name="id" itemId="id">'
				+'<table cellpadding="5">'
				+optionsHtml
				+opinionHtml
				+attHtml
				+'</table>'
				+'</form>' 
				+'</div>';
				$(document.body).append(html);
				$.parser.parse($('#'+params.dgId)); 
				$('#'+params.dgId).dialog({
					closed:true,
					close : true,
					title:params.title,
					modal:true,
					buttons:[{
						iconCls:'icon-ok2',
						text:I18N.getText('common.submit'),
						handler: function(){
							CheckUtil.submitCustom(params.formId);
						}
					}]
				});
		}
		$('#'+params.formId).form('reset');
		$('#'+params.formId).getCmp('id').val(id);
		$('#'+params.formId).getCmp(params.statusName).val('1');
		$('#'+params.dgId).dialog('open');
	},
	setCustomStatus : function(v,statusName,formId){
		$('#'+formId).getCmp(statusName).val(v);
	},
	submitCustom : function(formId){
		easyUISubimit(formId, CheckUtil.url, CheckUtil.callBack);
	}
		
		
}

CheckUtil.viewDetail = function(text,attFileName,attFileUrl){
	var content = '<p>'+text+'</p>';
	if(attFileName!=null && attFileName!='' && attFileName!='null'){
		var url = attFileUrl.replace(/\\/gi,'/');
		content+='<p>'+I18N.getText('common.enclosure')+'：<a href="javascript:;" onclick="FileUtil.download(\''+url+'\',\''+attFileName+'\')">'+attFileName+'</p>';
	}
	var $detail = $('#detail');
	if($detail.length>=0){
		$('#detail').dialog('open');
		$('#detail').getCmp('content').html(content);
	}
	$(document.body).append('<div id="detail"><div itemId="content"></div></div>');
	$('#detail').dialog({
		width:400,
		height:200,
		title : I18N.getText('common.detail'),
		content : content,
		modal : true
	});
	$('#detail').dialog('open');
};