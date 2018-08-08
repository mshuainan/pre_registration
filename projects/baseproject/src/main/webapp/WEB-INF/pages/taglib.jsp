<%@page import="com.elementspeed.common.env.EnvironmentVars"%>
<%@page import="com.elementspeed.framework.common.util.ContainerUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@page import="com.elementspeed.framework.common.util.StringUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.elementspeed.framework.common.util.JSONUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page import="com.elementspeed.framework.common.Version"%>
<%@page import="com.elementspeed.framework.common.AppProp"%>
<%@page import="com.elementspeed.common.datadictionary.DataDictionary"%>
<%@page import="com.elementspeed.common.env.CommonPropertiesLoad"%>
<% 
	String path = request.getContextPath(); 
	long version = Version.getVersionNo();
	String skin = CommonPropertiesLoad.getValueByKey("system.base.skin","green");
	String showMessageType = AppProp.getPageinationType();
%>

<link rel="stylesheet" type="text/css" id="themeLink" href="<%=path%>/static/js/jquery-easyui-1.4.5/themes/black/<%=skin %>/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/static/js/jquery-easyui-1.4.5/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/static/js/thirdplugin/htmleditor/jquery.cleditor.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/static/js/thirdplugin/message/message.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/static/css/framework/<%=skin%>/common.css">
<link href="<%=path%>/static/css/pages/css/base.css" rel="stylesheet">
<link href="<%=path%>/static/css/pages/css/<%=skin%>/process.css" rel="stylesheet">
<style type="text/css">
	/* 全局按钮div样式*/
	.globalBtn-div {
		padding:0px 0px 5px 0px;
		text-align:<%=AppProp.getCommonBtnAlign()%>;
	}
</style>
<script type="text/javascript">
	//全局配置
	var AppProp = {
		//翻页控件展示类型
		PaginationType : '<%=AppProp.getPageinationType()%>'
	};
</script>
<div class="error message" hidden="true">
	 <p></p>
</div>
<div class="warning message" hidden="true">
	 <p></p>
</div>
<div class="success message" hidden="true">
	 <p></p>
</div>
<script type="text/javascript">
	var CtxVar = {
		path : 	'<%=path%>',
	};
</script>

<script type="text/javascript" src="<%=path%>/static/js/jquery-easyui-1.4.5/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/jquery-easyui-1.4.5/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/thirdplugin/jquery.form2json.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/thirdplugin/jquery.form.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/thirdplugin/htmleditor/jquery.cleditor.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/kindeditor-4.1.10/kindeditor.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/thirdplugin/treeExtend.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/thirdplugin/DataGridExtend.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/thirdplugin/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/thirdplugin/validateExtend.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/thirdplugin/windowExtend.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/thirdplugin/datagrid-cellediting.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/common/base/extends.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/thirdplugin/validators/formValidator.js?version=<%=version%>"></script> 
<script type="text/javascript" src="<%=path%>/static/js/thirdplugin/chromFilter.js?version=<%=version %>"></script> 
<script type="text/javascript" src="<%=path%>/static/js/framework/common/util/StringUtil.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/common/util/DateUtil.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/common/util/ArrayUtil.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/common/util/AjaxUtil.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/common/util/FileUtil.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/common/util/HtmlUtil.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/common/sysFunction.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/vc/Observable.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/vc/VCAdapter.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/vc/grid/DataGrid.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/vc/grid/DataList.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/vc/grid/ComboGrid.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/vc/grid/GridFormatter.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/vc/grid/GridStyler.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/vc/fc/Fc.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/vc/fc/FcFactory.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/vc/fc/datebox.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/vc/fc/textbox.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/vc/fc/combobox.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/vc/fc/FcUtil.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/common/message/alertMessage.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/common/message/showMessage.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/common/message/messageFactory.js?version=<%=version %>"></script>
<script type="text/javascript" src="<%=path%>/static/js/framework/common/message/message.js?version=<%=version %>"></script>
<div id="background" class="background" style="display:none;z-index: 999999"></div> 
<div id="progressBar" class="progressBar" style="display: none;z-index: 999999"></div> 
<script type="text/javascript">
	/** 扩展itemId属性取值，主要是为了区分全局ID */
	$.fn.getCmp = function(itemId){
		return this.find('[itemId="'+itemId+'"]:eq(0)');
	}
	var ajaxbg = $("#background,#progressBar");
	$('#progressBar').text($.fn.datagrid.defaults.loadMsg);
	ajaxbg.hide();
	
	$(document).ajaxStart(function() {
		ajaxbg.show();
	}).ajaxStop(function() {
		ajaxbg.hide();
	});
	
	//隐藏高级查询
	$(function() {
		sysHideMore();
	});
	
	function clearDate(e){
		 $(e.data.target).datebox ('clear');
	}

</script>