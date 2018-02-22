<%@page import="cn.ypjalt.entity.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>注册</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/dist/css/formValidation.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/vendor/default.css">
<%
	User u = (User) request.getAttribute("modifyUser");//接收传过来的user
	User loginUser=(User)session.getAttribute("loginUser");//接收登陆过来的user
%>

</head>
<body>
	
	<div class="jq22-container" >
		<div class="container">
			<div class="row">
				<div class="col-lg-8 col-lg-offset-2">
					<div class="page-header">
					<h2>修改用户资料</h2>
						${message }
					</div>
					<form id="defaultForm" method="post" class="form-horizontal"
						action="${pageContext.request.contextPath}/user/UserAction?action=modify">
						<div class="alert alert-success" style="display: none;"></div>
						<div class="form-group">
							<label class="col-lg-3 control-label">用户名ID</label>
							<div class="col-lg-5" style="margin-top: 7px;">
								<input type="hidden" class="form-control" name="id"
									value="${modifyUser.id }" /> ${modifyUser.id }
							</div>
						</div>
<!--                 <div class="form-group">
							<label class="col-lg-3 control-label">密码</label>
							<div class="col-lg-5">
								<input type="password" class="form-control" name="pwd1"
									value="${modifyUser.pwd}" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label">确认密码</label>
							<div class="col-lg-5">
								<input type="password" class="form-control" name="pwd2"
									value="${modifyUser.pwd}" />
							</div>
						</div>
 -->
						<div class="form-group">
							<label class="col-lg-3 control-label">姓名</label>
							<div class="col-lg-5" style="margin-top: 7px;">
							<%if(!loginUser.isRole()){ %>
								<input type="hidden" class="form-control" name="name"
									value="${modifyUser.name}" />${modifyUser.name}
							<%}else{ %><!--管理员可以更改名字 -->
									<input type="text" class="form-control" name="name"
									value="${modifyUser.name}" />
									<%} %>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label">性别</label>
							<div class="col-lg-5">
								<select class="form-control" name="sex">
									<option value="">-- 选择性别 --</option>
									<option value="男" <%=u.getSex().equals("男") ? "selected" : ""%>>男</option>
									<option value="女" <%=u.getSex().equals("女") ? "selected" : ""%>>女</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-3 control-label">专业</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="depart"
									value="${modifyUser.depart}" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-3 control-label">班级</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="classes"
									value="${modifyUser.classes}" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label">电话</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="tel"
									value="${modifyUser.tel}" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-9 col-lg-offset-3">
								<input type="button" class="btn btn-primary" value="点击返回" onclick="history.go(-2);"/>
								<button type="submit" class="btn btn-primary">确认</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>

	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/vendor/jquery/jquery.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/vendor/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/dist/js/formValidation.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/dist/js/framework/bootstrap.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/dist/js/language/zh_CN.js"></script>
	<script type="text/javascript">
		$(document).ready(
				function() {
					$('#defaultForm').formValidation({
						message : 'This value is not valid',
						icon : {
							valid : 'glyphicon glyphicon-ok',
							invalid : 'glyphicon glyphicon-remove',
							validating : 'glyphicon glyphicon-refresh'
						},
						fields : {
							id : {
								message : '请输入您的ID',
								validators : {
									notEmpty : {
										message : '用户名不能为空'
									},
									stringLength : {
										min : 8,
										max : 8,
										message : '用户名必须为八位'
									},

									regexp : {
										regexp : /^[0-9]+$/,
										message : '用户名只能为数字'
									}
								}
							},
							pwd1 : {
								validators : {
									notEmpty : {
										message : '密码不能为空'
									},
									stringLength : {
										min : 6,
										max : 12,
										message : '密码长度6至12位'
									},

								}
							},
							pwd2 : {
								validators : {
									notEmpty : {
										message : '密码不能为空'
									},

									identical : {
										field : 'pwd1',
										message : '两次密码不一样'
									}

								}
							},
							sex : {
								validators : {
									notEmpty : {
										message : '性别不能为空'
									}
								}
							},
							name : {
								validators : {
									notEmpty : {
										message : '姓名不能为空'
									}
								}
							},
							depart : {
								validators : {
									notEmpty : {
										message : '专业不能为空'
									}
								}
							},
							classes : {
								validators : {
									notEmpty : {
										message : '班级不能为空'
									}
								}
							},
							tel : {
								validators : {
									notEmpty : {
										message : '电话不能为空'
									}
								}
							},

						}
					});
		/* 	.on('success.form.fv',function(e) {
			// Prevent submit form
				e.preventDefault();
				var $form = $(e.target), validator = $form.data('formValidation');
				
								// Use Ajax to submit form data
								/*  $.post($form.attr('action'), $form.serialize(), function(result) {
								     console.log(result);
								 }, 'json'); */ 
						
				});
	</script>
</body>
</html>
