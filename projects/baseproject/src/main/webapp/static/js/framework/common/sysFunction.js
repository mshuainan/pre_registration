/** 系统通用函数*/

/**
 * 重置表单数据
 * @param formId
 */
function sysReset(formId) {
	$('#' + formId).form('reset');
}

/**
 * 清空表单数据, 如果设置了expectArr, 则expectArr中的组件不清空
 * @param formId
 * @param expectArr	组件ID数组
 */
function sysClear(formId, expectArr) {
	var expectValueArr = [];
	var expectVCArr = [];
	if(expectArr) {
		for(var i = 0; i < expectArr.length; i++) {
			var vc = new VCAdapter(expectArr[i]);
			expectValueArr[i] = vc.getValue();
			expectVCArr[i] = vc;
		}
	}
	
	$('#' + formId).form('clear');
	
	if(expectArr) {
		for(var i = 0; i < expectArr.length; i++) {
			var vc = expectVCArr[i];
			vc.setValue(expectValueArr[i]);
		}
	}
}

/**
 * 页面加载完毕后调用 sysHideMore(), 用以隐藏高级查询条件
 */
function sysHideMore() {
	$('div[group=seniorSerch]').hide();
}

function sysShowMore() {
	$('div[group=seniorSerch]').slideToggle('fast');
}

/**
 * 执行fun函数, 执行时附带ajax禁止页面交互效果
 */
function execWithAjaxbg(fun) {
	ajaxbg.show();
	fun();
	ajaxbg.hide();
}

/**
 * 跳转到URL指向的视图
 * @param url
 * @param isShowSuccessMsg 非必填 默认为false 
 * 		  当保存成功后需要跳转页面时应设置为true
 * 
 */
function sysToUrl(url, isShowSuccessMsg) {
	ajaxbg.show();
	location.href = getSecurityUrl(url) + "&isShowSuccessMsg=" + isShowSuccessMsg;
}

function sysToNewPageUrl(url, isShowSuccessMsg) {
	window.open(getSecurityUrl(url) + "&isShowSuccessMsg=" + isShowSuccessMsg);
}

/**
 * 导出Excel专用
 * @param url
 * @param isShowSuccessMsg 非必填 默认为false 
 * 		  当保存成功后需要跳转页面时应设置为true
 * 
 */
function exportExcelToUrl(url, isShowSuccessMsg) {
	location.href = getSecurityUrl(url) + "&isShowSuccessMsg=" + isShowSuccessMsg;
}

function getSecurityUrl(url) {
	if(!!url) {
		if (url.indexOf("?") === -1) {
			url += "?"; 
		} 
		else { 
			url += "&"; 
		}
		return CtxVar.path + url;
	} else {
		return null;
	}
}

/**
 * 
 * @param url
 * @returns {String}
 */
function getSysIframe(url) {
	if(!!url)
		return '<iframe src="' + CtxVar.path + '/system/ifream/toUrl?url=' + url + '&security_id=' + CtxVar.securityId + '" frameborder="no"   border="no" height="100%" width="100%" scrolling="auto"></iframe>';
	else
		return null;
}

/**
 * 当前用户是否拥有按钮的操作级权限 moduleBtnId 按钮的唯一标识 格式为 pid_btnId 如：00302_btnAdd
 * @param moduleBtnId
 * @returns
 */
function hasOpt(moduleBtnId) {
	return CtxVar.optMap[moduleBtnId] == undefined ? 
			false : CtxVar.optMap[moduleBtnId];
}

/**
 * 
 * @param tabId
 * @param title
 * @param url
 * @param closeable
 */
function addTab(tabId, title, url, closeable) {
	$('#' + tabId).tabs('add', {
        title : title,
        closable : closeable,
        content : '<iframe  class="page-iframe" src="' + CtxVar.path + '/system/ifream/toUrl?url=' + url + '" frameborder="no" border="no" height="99%" width="100%" scrolling="auto"></iframe>'
    });
}

/**
 * 
 * @param tabId
 * @param title
 * @param id
 * @param closeable
 */
function addTabWithoutContent(tabId, title, id, closeable) {
	$('#' + tabId).tabs('add', {
        title : title,
        closable : closeable,
        content : '<iframe id="' + id + '" class="iframe-normal"></iframe>'
    });
}

/**
 * easyUI 文件控件得到选择文件提示
 * @param id
 * @returns
 */
function getSelectFilePrompt(id) {
	$('#' + id).filebox({
		prompt : I18N.getText('common.chooseFile')
	});
}