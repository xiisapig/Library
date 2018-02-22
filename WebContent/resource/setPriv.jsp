<%@page import="cn.ypjalt.entity.Resource"%>
<%@page import="cn.ypjalt.entity.User"%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
	<script src="${pageContext.request.contextPath}/js/jquery-1.12.1.min.js"></script>
<%--<script language="javascript" src="${pageContext.request.contextPath}/js/ios6switch.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/example.js" ></script> 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ios6switch.css" />  --%>
	<script type="text/javascript">
	
	   
	$(document).ready(function(){
	      $("input[name='privfa']").click(function(){
    		// alert($(this).parents("li").children("ul").children("li").size());
    		 $(this).parents("h3").parents("li").children("ul").children("li").children("input[name='priv']").attr("checked",this.checked);
	      $(":checkbox").each(function(){   
	    	var id=$(this).attr("id");
	    	var issys="N";
	    	if(this.checked == true){
	    	    	 issys="Y";
	    	}else{
	    	    	 issys="N";
	    	}
    	     $.ajax({
				   type: "POST",
				   url: "${pageContext.request.contextPath}/resource/ResourceAction",
				   data: "action=issys&id="+id+"&issys="+issys,
				});
		   });
  		 });
	      $("input[name='priv']").click(function(){
	    	//	alert($(this).parents("li").parents("ul").parents("li").children("h3").children("input[name='privfa']").size());
	    		$(this).parents("li").parents("ul").parents("li").children("h3").children("input[name='privfa']").attr("checked",this.checked);
		        $(":checkbox").each(function(){   
		    	var id=$(this).attr("id");
		    	var issys="N";
		    	var i=1;
		    	if(this.checked == true){
		    	    	 issys="Y";
		    	}else{
		    	    	 issys="N";
		    	}
	    	     $.ajax({
					   type: "POST",
					   url: "${pageContext.request.contextPath}/resource/ResourceAction",
					   data: "action=issys&id="+id+"&issys="+issys,
					});
			   });
	  		 });
});
	</script>

<style>  
.container{  
    margin:0px auto;  
    width:300px; 
}  
 ul li{  
list-style-type:none;  
}  
</style> 
</head>


<body>
<%
	User u = (User) session.getAttribute("loginUser");
	List<Resource> plist=(List<Resource>)request.getAttribute("plist");
	Map<Integer,List<Resource>> cmap=(Map<Integer,List<Resource>>)request.getAttribute("cmap");
%>
	<ul class="container">
		 <%for(Resource r:plist){%>
		 <li><h3>
			<input type="checkbox"  class="mycheckbox" name="privfa"  onchange="checkall('<%=r.getId() %>');"  id="<%=r.getId()%>" <%="Y".equals(r.getIssys())?"checked='checked'":"" %>/>
			<%=r.getName()%></h3>
				<%
			   		List<Resource> clist=cmap.get(r.getId());
					if(clist!=null&&clist.size()>0){
				 %>
				<ul >
					<%for(Resource cr:clist){%>
						<li>
						<input type="checkbox"  class="mycheckbox" name="priv" id="<%=cr.getId()%>" <%="Y".equals(cr.getIssys())?"checked='checked'":"" %>/>
						<%=cr.getName() %>
						</li>
					<% } %>
				</ul>
			  <% } %>
			</li>
		<% } %>	
	</ul>
</body>
	
</html>