/**
 * jquery easyUI的表单提交
 * @param formId	表单ID
 * @param url		
 * @param callback	执行成功后的回调函数,
 * 			<li>1.如果callback不为空, 成功后回调callback, callback的参数应当是经json转换后的ExecuteResult 
 * 			<li>2.如果callback为空, 根据isShowMsg提示"执行成功"
 * @param isShowMsg 是否在当前页面显示'执行成功'提示, 默认true
 * @param validate 验证表单 默认true 如不验证表单则为false
 * @param methodType
 */
function easyUISubimit(formId, url, callback, isShowMsg, validate, methodType) {
	if (validate == null || validate == undefined) {
		validate = true;
	}
	$("#" + formId).form("submit", {
		url : url,
		method : methodType || 'post',
		onSubmit : function() {
			if(!validate) {
				ajaxbg.show();
				return true;
			} else {
				var result = $(this).form("validate");
				if(result) 
					ajaxbg.show();
				
				return result;
			}
		},
		success : function(response) {
			_relogin(response);
			_doCallback(!!response ? StringUtil.stringToJSON(response) : response, callback, isShowMsg);
			ajaxbg.hide();
		}
	});
	
}

/**
 * jquery easyUI的表单提交
 * @param formId	表单ID
 * @param url		
 * @param callback	执行成功后的回调函数,
 * 			<li>1.如果callback不为空, 成功后回调callback, callback的参数应当是经json转换后的ExecuteResult 
 * 			<li>2.如果callback为空, 根据isShowMsg提示"执行成功"
 * @param isShowMsg 是否在当前页面显示'执行成功'提示, 默认true
 * @param validate 验证表单 默认true 如不验证表单则为false
 * @param methodType
 */
function easyUISubimitWhenError(formId, url, callback, erroCallback, isShowMsg, validate, methodType) {
	if (validate == null || validate == undefined) {
		validate = true;
	}
	$("#" + formId).form("submit", {
		url : getSecurityUrl(url),
		method : methodType || 'post',
		onSubmit : function() {
			if(!validate) {
				ajaxbg.show();
				return true;
			} else {
				var result = $(this).form("validate");
				if(result) 
					ajaxbg.show();
				return result;
			}
		},
		success : function(response) {
			//_relogin(response);
			_doCallbackWhenError(!!response ? StringUtil.stringToJSON(response) : response, callback, erroCallback, isShowMsg);
			ajaxbg.hide();
		}
	});
	
}

function easyUISubimit2(formId, url, callback, isShowMsg, validate, methodType,modal) {
	if (validate == null || validate == undefined) {
		validate = true;
	}
	if (modal == null || modal == undefined) {
		modal = true;
	}
	$("#" + formId).form("submit", {
		url : getSecurityUrl(url),
		method : methodType || 'post',
		onSubmit : function() {
			if(!validate) {
				if(modal)
				ajaxbg.show();
				return true;
			} else {
				var result = $(this).form("validate");
				if(result){ 
					if(modal)
					ajaxbg.show();
				}
				
				return result;
			}
		},
		success : function(response) {
			_relogin(response);
			_doCallback(!!response ? StringUtil.stringToJSON(response) : response, callback, isShowMsg);
			if(modal)
			ajaxbg.hide();
		}
	});
	
}

/**
 * jquery easyUI的表单提交，导出专用
 * @param formId 表单ID
 * @param url 请求的url
 * @param methodType get|post
 */
function easyUISubimitForExport(formId, url, methodType) {
	$("#" + formId).form("submit", {
		url : getSecurityUrl(url),
		method : methodType || 'post',
		onSubmit : function() {
			return true;
		},
		success : function(response) {
			_relogin(!!response ? StringUtil.stringToJSON(response) : response);
			_doCallback(!!response ? StringUtil.stringToJSON(response) : response);
		}
	});
}

/**
 * 执行jquery 的 $post操作
 * @param url
 * @param param	应当是json或数组
 * @param callback	执行成功后的回调函数,
 * 			<li>1.如果callback不为空, 成功后回调callback, callback的参数应当是经json转换后的ExecuteResult 
 * 			<li>2.如果callback为空, 提示"执行成功"
 * @param isShowMsg 是否在当前页面显示'执行成功'提示, 默认true
 * @param methodType
 * 
 * 例1:ajaxExec('/vendormgt/npc/npcTaskItem/confirm', [npcTaskItemId]);
 * 例2:ajaxExec("/vendormgt/npc/npcTaskItem/confirm", idArr);
 */
function ajaxExec(url, param, callback, isShowMsg, methodType) {
	ajaxbg.show();
	$.ajax({
	    type: methodType || "post",
	    url: getSecurityUrl(url) ,
	    contentType: "application/json",
	    data:JSON.stringify(param),
	    success:function(response){
	    	_relogin(response);
	    	_doCallback(response, callback, isShowMsg);
	    	ajaxbg.hide();
	    }
	});
	
}
/***
 * 显示Failure 的回调处理
 * @param url
 * @param param
 * @param callback
 * @param isShowMsg
 * @param methodType
 */
