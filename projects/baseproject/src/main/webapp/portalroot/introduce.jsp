<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp"%>
<html>
<head> 
	<title>欢迎填写报名</title>
 	<link rel="stylesheet" type="text/css" id="themeLink" href="<%=path%>/static/js/jquery-easyui-1.4.5/themes/black/<%=skin %>/easyui.css">
</head>
<body>
  <div align="center" style="font-size:30px;padding: 10px;">
	  <h1>欢迎填写报名表</h1>
	  <p align="center">请仔细阅读如下内容</p>
  </div>
  <div align="center">
	<div id="dd" class="easyui-dialog" title="报名须知" style="width:700px;height:600px;top:100px;line-height: 30px;font-size:18px;"
		data-options="iconCls:'icon-save',buttons:'#bb',resizable:true,modal:true,closable: false,">   
	    　&nbsp;&nbsp;&nbsp;1.查询相关招生政策请登陆蚌埠人民政府网关注《2018年义务教育段学校招生工作实施意见》。</br>
	　　2.请家长认真如实填写相关信息，对弄虚作假、改动户口、制造假户口、假房产证、出具假证明或拒不提供相关证明材料的学生，一经查出，取消分配资格，待招生结束后，按调剂生处理或不予安排处理。</br>
	　　3.请提前准备好户口本、房产证等证件，便于查看、填写相关信息。</br>
	　　4.本平台今年为试运行，若家长报名时发现“所在小区或村”一栏无自己房产、住所的对应选项， 请直接持相关证件于2018年8月10日至8月31日到片区学校或就近报名点（外来务工人员子女）进行现场审核。</br>
	　　5.每次填写默认保留最后一次修改内容。</br>
	　　6.填写完成后，请点击最下方的【提交】按钮。</br>
	　　7.请本地户籍的学生家长或监护人持户口簿、房产证（房产已交工但房产证未办理的出具购房合同、 购房票据及入住证明材料或其他房屋有效证件）、按照网上预报名时产生的序号到相应预报名小学进行现场审核。</br>
	　　8.请外来务工人员入学子女的家长或监护人持户口簿、房产证（房产已交工但房产证未办理的出具购房合同、 购房票据及入住证明材料或其他房屋有效证件）或夫妻双方至少一方满一年办理居住证、
		 夫妻双方至少一方在人力资源和社会保障局备案的用工单位劳动合同及保险缴纳证明或张店区个体营业执照（或公司资质）原件及复印件各1份，
		 按照网上预报名时产生的序号和预报名时间到相应预报名地点进行现场审核。   
	</div>    
	<div id="bb">
		<a href="newRegister/toRegister" class="easyui-linkbutton" style="height:40px; width:110px">
			<font size="4" style="font-weight:bold;color:black">已读</font>
		</a>
	</div>
  </div>
</body>
</html>
