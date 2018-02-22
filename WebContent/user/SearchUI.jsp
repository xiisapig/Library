<%@page import="cn.ypjalt.entity.QueryResult"%>
<%@page import="cn.ypjalt.entity.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>

<title>用户查询</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
<link href="${pageContext.request.contextPath}/css/ios6alert.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/ios6alert.min.js" language="javascript"></script>

<%
	QueryResult<User> result = (QueryResult<User>) request.getAttribute("queryResult");
    User u=(User)session.getAttribute("loginUser");
%>

 <script type="text/javascript">
	function getResult() {
		$("form").submit();
	}
	$(function(){
		$(".table tr td .btn_del").click(function(){
			var del_id  = $(this).attr("id");
			$("body").ios6alert({
				title : "图书馆管理系统",
				content : "你确定要删除"+del_id+"吗？",
				buttonText : {
					Delete : "删除",
					No : "不"
				},
				type : 2,
				onClickYes : function(){
					$.ajax({
						   type: "POST",
						   url: "${pageContext.request.contextPath}/user/UserAction",
						   data: "action=delete&del_id="+del_id,
						   success: function(){
							   $("body").ios6alert({
									content : "删除成功"
								});
						     $("#line_"+del_id).hide();
						   },
						   error:function(){
							   $("body").ios6alert({
									content : "删除失败"
								});
						   }
						});
				},
				onClickNo : function(){
					return false;
				}
		});
		});
		$(".table tr td .btn_modify").click(function(){
			var modify_id  = $(this).attr("id");
			window.location.href="${pageContext.request.contextPath}/user/UserAction?action=modifyUI&modify_id="+modify_id;
		});	
	});
</script> 
</head>

<body>
	<form action="${pageContext.request.contextPath}/user/UserAction?action=searchUI" method="post"
		style="margin-top: 5px">
		<input type="hidden" name="page" id="page"
			value="<%=result.getNowPage()%>" />		
		<%
			if (u.isRole()) {
		%>
		<table align="center">
			按用户名ID查询：
			<input type="text" width="200" id="id" name="id" onchange="getResult();" value="${id}"> 
			按姓名查询：
			<input type="text" width="200" id="name" name="name" value="${name}"
				onchange="getResult();">
		</table>
		<%
			}
		%>
	</form>

 
	<table align="center" width="100%" cellpadding="0" cellspacing="0" class="table">
		<tr height="30" style="background-color: #52be7f">
			<td width="15%" align="center">用户名ID</td>
			<td width="10%" align="center">姓名</td>
			<td width="10%" align="center">性别</td>
			<td width="20%" align="center">专业</td>
			<td width="5%" align="center">班级</td>
			<td width="20%" align="center">电话</td>
			<td width="5%" align="center">角色</td>
			<td width="15%" align="center"></td>
		</tr>
		<%
			for (int i = 0; i < result.getResultList().size(); i++) {
				User s = result.getResultList().get(i);
		%>

		<tr id="line_<%=s.getId()%>" height="35" onmouseover="this.style.background='#eeeeee';"
			onmouseout="this.style.background='#FFFFFF';">
			<td align="center" id="userId"><%=s.getId()%></td>
			<td align="center" ><%=s.getName()%></td>
			<td align="center"><%=s.getSex()%></td>
			<td align="center"><%=s.getDepart()%></td>
			<td align="center"><%=s.getClasses()%></td>
			<td align="center"><%=s.getTel()%></td>
			<td align="center"><%=s.isRole()?"管理员":"普通用户"%></td>
			<td>
			<%if(u.getId()==s.getId()||!s.isRole()) {%>
			<input type="button" class="btn_modify" title="资料修改" id="<%=s.getId()%>"/>
			<%} %>
		  <%
 			if (u.isRole() && u.getId()!=s.getId()&&!s.isRole()) {
 		  %> 
 		   	<input type="button" class="btn_del" title="删除" id="<%=s.getId()%>" /> 
 		  <%
 			  }
 		  %>
 			</td>
		</tr>
		<%
			}
		%>
	</table>
	
		<jsp:include page="../pagination.jsp"></jsp:include>

</body>
</html>
