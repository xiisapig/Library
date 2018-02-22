<%@page import="cn.ypjalt.entity.Jieshu"%>
<%@page import="cn.ypjalt.entity.Book"%>
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
	QueryResult<Jieshu> result = (QueryResult<Jieshu>) request.getAttribute("queryResult");
    User u=(User)session.getAttribute("loginUser");
 
%>

 <script type="text/javascript">
	function getResult() {
		$("form").submit();
	}
	$(function(){
			$(".table tr td .btn_huanshu").click(function(){
			var id  = $(this).attr("id");
			var bid  = $(this).attr("name");
			var bname  = $(this).attr("bname");
			$("body").ios6alert({
				title : "图书馆管理系统",
				content : "你确定要还"+bname+"吗？",
				buttonText : {
				Yes : "确定",
				No :  "取消",
				},
				type : 1,
				onClickYes : function(){
					$.ajax({
						   type: "POST",
						   url: "${pageContext.request.contextPath}/book/JieshuAction",
						   data: "action=delete&id="+id+"&bid="+bid,
						   success: function(){
							   $("body").ios6alert({
									content : "还书成功"
								});
							   $("#line_"+id).hide();
							   getResult();
						   },
						   
						   error:function(){
							   $("body").ios6alert({
									content : "还书失败"
								});
							   getResult();
						   }
						   //window.opener.location.reload();
						});
				},
				onClickNo : function(){
					return false;
				}
				 
		}); 
		});
			
		$(".table tr td .btn_modify").click(function(){
			var modify_id  = $(this).attr("id");
			window.location.href="${pageContext.request.contextPath}/book/JieshuAction?action=modifyUI&modify_id="+modify_id;
		});	
	});
</script> 
</head>

<body>
	<form action="${pageContext.request.contextPath}/book/JieshuAction?action=searchUI" method="post"
		style="margin-top: 5px">
		<input type="hidden" name="page" id="page"
			value="<%=result.getNowPage()%>" />		
		<%if(u.isRole()){ %>
			<table align="center">
			按借书人姓名查询：
			<input type="text" width="200" name="name"  value="${name}"/>
			<input type="submit" style="margin-left: 5px;"    value="查询"/>
		</table>
	</form>
		<%} %>
 
	<table align="center" width="100%" cellpadding="0" cellspacing="0" class="table">
		<tr height="30" style="background-color: #52be7f">
			<td width="15%" align="center">借书人ID</td>
			<td width="10%" align="center">借书人姓名</td>
			<td width="10%" align="center">借书ID</td>
			<td width="20%" align="center">所借书名</td>
			<td width="20%" align="center" >借书时间</td>
			<td width="15%" align="center"></td>
		</tr>
		<%
			for (int i = 0; i < result.getResultList().size(); i++) {
				Jieshu b = result.getResultList().get(i);
				if(u.getId()==b.getUser().getId()||u.isRole()){
		%>

		<tr id="line_<%=u.getId()%>" height="35" onmouseover="this.style.background='#eeeeee';"
			onmouseout="this.style.background='#FFFFFF';">
			<td align="center"><%=b.getUser().getId()%></td>
			<td align="center"><%=b.getUser().getName()%></td>
			<td align="center"><%=b.getBook().getBid()%></td>
			<td align="center"><%=b.getBook().getBname()%></td>
			<td align="center"><%=b.getJtime()%></td>
			<td>
			<%if(!u.isRole()){ %>
			  <input type="button" value="还书" class="btn_huanshu" id="<%=u.getId()%>" name="<%=b.getBook().getBid()%>"
			  bname="<%=b.getBook().getBname()%>" />
			<%} %>
			</td>
		</tr>
		<%
		}
				}
		%>
	</table>
	 <input type="button" class="btn btn-primary" style="margin-top: 10px"    value="返回首页" onclick="javascript:window.location.href='${pageContext.request.contextPath}/index.jsp'"/>
	<jsp:include page="../pagination.jsp"></jsp:include>

</body>
</html>