function ajaxExecWithError(url, param, callback, errorCallback, isShowMsg, methodType) {
	ajaxbg.show();
	$.ajax({
	    type: methodType || "post",
	    url: getSecurityUrl(url) ,
	    contentType: "application/json",
	    data:JSON.stringify(param),
	    success:function(response){
	    	_relogin(response);
	    	_doCallbackWhenError(response, callback, errorCallback, isShowMsg);
	    	ajaxbg.hide();
	    }
	});
	
}
/**
 * 参考上述ajax请求，只不过，此ajax请求用于同步操作的场景，可根据需要适当扩展
 * @param url
 * @param param
 * @param callback
 * @param isShowMsg
 * @param methodType
 */
function ajaxExecSync(url, param, callback, isShowMsg, methodType) {
	ajaxbg.show();
	$.ajax({
	    type: methodType || "post",
	    url: getSecurityUrl(url) ,
	    contentType: "application/json",
	    data:JSON.stringify(param),
	    async: false,
	    success:function(response){
	    	_relogin(response);
	    	_doCallback(response, callback, isShowMsg);
	    	ajaxbg.hide();
	    }
	});
	
}

/**
 * 执行jquery 的 $post操作，该操作并不指定为contentType为json
 * @param url
 * @param param	原始对象
 * @param callback	执行成功后的回调函数,
 * 			<li>1.如果callback不为空, 成功后回调callback, callback的参数应当是经json转换后的ExecuteResult 
 * 			<li>2.如果callback为空, 提示"执行成功"
 * @param isShowMsg 是否在当前页面显示'执行成功'提示, 默认true
 * 
 * 例1:ajaxExec('/vendormgt/npc/npcTaskItem/confirm', [npcTaskItemId]);
 * 例2:ajaxExec("/vendormgt/npc/npcTaskItem/confirm", idArr);
 */
function ajaxExec2(url,param, callback,isShowMsg,async,isModal) {
	if(async==null){
		async = true;
	}
	if(isModal){
		ajaxbg.show();
	}
	$.ajax({
		type: "POST",
		url: getSecurityUrl(url),
		data:param,
		async : async,
		success:function(response){
			_relogin(response);
			_doCallback(response, callback, isShowMsg);
			if(isModal){
				ajaxbg.hide();
			}
		}
	});
}

/**
 * 执行jquery 的 $post操作，该操作并不指定为contentType为json
 * @param url
 * @param param	原始对象
 * @param callback	执行成功后的回调函数,
 * 			<li>1.如果callback不为空, 成功后回调callback, callback的参数应当是经json转换后的ExecuteResult 
 * 			<li>2.如果callback为空, 提示"执行成功"
 * @param isShowMsg 是否在当前页面显示'执行成功'提示, 默认true
 * 
 * 例1:ajaxExec('/vendormgt/npc/npcTaskItem/confirm', [npcTaskItemId]);
 * 例2:ajaxExec("/vendormgt/npc/npcTaskItem/confirm", idArr);
 */
function ajaxExec2WithError(url,param, callback,errorCallback,isShowMsg,async,isModal) {
	if(async==null){
		async = true;
	}
	if(isModal){
		ajaxbg.show();
	}
	$.ajax({
		type: "POST",
		url: getSecurityUrl(url),
		data:param,
		async : async,
		success:function(response){
			_relogin(response);
			_doCallbackWhenError(response, callback, errorCallback, isShowMsg);
			if(isModal){
				ajaxbg.hide();
			}
		}
	});
}

/**
 * ajax执行回调函数
 * @param response
 * @param callback
 * @param isShowMsg 是否显示'执行成功'提示, 默认true
 */
function _doCallback(response, callback, isShowMsg) {
	if(response.success) {
		if(isShowMsg || typeof(isShowMsg) == "undefined") {
			Msg.showSuccess(response.message);
		}
		if($.isFunction(callback)) {
			//参数true 表示跳转后显示提示信息
			callback(response, true);
		}
	}
	else {
		Msg.showError(response.message);
	}
}

/**
 * ajax执行回调函数
 * @param response
 * @param callback
 * @param isShowMsg 是否显示'执行成功'提示, 默认true
 */
function _doCallbackWhenError(response, callback, errorCallback, isShowMsg) {
	if(response.success) {
		if(isShowMsg || typeof(isShowMsg) == "undefined"){
			Msg.showSuccess(response.message);
		}
		if($.isFunction(callback)) {
			callback(response, true);
		}
	} else {
		if($.isFunction(errorCallback)) {
			errorCallback(response, true);
			if(isShowMsg || typeof(isShowMsg) == "undefined"){
				Msg.showError(response.message);
			}
		}
	}
}

/**
 * 跳转到重新登录页面
 * @param response
 */
function _relogin(response) {
	if((typeof response) == "string" && response.indexOf("登陆已失效") > 0) {
		top.location.href = CtxVar.path + "/index.jsp";
	}
}