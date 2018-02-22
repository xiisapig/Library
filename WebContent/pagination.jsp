<%@page import="cn.ypjalt.entity.QueryResult"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	QueryResult result = (QueryResult) request.getAttribute("queryResult");
%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css" />
<script type="text/javascript">
$(document).ready(function(){ 
	}); 
	function goUrl(page) {
		var totalPage=<%=result.getTotalPage()%>;
		if (!isNaN(page)) {
			if (page >parseInt(totalPage)) {
				$("#page").val(totalPage);	//超过总页数跳到最后一页
			} else if (page < 1) {	//小于总页数第一页
				$("#page").val(1);
			} else {
				$("#page").val(page);
			}
				$("form").submit();
				 return true; 
		}else {
			alert("请输入数字");
			return false;
		}
	}
	
</script>
<div class="pagination" style="width:95%;height:30px;text-align:left;margin:5 auto;">
	<%
		if (result.getTotalPage() > 1) {
	%>
	<%
		if (result.getNowPage() != 1) {
	%>
	<a href="#" onclick="goUrl('<%=result.getNowPage() - 1%>')">上一页</a>
	<a href="#" onclick="goUrl('1')">首页</a> 
	<%
		}
	%>

	<%
		for (int i = 1; i <= result.getTotalPage(); i++) {
				if (result.getNowPage() == i) {
	%>
		<span class="current" ><%=i%></span>
	<%
		} else {
	%> 
	<a href="#"  onclick="goUrl('<%=i%>')" <%=result.getNowPage() == i ? "class='current'": ""%>><%=i%></a>
	<%
		}
			}
	%>
	<%
		if (result.getNowPage() != result.getTotalPage()) {
	%>
	<a href="#" onclick="goUrl('<%=result.getTotalPage()%>')">尾页</a>
	<a href="#" onclick="goUrl('<%=result.getNowPage() + 1%>')">下一页</a> 
	<%
		}
	%>
	<input type="text" style="width:30px;height: 29px; text-align: center"
		id="goForm" name="goForm" onblur="goUrl(this.value)">
	<%
		}
	%>
</div>
<span>第<%=result.getNowPage()%>页/共<%=result.getTotalPage()%>页   共<%=result.getTotalNum() %>条数据</span>
