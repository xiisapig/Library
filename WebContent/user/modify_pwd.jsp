<%@page import="cn.ypjalt.entity.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<title>修改个人信息</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/platform-1.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/easyform/easyform.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/tab.css">

<script src="${pageContext.request.contextPath}/js/jquery-1.12.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/easyform/easyform.js"></script>
<%
	User u = (User) session.getAttribute("loginUser");//接收传过来的user
%>

</head>

<body>
<% if(request.getAttribute("message")!=null){%>
   <input type="hidden" id="message" value=${message }>
   <script type="text/javascript">
         var mes=$("#message").val();
         alert(mes);
  </script>
<% } %>

<input type="hidden" id="u_pwd" name="u_pwd" value="${loginUser.pwd }">


	

	<div class="page">

		<div class="header">

			<a class="link title-ani" data-letters="修改个人密码" id="head">修改个人密码</a>
		</div>


		<div class="form-div">
			<form id="reg-form" action="UserAction?action=modify_pwd" method="post">
				<table>
					<tr>
						<td>姓名</td>
						<td><span style="float: left;text-align: left;">${loginUser.name }</span></td>
					</tr>
					<tr>
						<td>旧密码</td>
						<td><input name="old_pwd" type="password" id="old_pwd" 
							data-easyform="length:6 16;equal:#u_pwd;" data-message="与旧密码不一致"
							data-easytip="class:easy-blue;"></td>
					</tr>
						<tr>
						<td>新密码</td>
						<td><input name="new_pwd1" type="password" id="new_pwd1" 
							data-easyform="length:6 16;" data-message="密码必须为6—12位"
							data-easytip="class:easy-blue;"></td>
					</tr>
					<tr>
						<td>确认密码</td>
						<td><input name="new_pwd2" type="password" id="new_pwd2" 
							data-easyform="length:6 16;equal:#new_pwd1;" data-message="两次密码输入要一致"
							data-easytip="class:easy-blue;"></td>
					</tr>
				
				</table>

				<div class="buttons" style="margin-top: 50px;">
					<input value="修改" type="submit"
						style="margin-right:50px; margin-top:20px;">
				<a href="../index.jsp"><input type="button" value="点击返回" /></a>	
				</div>
					
			
				<br class="clear">
			</form>
		</div>

		<script>
			$(document).ready(function() {
				var v = $('#reg-form').easyform();
				$('#demo-form').easyform();
				v.is_submit = false;
				v.error = function(ef, i, r) {
					//console.log("Error事件：" + i.id + "对象的值不符合" + r + "规则");
				};
				v.success = function(ef) {
					//console.log("成功");
				};
				v.complete = function(ef) {
					console.log("完成");
				};
				$('#tip-test1').easytip();
				$('#tip-test2').easytip();
				$('#tip-test3').easytip();
				$('#tip-test4').easytip();
			});

			function ajax_demo(p) {
				$("#id").trigger("easyform-ajax", true);
			}
		</script>
</body>