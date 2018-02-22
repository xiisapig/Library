<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>菜单 </title>
	<script src="${pageContext.request.contextPath}/js/jquery-1.12.1.min.js"></script>
	<script type="text/javascript" src='${pageContext.request.contextPath}/js/style.js'></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="nav">
		<div class="nav_li">
			<ul>
				<li><a href="${pageContext.request.contextPath}/user/UserAction?action=addUI" target="mainFrame">添加用户</a></li>
				<li><a href="${pageContext.request.contextPath}/user/UserAction?action=searchUI" target="mainFrame">查找用户</a></li>
				<li><a href="${pageContext.request.contextPath}/index.jsp" target="_parent">返回首页</a></li>
				<li><a href="${pageContext.request.contextPath}/login.jsp" target="_parent">退出登录</a></li>
			</ul>
		</div>
	</div>
  
  
  </body>
</html>
