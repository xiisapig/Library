<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>用户登录</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login_default.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login_styles.css">
	<script src="${pageContext.request.contextPath}/js/jquery-1.12.1.min.js"></script>
</head>
<body>
<%
session.invalidate();
if(request.getAttribute("message")!=null){%>
   <input type="hidden" id="message" value=${message }>
   <script type="text/javascript">
         var mes=$("#message").val();
      	  alert(mes);
  </script>
<% } %>
	<article class="htmleaf-container">
		<header class="htmleaf-header">
			<h1>欢迎来到图书馆管理系统<span>请登录</span></h1>
		</header>
		<div class="panel-lite">
		  <div class="thumbur">
		    <div class="icon-lock"></div>
		  </div>
		  <h4>用户登录</h4>
		  <form action="${pageContext.request.contextPath}/user/UserAction?action=login" method="post">
		  <div class="form-group">
		    <input required="required" class="form-control" name="id" value="${userId }"/>
		    <label class="form-label">用户名    </label>
		  </div>
		  <div class="form-group">
		    <input type="password" required="required" class="form-control" name="password"/>
		    <label class="form-label">密　码</label>
		  </div>
		  
		
		   <div class="form-group" >
		  	<img src="${pageContext.request.contextPath}/AuthCodeAction" style="float:right;" onclick="this.src='${pageContext.request.contextPath}/AuthCodeAction?'+Math.random()" />
		    <input type="text" required="required" class="form-control" name="vcode"/>
		    <label class="form-label">验证码</label>
		  </div> 
		  <br />
		 <!--  <a href="${pageContext.request.contextPath}/user/add.jsp">没有账号 ?注册  </a> -->
		 <br>
		  <button class="floating-btn" type="submit" ><i class="icon-arrow"></i></button>
		</form>
		</div>
	</article>
</body>
</html>
