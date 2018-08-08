<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp"%>
<%  
	String serverType = "测试环境";
%>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>${page_title}</title>
	<link href="<%=path%>/static/css/pages/css/base.css" rel="stylesheet">
	<link href="<%=path%>/static/css/pages/css/<%=skin%>/platform.css"rel="stylesheet">
	<script type="text/javascript" src="<%=path%>/static/js/system/login/layout.js"></script>
	<style type="text/css">
		.tabs-panels {
			padding: 5px 5px 5px 5px
		}
	</style>
</head>
<body>
	<div class="container">
		<div id="pf-hd">
			<div class="pf-logo">
				<img src="<%=path%>/static/css/pages/images/main/<%=skin%>/logo.png" alt="logo">
			</div>
			<div class="pf-nav-wrap">
				<div class="pf-nav-ww">
					<ul class="pf-nav">
						<c:forEach var="module" varStatus="status" items="${modules}">
							<c:if test="${status.first}">
								<li class="pf-nav-item home current" data-menu="${module.id}">
							</c:if>
							<c:if test="${!status.first}">
								<li class="pf-nav-item home" data-menu="${module.id}">
							</c:if>
							<a href="javascript:;"> <span class="iconfont">${module.icon}</span>
								<span class="pf-nav-title">${module.i18nText}</span>
							</a>
							</li>
						</c:forEach>
					</ul>
				</div>
				<a href="javascript:;" class="pf-nav-prev disabled iconfont">&#xe606;</a>
				<a href="javascript:;" class="pf-nav-next iconfont">&#xe607;</a>
			</div>
			<div class="pf-user">
				<div class="pf-user-photo">
					<img src="<%=path%>/static/css/pages/images/main/blue/user.png">
				</div>
				<h4 class="pf-user-name ellipsis" title="masn">Futur</h4>
				<i class="iconfont xiala">&#xe607;</i>
				<div class="pf-user-panel">
					<ul class="pf-user-opt">
						<a href="javascript:;" id="changeInfo">
							<li><i class="iconfont">&#xe60d;</i> <span
								class="pf-opt-name">用户信息</span></li>
						</a>
						<a href="javascript:;" id="changePwd">
							<li class="pf-modify-pwd"><i class="iconfont">&#xe634;</i> <span
								class="pf-opt-name">修改密码</span></li>
						</a>
						<a href="javascript:;"> <!-- onclick="logout()" -->
							<li class="pf-logout"><i class="iconfont">&#xe60e;</i> <span
								class="pf-opt-name">退出</span></li>
						</a>
					</ul>
				</div>
			</div>
		</div>
		<div id="pf-bd">
			<c:forEach var="module" varStatus="status" items="${modules}">
				<c:if test="${status.first}">
					<div id="pf-sider" parentId="${module.id}" submenu="true">
				</c:if>
				<c:if test="${!status.first}">
					<div id="pf-sider" parentId="${module.id}" submenu="true"
						hidden="true">
				</c:if>
				<h2 class="pf-model-name">
					<span class="iconfont">${module.icon}</span> <span class="pf-name">${module.i18nText}</span>
					<span class="toggle-icon"></span>
				</h2>
				<ul class="sider-nav">
					<c:forEach var="level2" items="${module.children}">
						<c:if test="${not empty level2.children}">
							<li><a href="javascript:;" level="second"> <span
									class="iconfont sider-nav-icon">${level2.icon}</span> <span
									class="sider-nav-title">${level2.i18nText}</span> <i
									class="iconfont">&#xe642;</i>
							</a>
								<ul class="sider-nav-s">
									<c:forEach var="level3" items="${level2.children}">
										<li><a href="#" level='third' data-url="${level3.url}"
											data-text="${level3.i18nText}">${level3.i18nText}</a></li>
									</c:forEach>
								</ul></li>
						</c:if>
						<c:if test="${empty level2.children}">
							<li><a href="#" level="second" data-url="${level2.url}"
								data-text="${level2.i18nText}"> <span
									class="iconfont sider-nav-icon">${level2.icon}</span>
									${level2.i18nText}
							</a></li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</c:forEach>
		<div id="pf-page">
			<div class="easyui-tabs1" id="tabs" style="width: 100%; height: 100px;"></div>
		</div>
	</div>
	<div id="pf-ft">
		<div class="system-name">
			<i class="iconfont"></i> <span> 报名注册管理系统
				&nbsp;&nbsp;&nbsp;&nbsp; 您当前登录的为<%=serverType %>
			</span>
		</div>
	</div>
</body>
</html>