<%@page import="cn.ypjalt.entity.QueryResult"%>
<%@page import="cn.ypjalt.entity.Advice"%>
<%@page import="cn.ypjalt.entity.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    <title>建议</title>
   
	<%
	User u = (User) session.getAttribute("loginUser");//接收传过来的user
	%>
  </head>
  
  <body >
   <div style="width: 100px; height:20px; text-align: center;" align="center">${message }</div>
   <%if(u.isRole()){ 
		QueryResult<Advice> result = (QueryResult<Advice>) request.getAttribute("queryResult");
   %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
<link href="${pageContext.request.contextPath}/css/ios6alert.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/ios6alert.min.js" language="javascript"></script>
<script type="text/javascript">
	function getResult() {
		$("form").submit();
	}
	$(function(){
		$(".table tr td .btn_del").click(function(){
			var del_id  = $(this).attr("id");
			$("body").ios6alert({
				title : "图书馆管理系统",
				content : "你确定要删除"+del_id+"的建议吗？",
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
	
	});
</script> 
   	<form action="${pageContext.request.contextPath}/AdviceAction?action=show" method="post"
		style="margin-top: 5px">
		<input type="hidden" name="page" id="page"
			value="<%=result.getNowPage()%>" />		
			</form>
    <table align="center" width="100%" cellpadding="0" cellspacing="0" class="table">
		<tr height="30" style="background-color: #52be7f">
			<td width="15%" align="center">用户名ID</td>
			<td width="10%" align="center">姓名</td>
			<td width="70%" align="center">建议</td>
			<td width="5%" align="center"></td>
		</tr>
		<%
			for (int i = 0; i < result.getResultList().size(); i++) {
				Advice s = result.getResultList().get(i);
		%>

	<tr id="line_<%=s.getUser().getId() %>" height="30" onmouseover="this.style.background='#eeeeee';"
			onmouseout="this.style.background='#FFFFFF';">
			<td align="center" id="userId" name="userId"><%=s.getUser().getId()%></td>
			<td align="center" ><%=s.getUser().getName() %></td>
			<td align="center"><%=s.getAdvice()%></td>
			<td> 	<input type="button" class="btn_del" title="删除" id="<%=s.getUser().getId() %>" /> </td>
    </tr>
    <%} %>
    </table>
       <input type="button" class="btn btn-primary" style="margin-top: 10px"    value="返回首页" onclick="javascript:window.location.href='${pageContext.request.contextPath}/index.jsp'"/>
    	<jsp:include page="pagination.jsp"></jsp:include>
    <%}else{ %> 
     <link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/formValidation.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/vendor/default.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/vendor/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/dist/js/formValidation.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/dist/js/framework/bootstrap.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/dist/js/language/zh_CN.js"></script>
	 <script type="text/javascript">
	 $(document).ready(function() {
		    $('#defaultForm')
		        .formValidation({
		            message: 'This value is not valid',
		            icon: {
		                valid: 'glyphicon glyphicon-ok',
		                invalid: 'glyphicon glyphicon-remove',
		                validating: 'glyphicon glyphicon-refresh'
		            },
		            fields: {
		         
		                bio: {
		                    validators: {
		                        notEmpty: {
		                            message: '建议不能为空'
		                        },
		                        stringLength: {
		                            min: 6,
		                            max: 100,
		                            message: '最少6字'
		                        },
		                    }
		                }
	 }
		        });
		        });
	</script>
    <div class="container" style="margin-top: 50px;">
		    <div class="row">
		        <section>
		            <div class="col-lg-8 col-lg-offset-2">
		                <form id="defaultForm" method="post" class="form-horizontal" action="AdviceAction?action=add"
		                      data-fv-message="This value is not valid"
		                      data-fv-icon-valid="glyphicon glyphicon-ok"
		                      data-fv-icon-invalid="glyphicon glyphicon-remove"
		                      data-fv-icon-validating="glyphicon glyphicon-refresh">
		                    <div class="form-group">
		                        <label class="col-lg-3 control-label">提交建议</label>
		                        <div class="col-lg-5">
		                            <textarea class="form-control" name="bio" rows="10" data-fv-stringlength data-fv-stringlength-max="100" data-fv-stringlength-message="请真诚提出建议"></textarea>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-lg-9 col-lg-offset-3">
		                            <button type="submit" class="btn btn-primary">提交</button>
		                            <input type="button" class="btn btn-primary" value="返回首页" onclick="javascript:window.location.href='${pageContext.request.contextPath}/index.jsp'"/>
		                        </div>
		                    </div>
		                </form>
		            </div>
		        </section>
		        <!-- :form -->
		    </div>
		</div>
    
    
    <%} %>
    
    
    
 
  </body>
</html>
