<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp"%>
<%@page import="com.elementspeed.common.sys.DistrictService"%>
<%@page import="com.elementspeed.common.sys.SchoolService"%>
<%@page import="com.elementspeed.common.sys.DomicileService"%>
<html>
<head> 
	<title>学生注册</title>
 	<link rel="stylesheet" type="text/css" id="themeLink" href="<%=path%>/static/js/jquery-easyui-1.4.5/themes/black/<%=skin %>/easyui.css">
 	<script type="text/javascript" src="<%=path%>/static/js/system/register.js"></script>
</head>
<body>
  <div align="center" style="font-size:24px;padding: 40px;">
	  <h1>淮上实验小学2018年秋季招生新生报名表</h1>
	  <h2 style="font-size:14px;padding: 15px;color:red">注意：填写报名表格后，请下载报名表PDF，如若忘记，重新输入正确格式的身份证号后，即可下载.</h1>
  </div>
  <div align="center">
	<div id="dd" class="easyui-dialog" title="报名信息填写" style="width:680px;height:630px;top:150px;"
		data-options="iconCls:'icon-save',buttons:'#bb',resizable:true,modal:true,closable: false,">   
	   <sf:form modelAttribute="sysPreRegister" id="registerForm" enctype="multipart/form-data" >
           <fieldset>
			<center>
				<table cellpadding="5" border="2" class="normalForm-table">
				   <tr>
					 	<td class="normalForm-td-label required">学生姓名: </td>
					 	<td >
					 		<sf:input path="studentName" cssClass="easyui-textbox" data-options="required:'true', validType:{length:[0,10]}" />
					    </td>
					    <td class="normalForm-td-label required">学生性别: </td>
					 	<td >
							<sf:radiobutton path="studentGender" value="1" id="studentGender1" checked="checked"/>
							<label for="studentGender1">男</label>
							<sf:radiobutton path="studentGender" value="2" id="studentGender2"/>
							<label for="studentGender2">女</label>
						</td>
					</tr>
					<tr>
					    <td class="normalForm-td-label required">出生日期: </td>
					 	<td >
					 		<sf:input path="birthDate" type="text" editable="fasle" cssClass="easyui-datebox" data-options="required:'true'" />
					    </td>
					    <td class="normalForm-td-label required">学生身份证号: </td>
					 	<td >
					 		<sf:input  path="studentIdentity" cssClass="easyui-textbox" data-options="required:'true',validType:'idCard',prompt:'18位，包含字母及数字'" />
					    </td>
					 </tr>
					 <tr>
					 	<td class="normalForm-td-label required">父亲姓名: </td>
					 	<td >
					 		<sf:input  path="fatherName" cssClass="easyui-textbox" data-options="required:'true', validType:{length:[0,10]}" />
					    </td>
					    <td class="normalForm-td-label required">联系方式: </td>
					 	<td >
					 		<sf:input  path="fatherContactInfo" cssClass="easyui-textbox" data-options="required:'true', validType:'phoneRex'" />
					    </td>
					 </tr>
					<tr>
					  	<td class="normalForm-td-label required">父亲身份证号: </td>
					 	<td >
					 		<sf:input  path="fatherIdentity" cssClass="easyui-textbox" data-options="required:'true',validType:'idCard',prompt:'18位，包含字母及数字'" />
					    </td>
					 	<td class="normalForm-td-label required">母亲姓名: </td>
					 	<td >
					 		<sf:input  path="matherName" cssClass="easyui-textbox" data-options="required:'true', validType:{length:[0,10]}" />
					    </td>
					 </tr>
					 <tr>
					    <td class="normalForm-td-label required">联系方式: </td>
					 	<td >
					 		<sf:input  path="matherContactInfo" cssClass="easyui-textbox" data-options="required:'true', validType:'phoneRex'" />
					    </td>
					    <td class="normalForm-td-label required">母亲身份证号: </td>
					 	<td >
					 		<sf:input  path="matherIdentity" cssClass="easyui-textbox" data-options="required:'true', validType:'idCard',prompt:'18位，包含字母及数字'" />
					    </td>
					 </tr>
					 <tr style="font-size: 97%;">
					 	<td class="normalForm-td-label required">居住类型:</td>
					 	<td>
							<sf:radiobutton path="domicileType" value="1"/><label for="scopeGroup">户口本地段</label>
							<sf:radiobutton path="domicileType" value="2" checked="checked"/><label for="cretGroup">购置商品房</label>
						</td>
					 	<td group="domicileType1" class="normalForm-td-label required">户口本地段: </td>
					 	<td group="domicileType1">
					 		<sf:select cssClass="easyui-combobox select-width" path="domicileId1" editable="false" data-options="required:'true', validType:'selectValueRequired'">
								<sf:option value="">--请选择--</sf:option>
								<sf:options items="<%=DomicileService.getByType(Integer.parseInt(\"1\"))%>" itemValue="id" itemLabel="domicileName"/>
							</sf:select> 
					    </td>
					    <td group="domicileType2" class="normalForm-td-label required">购置商品房: </td>
					 	<td group="domicileType2">
							<sf:select cssClass="easyui-combobox select-width" path="domicileId2" editable="false" data-options="required:'true', validType:'selectValueRequired'">
								<sf:option value="">--请选择--</sf:option>
								<sf:options items="<%=DomicileService.getByType(Integer.parseInt(\"2\"))%>" itemValue="id" itemLabel="domicileName"/>
							</sf:select> 
					    </td>
					 </tr>
					 <tr>
					 	<td class="normalForm-td-label required">区域: </td>
					 	<td >
					 		<sf:hidden path="districtName"/>
							<sf:select cssClass="easyui-combobox select-width" path="districtCode" editable="false" 
								data-options="required:'true', validType:'selectValueRequired'">
								<sf:option value="">--请选择--</sf:option>
								<sf:options items="<%=DistrictService.getAllList() %>" itemValue="districtCode" itemLabel="districtName"/>
							</sf:select> 
					    </td>
					    <td class="normalForm-td-label required">学校: </td>
					 	<td>
					 		<sf:hidden path="schoolName"/>
					 		<sf:select cssClass="easyui-combobox select-width" path="schoolCode" editable="false" data-options="required:'true', validType:'selectValueRequired'">
								<sf:option value="">--请选择--</sf:option>
								<sf:options items="<%=SchoolService.getAllList() %>" itemValue="schoolCode" itemLabel="schoolName"/>
							</sf:select> 
					    </td>
					 </tr>
					 <tr>
					 	<td class="normalForm-td-label required">监护人&学生居住地址: </td>
					 	<td colspan="3">
					 		<sf:input  style="width:90%" path="domicileAddress" prompt="输入楼号、单元、室 （此文字填写后消失）" cssClass="easyui-textbox" data-options="required:'true', validType:{length:[0,50]}" />
					    </td>
					 </tr>
					 <tr>
					 	<td  group="domicileType2" class="normalForm-td-label required">房产所有人称谓: </td>
					 	<td  group="domicileType2">
							<sf:radiobutton path="propertyOwner" value="1" id="propertyOwner1" data-options="required:'true'" checked="checked"/>
							<label for="propertyOwner1">父亲</label>
							<sf:radiobutton path="propertyOwner" value="2" id="propertyOwne2" data-options="required:'true'"/>
							<label for="propertyOwne2">母亲</label>
						</td>
					 	<td group="domicileType2" class="normalForm-td-label required">房产所有人姓名: </td>
					 	<td group="domicileType2">
					 		<sf:input path="propertyOwnerName" cssClass="easyui-textbox" data-options="required:'true', validType:{length:[0,10]}" />
					    </td>
				    </tr>
					<tr>
					    <td group="domicileType2" class="normalForm-td-label required">房产证&合同编号 : </td>
					 	<td group="domicileType2" >
					 		<sf:input  path="propertyCode" cssClass="easyui-textbox" data-options="required:'true', validType:{length:[0,20]}" />
					    </td>
					    <td group="domicileType2" class="normalForm-td-label required">房产证签发日期: </td>
					 	<td group="domicileType2">
					 		<sf:input path="propertySignDate" editable="fasle" type="text" cssClass="easyui-datebox" data-options="required:'true'" />
					    </td>
					 </tr>
					 <tr>
					    <td class="normalForm-td-label required">上传学生头像 : </td>
					 	<td>
					 		<div id="img1" style="width:200px; height:200px;background-color:#efeffb">
								<img id="img2"  width="200px" height="200px" >
							</div>
							<sf:input path="studentFace" name="studentFace" class="easyui-filebox" data-options="onChange:changeStuFace,width:200,required:true,buttonText:'头像',accept:'image/*',prompt:'最大支持3M的图片'"  />
						</td>
						<td class="normalForm-td-label required">上传合同&户口 : </td>
					 	<td >
					 		<div id="img3" style="width:200px; height:200px;background-color:#efeffb">
								<img id="img4" width="200px" height="200px" >
							</div>
							<sf:input path="propertyFile" name="propertyFile" class="easyui-filebox" data-options="onChange:changeProFile,width:200,required:true,buttonText:'合同&户口',accept:'image/*',prompt:'最大支持3M的图片'"  />
						</td>
					 </tr>
				</table>
			</center>
		</fieldset>
        </sf:form> 
	</div>    
	<div id="bb">
		<a href="javascript:void(0)"" class="easyui-linkbutton" style="height:40px; width:110px" id="btnSubmitRegistration" >
			<font size="3" style="font-weight:bold;color:black">提交</font>
		</a>
		<a href="javascript:void(0)"" class="easyui-linkbutton" iconCls="icon-export" onclick="exportPDF();" style="height:40px; width:200px">
			<font size="3" style="font-weight:bold;color:black;">下载报名表PDF</font>
		</a>
	</div>
  </div>
</body>
</html>