<%@page import="cn.ypjalt.dao.JieshuDao"%>
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
	QueryResult<Book> result = (QueryResult<Book>) request.getAttribute("queryResult");
    User u=(User)session.getAttribute("loginUser");
 
%>



<% if(request.getAttribute("message")!=null){%>
   <input type="hidden" id="message" value=${message }>
   <script type="text/javascript">
         var mes=$("#message").val();
      	  alert(mes);
  </script>
<% } %>

 <script type="text/javascript">
	function getResult() {
		$("form").submit();
	}
	$(function(){
		$(".table tr td .btn_del").click(function(){
			var del_id  = $(this).attr("id");
			var name  = $(this).attr("name");
			$("body").ios6alert({
				title : "图书馆管理系统",
				content : "你确定要删除"+name+"吗？",
				buttonText : {
					Delete : "删除",
					No : "不"
				},
				type : 2,
				onClickYes : function(){
					$.ajax({
						   type: "POST",
						   url: "${pageContext.request.contextPath}/book/BookAction",
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
		$(".table tr td .btn_jieshu").click(function(){
			var id  = $(this).attr("id");
			var name  = $(this).attr("name");
		/* 	window.location.href="${pageContext.request.contextPath}/book/BookAction?action=jieshu&id="+id""; */
	 		$("body").ios6alert({
				title : "图书馆管理系统",
				content : "你确定要借"+name+"吗？",
				buttonText : {
				Yes : "确定",
				No :  "取消",
				},
				type : 1,
				onClickYes : function(){
					$.ajax({
						   type: "POST",
						   url: "${pageContext.request.contextPath}/book/BookAction",
						   data: "action=jieshu&id="+id,
						   success: function(){
							   $("body").ios6alert({
									content : "借书成功"
								});
							   //var x=$().val;
							  // function $(v){return document.getElementById(v);}
							   var x=document.getElementById("store_"+id).innerHTML;
							   x=parseInt(x)-1;
							   document.getElementById("store_"+id).innerHTML=x;
							   $("#"+id).hide();
						   },
						   error:function(){
							   $("body").ios6alert({
									content : "不要太贪心哦，一人只能借一本"
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
			window.location.href="${pageContext.request.contextPath}/book/BookAction?action=modifyUI&modify_id="+modify_id;
		});	
	});
</script> 
</head>

<body>
	<form action="${pageContext.request.contextPath}/book/BookAction?action=searchUI" method="post"
		style="margin-top: 5px">
		<input type="hidden" name="page" id="page"
			value="<%=result.getNowPage()%>" />		
			<table align="center">
			按出版社查询：
			<input type="text" width="200" name="pub"  value="${pub}"/>
			按图书名查询：
			<input type="text" width="200" name="name"  value="${bname}"/>
			按图书作者查询：
			<input type="text" width="200"  name="author"  value="${bauthor}"/>
			<input type="submit" style="margin-left: 5px;"    value="查询"/>
		</table>
	</form>

 
	<table align="center" width="100%" cellpadding="0" cellspacing="0" class="table">
		<tr height="30" style="background-color: #52be7f">
			<td width="15%" align="center">图书编号</td>
			<td width="10%" align="center">图书名称</td>
			<td width="10%" align="center">图书作者</td>
			<td width="20%" align="center">图书类型</td>
			<td width="5%" align="center">图书价格</td>
			<td width="20%" align="center">出版社</td>
			<td width="5%" align="center" >存储量</td>
			<td width="15%" align="center"></td>
		</tr>
		<%
			for (int i = 0; i < result.getResultList().size(); i++) {
				Book b = result.getResultList().get(i);
		%>

		<tr id="line_<%=b.getBid()%>" height="35" onmouseover="this.style.background='#eeeeee';"
			onmouseout="this.style.background='#FFFFFF';">
			<td align="center"><%=b.getBid()%></td>
			<td align="center"><%=b.getBname()%></td>
			<td align="center"><%=b.getBauthor()%></td>
			<td align="center"><%=b.getBtype()%></td>
			<td align="center"><%=b.getBprice()%></td>
			<td align="center"><%=b.getBpublisher()%></td>
			<td align="center" id="store_<%=b.getBid() %>"><%=b.getBstore()%></td>
			<td>
		  <%
		 	JieshuDao jd=new JieshuDao();
 			if (u.isRole() ) {
 		  %>  
			<input type="button" class="btn_modify" title="资料修改" id="<%=b.getBid()%>"   />
 		   	<input type="button" class="btn_del" title="删除" id="<%=b.getBid()%>"   name="<%=b.getBname()%>"/> 
 	   <%}else if(b.getBstore()>0&&!u.isRole()&&jd.queryById(u.getId(), b.getBid())==null){%> 
 	 	  <input type="button" value="借书" class="btn_jieshu" id="<%=b.getBid()%>" name="<%=b.getBname()%>"/>
 	   <%} %>
 			</td>
		</tr>
		<%
			}
		%>
	</table>
	<%if(!u.isRole()){ %>
	 <input type="button" class="btn btn-primary" style="margin-top: 10px"    value="返回首页" onclick="javascript:window.location.href='${pageContext.request.contextPath}/index.jsp'"/>
	<%} %>
		<jsp:include page="../pagination.jsp"></jsp:include>

</body>
</html>
