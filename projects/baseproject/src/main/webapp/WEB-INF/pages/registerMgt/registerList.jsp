<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp"%>
<%@page import="com.elementspeed.common.sys.SchoolService"%>
<%@page import="com.elementspeed.common.sys.DistrictService"%>
<html>
<head>
<script type="text/javascript" src="<%=path%>/static/js/registerMgt/registerList.js?version=<%=version%>"></script>
</head>
<body>
	<!-- 查询条件 -->
	<div id="btnToolbar">
		<sf:form modelAttribute="sysPreRegister" id="searchForm" method="post">
			<div class="div-search">
				学生姓名：<sf:input path="studentName" cssClass="easyui-textbox" data-options="validType:{length:[0,10]}"/>
				学生身份证号：<sf:input path="studentIdentity" cssClass="easyui-textbox" data-options="validType:{length:[0,18]}"/>
				学校：<sf:select cssClass="easyui-combobox select-width" path="schoolCode" editable="false">
						<sf:option value="">--请选择--</sf:option>
						<sf:options items="<%=SchoolService.getAllList() %>" itemValue="schoolCode" itemLabel="schoolName"/>
					</sf:select> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="btnQuery">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="sysReset('searchForm')">重置</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-more" onclick="sysShowMore()">更多</a> 
			</div>
			<div class="div-search" group="seniorSerch">
				<li class="li-adaptive">
					<nobr>区域：
						<sf:select cssClass="easyui-combobox select-width" path="districtCode" editable="false">
							<sf:option value="">--请选择--</sf:option>
							<sf:options items="<%=DistrictService.getAllList() %>" itemValue="districtCode" itemLabel="districtName"/>
						</sf:select> 
					</nobr>
					<nobr>
						性别：<sf:select cssClass="easyui-combobox select-width" path="studentGender" editable="false">
							<sf:option value="">--请选择--</sf:option>
							<sf:option value="1">--男--</sf:option>
							<sf:option value="2">--女--</sf:option>
						</sf:select> 
					</nobr>
					<nobr>
						父亲姓名：<sf:input path="fatherName" cssClass="easyui-textbox" data-options="validType:{length:[0,10]}"/>
					</nobr>
					<nobr>
						母亲姓名：<sf:input path="matherName" cssClass="easyui-textbox" data-options="validType:{length:[0,10]}"/>
					</nobr>
				</li>
			</div>
		</sf:form>
		<!-- <a href="javascript:void(0)" id="btnDelete" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a> -->
		<a href="javascript:void(0)" id="expUser" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="expRegisterInfo()">导出</a>
	</div>
	<!-- 查询结果列表 -->
	<table id="registerGrid" class="easyui-datagrid">
		<thead>
			<tr>
				<!-- <th data-options="field:'operation',width:'80', align:'center'" formatter="RegisterGrid.formatter.opteration">操作</th> -->
				<th data-options="field:'id', hidden: true"></th>
				<th data-options="field:'preOrderNo', width:'150', align:'center', sortable:'true'">报名号</th>
				<th data-options="field:'studentName', width:'120', align:'center', sortable:'true'">学生姓名</th>
				<th data-options="field:'birthDate', width:'120', align:'center', sortable:'true'">学生出生日期</th>
				<th data-options="field:'studentGender', width:'80', align:'center', sortable:'true'" formatter='RegisterGrid.formatter.studentGender'>性别</th>
				<th data-options="field:'studentIdentity', width:'180', align:'center', sortable:'true'">身份证号</th>
				<th data-options="field:'districtName', width:'120', align:'center', sortable:'true'">区域</th>
				<th data-options="field:'schoolName', width:'180', align:'center', sortable:'true'">学校</th>
				<th data-options="field:'fatherName', width:'120', align:'center', sortable:'true'">父亲姓名</th>
				<th data-options="field:'fatherContactInfo', width:'150', align:'center', sortable:'true'">父亲联系方式</th>
				<th data-options="field:'fatherIdentity', width:'180', align:'center', sortable:'true'">身份证号</th>
				<th data-options="field:'matherName', width:'120', align:'center', sortable:'true'">母亲姓名</th>
				<th data-options="field:'matherContactInfo', width:'150', align:'center', sortable:'true'">母亲联系方式</th>
				<th data-options="field:'matherIdentity', width:'180', align:'center', sortable:'true'">身份证号</th>
				<th data-options="field:'domicileName', width:'150', align:'center', sortable:'true'">居住地名称</th>
				<th data-options="field:'domicileAddress', width:'200', align:'center', sortable:'true'">居住地址</th>
				<th data-options="field:'propertyOwner', width:'120', align:'center', sortable:'true'" formatter='RegisterGrid.formatter.propertyOwner'>房产所有人 </th>
				<th data-options="field:'propertySignDate', width:'120', align:'center', sortable:'true'">房产证签发日期 </th>
				<th data-options="field:'propertyOwnerName', width:'120', align:'center', sortable:'true'">房产所有人姓名</th>
				<th data-options="field:'propertyCode', width:'150', align:'center', sortable:'true'">房产证编号</th>
				<th data-options="field:'studentFaceName', width:'200', align:'center', sortable:'true'" formatter="RegisterGrid.formatter.studentFaceName">头像</th>
				<th data-options="field:'studentFaceUrl', width:'150',  hidden:'true'">头像Path</th>
				<th data-options="field:'propertyFileName', width:'200', align:'center', sortable:'true'" formatter="RegisterGrid.formatter.propertyFileName">户口本或房屋合同</th>
				<th data-options="field:'propertyFileUrl', width:'150', hidden:'true'">户口本或房屋合同附件Path</th>
			</tr>
		</thead>
	</table>
</html>