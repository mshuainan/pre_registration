<!DOCTYPE html>
<%@page import="com.elementspeed.system.webpage.controller.WebPageCtrl"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/web/webpageTaglib.jsp"%>

<html>
<head>
	<title><s:message code="portal.system.name"/></title>
	<link href="<%=path%>/static/js/thirdplugin/slides/jquery.slides.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=path%>/static/js/thirdplugin/slides/jquery.slides.min.js"></script>
</head>
	<%@ include file="/WEB-INF/pages/web/header.jsp"%>
	<%@ include file="/WEB-INF/pages/web/navigationbar.jsp"%>
	
	<!-- 图片区域 -->
	<div class="bigpic" style="height: 340px;">
		<div style="background-color:#c7c7c7;">
			<div id="slides">
			  <c:forEach items="${systemConfig.portalCarouselList}" var="carousel">
			    <img src="<%=path%>${carousel}" style="float: left;width:100%;height: 340px;">
			  </c:forEach>
				
			</div>
		</div>
	</div>
	
	<div style="margin-top: 20px;">
		<div class="ml">
			<h1>
				<a href="<%=path%>/system/webPage/toMore/<%=ContentType.NOTICE.getValue()%>" target="_blank" class="more"><s:message code="common.btn.more"/></a>
				<s:message code="portal.homepage.purchaseannouncement"/>
			</h1>
			<ul id="notice"></ul>
		</div>
	
		<div class="ml">
			<h1> 
				<a href="<%=path%>/system/webPage/toMore/<%=ContentType.BUY.getValue()%>" target="_blank" class="more"><s:message code="common.btn.more"/></a>
				<s:message code="portal.homepage.siteannouncement"/> 
			</h1>
			<ul id="buy"></ul>
		</div>
	
		<div class="ml">
			<h1>
				<a href="<%=path%>/system/webPage/toMore/<%=ContentType.NEW.getValue()%>" target="_blank" class="more"><s:message code="common.btn.more"/></a>
				<s:message code="portal.homepage.newspolicy"/> 
			</h1>
			<ul id="new"></ul>
		</div>
	
		<div class="ml">
			<h1>
				<a href="<%=path%>/system/webPage/toMore/<%=ContentType.USERS.getValue()%>" target="_blank" class="more"><s:message code="common.btn.more"/></a>
				<s:message code="portal.homepage.usernote"/> 
			</h1>
			<ul id="users"></ul>
		</div>
	</div>
	
	<div style="margin-top: 10px;margin-bottom: 10px;">
		<div style="clear: both; height: 0px; line-height: 0px; font-size: 0px;"></div>
		<div class="freinds">
			<div class="title"><s:message code="portal.homepage.friendslink"/></div>
			<ul>
			<c:forEach items="${systemConfig.portalFriendlylinkMap}" var="linkMap">
			<li><a href="${linkMap.value }" target="_blank">${linkMap.key }</a></li>
			</c:forEach>
			
			</ul>
		</div>
	</div>
			
	<%@ include file="/WEB-INF/pages/web/footer.jsp"%>
</html>

<script type="text/javascript">

$(function() {
	$('#slides').slidesjs({
	    height: 340,
		play : {
			active : true,
			auto : true,
			interval : 2000,
			swap : true
		}
	});
	
	//$('#homePage').attr('style', 'color:black;');
});

$.ajax({
	type : "post",
	url : "<%=serviceUrl%>/system/webPage/query",
    contentType: "application/json",
    success:function(response){
    	createContent('notice', response['<%=ContentType.NOTICE.getValue()%>']);
    	createContent('buy', response['<%=ContentType.BUY.getValue()%>']);
    	createContent('new', response['<%=ContentType.NEW.getValue()%>']);
    	createContent('users', response['<%=ContentType.USERS.getValue()%>']);
    }
});

function createContent(containerId, arr) {
	var container = $('#' + containerId);
	var length = arr.length;
	if(arr.length > <%=WebPageCtrl.PAGE_SIZE%> ) {
		length = <%=WebPageCtrl.PAGE_SIZE%>;
	}
	for(var i = 0; i < length; i++) {
		var content = "<div class='date'>" + arr[i].timeStr + 
			"</div><a target='_blank' href='<%=path%>/system/webPage/toDesc/" + arr[i].value + "'>" + 
			arr[i].text + "</a>";
			
		if(arr[i].highLight)
			content += '&nbsp;<img src="<%=path%>/static/img/new.gif">';
			
		container.append("<li>" + content + "</li>");
	}
}

function toPage(type) {
	window.location.href = CtxVar.path + '/system/webPage/toPage/' + type; 
}
</script>
