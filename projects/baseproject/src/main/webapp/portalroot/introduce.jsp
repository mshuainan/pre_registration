<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp"%>
<html>
<head> 
	<title>欢迎填写报名</title>
 	<link rel="stylesheet" type="text/css" id="themeLink" href="<%=path%>/static/js/jquery-easyui-1.4.5/themes/black/<%=skin %>/easyui.css">
</head>
<body>
  <div align="center" style="font-size:25px;padding: 10px;">
	  <h1>欢迎填写报名表</h1>
	  <p align="center">请仔细阅读如下内容</p>
  </div>
  <div align="center">
	<div id="dd" class="easyui-dialog" title="报名须知" style="width:650px;height:500px;top:100px;line-height: 30px;font-size:18px;"
		data-options="iconCls:'icon-save',buttons:'#bb',resizable:true,modal:true,closable: false,">   
	    　&nbsp;&nbsp;&nbsp;1.请家长认真如实填写相关信息，对弄虚作假、改动户口、制造假户口、假房产证、出具假证明或拒不提供相关证明材料的学生，一经查出，取消报名资格。</br>
	　　2.请提前准备好户口本、房产证等证件，便于查看、填写相关信息。</br>
	　　3.本平台今年为试运行，若家长网上报名如有疑问或困难，请用手机登录我校微信公众号查看具体报名指南。微信公众平台搜索"蚌埠淮上实验小学"。</br>
	　　4.填写完表格后点击提交，提交后点击下载PDF文件，打印后报名当天携带表格及相关材料来校报名。</br>
	　　5.我校现场报名时间为8月22、23日。报名当天请家长携带户口本原件及首页和孩子页复印件，出身证明原件及复印件，购房合同原件及复印件。</br>
	　　6.即时信息请家长注意我校微信公众号的通知。
	</div>    
	<div id="bb">
		<a href="newRegister/toRegister" class="easyui-linkbutton" style="height:40px; width:110px">
			<font size="4" style="font-weight:bold;color:black">已读</font>
		</a>
	</div>
  </div>
</body>
</html>
