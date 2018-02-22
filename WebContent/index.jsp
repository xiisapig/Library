<%@page import="cn.ypjalt.entity.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html lang="en" class="demo-3 no-js">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>首页</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/normalize.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/demo.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/component.css" />
<script src="${pageContext.request.contextPath}/js/snap.svg-min.js"></script>

<% User u=(User)session.getAttribute("loginUser"); %>


</head>
<body>
	<div class="container">
		<header class="jq22-header">
			<h1>
				图书馆管理系统<span>欢迎你 <a href="${pageContext.request.contextPath}/user/UserAction?action=modifyUI">${loginUser.name }</a></span>
			</h1>

		</header>
		<section id="grid" class="grid clearfix">
			<a href="#" data-path-hover="M 0,0 0,38 90,58 180.5,38 180,0 z">
				<figure>
					<img src="${pageContext.request.contextPath}/img/1.png" />
					<svg viewBox="0 0 180 320" preserveAspectRatio="none">
						<path d="M 0 0 L 0 182 L 90 126.5 L 180 182 L 180 0 L 0 0 z " /></svg>
					<figcaption>
						<h2>图书管理</h2>
						<%if(u.isRole()){ %>
						<p>图书的增删改查	</p>
						<button onclick="javascript:window.location.href='${pageContext.request.contextPath}/book/main.jsp'">进入</button>
						<%}else{ %>
						<p>您没有此权限	</p>
						<%} %>
					
					</figcaption>
				</figure>
			</a> <a href="#" data-path-hover="M 0,0 0,38 90,58 180.5,38 180,0 z">
				<figure>
					<img src="${pageContext.request.contextPath}/img/2.png" />
					<svg viewBox="0 0 180 320" preserveAspectRatio="none">
						<path d="M 0 0 L 0 182 L 90 126.5 L 180 182 L 180 0 L 0 0 z " /></svg>
					<figcaption>
						<h2>用户管理</h2>
							<%if(u.isRole()){ %>
						<p>用户的增删改查</p>
						<button onclick="javascript:window.location.href='${pageContext.request.contextPath}/user/main.jsp'">进入</button>
						<%}else{ %>
						<p>您没有此权限</p>
						<%} %>
					</figcaption>
				</figure>
			</a> <a href="#" data-path-hover="M 0,0 0,38 90,58 180.5,38 180,0 z">
				<figure>
					<img src="${pageContext.request.contextPath}/img/3.png" />
					<svg viewBox="0 0 180 320" preserveAspectRatio="none">
						<path d="M 0 0 L 0 182 L 90 126.5 L 180 182 L 180 0 L 0 0 z " /></svg>
					<figcaption>
						<h2>借书</h2>
						<%if(u.isRole()){ %>
						<p>查看借书人信息</p>
						<button onclick="javascript:window.location.href='${pageContext.request.contextPath}/book/JieshuAction?action=searchUI'">进入</button>
					<%}else{ %>
						<p>借书</p>
						<button onclick="javascript:window.location.href='${pageContext.request.contextPath}/book/BookAction?action=searchUI'">进入</button>
					<%} %>
					
					
					</figcaption>
				</figure>
			</a> <a href="#" data-path-hover="M 0,0 0,38 90,58 180.5,38 180,0 z">
				<figure>
					<img src="${pageContext.request.contextPath}/img/4.png" />
					<svg viewBox="0 0 180 320" preserveAspectRatio="none">
						<path d="M 0 0 L 0 182 L 90 126.5 L 180 182 L 180 0 L 0 0 z " /></svg>
					<figcaption>
						<h2>还书</h2>
					<%if(!u.isRole()){ %>
						<p>还书</p>
						<button onclick="javascript:window.location.href='${pageContext.request.contextPath}/book/JieshuAction?action=searchUI'">进入</button>
					<%}else{ %>
						<p>查看还书信息</p>
						<button onclick="javascript:window.location.href='${pageContext.request.contextPath}/book/JieshuAction?action=huanshuUI'">进入</button>
					<%} %>
					</figcaption>
				</figure>
			</a> <a href="#" data-path-hover="M 0,0 0,38 90,58 180.5,38 180,0 z">
				<figure>
					<img src="${pageContext.request.contextPath}/img/5.png" />
					<svg viewBox="0 0 180 320" preserveAspectRatio="none">
						<path d="M 0 0 L 0 182 L 90 126.5 L 180 182 L 180 0 L 0 0 z " /></svg>
					<figcaption>
						<h2>建议与反馈</h2>
						<%if(u.isRole()){ %>
						<p>	查看建议</p>
						<%}else{ %>
							<p>输入建议</p>
						<%} %>
						<button onclick="javascript:window.location.href='${pageContext.request.contextPath}/AdviceAction?action=show'">进入</button>
					</figcaption>
				</figure>
			</a> <a href="#" data-path-hover="M 0,0 0,38 90,58 180.5,38 180,0 z">
				<figure>
					<img src="${pageContext.request.contextPath}/img/6.png" />
					<svg viewBox="0 0 180 320" preserveAspectRatio="none">
						<path d="M 0 0 L 0 182 L 90 126.5 L 180 182 L 180 0 L 0 0 z " /></svg>
					<figcaption>
						<h2>修改密码</h2>
						<p>修改密码</p>
						<button onclick="javascript:window.location.href='${pageContext.request.contextPath}/user/UserAction?action=modify_pwdUI'">进入</button>
					</figcaption>
				</figure>
			</a> <a href="#" data-path-hover="M 0,0 0,38 90,58 180.5,38 180,0 z">
				<figure>
					<img src="${pageContext.request.contextPath}/img/7.png" />
					<svg viewBox="0 0 180 320" preserveAspectRatio="none">
						<path d="M 0 0 L 0 182 L 90 126.5 L 180 182 L 180 0 L 0 0 z " /></svg>
					<figcaption>
						<h2>修改资料</h2>
						<p>修改资料</p>
						<button onclick="javascript:window.location.href='${pageContext.request.contextPath}/user/UserAction?action=modifyUI'">进入</button>
					</figcaption>
				</figure>
			</a> <a href="#" data-path-hover="M 0,0 0,38 90,58 180.5,38 180,0 z">
				<figure>
					<img src="${pageContext.request.contextPath}/img/8.png" />
					<svg viewBox="0 0 180 320" preserveAspectRatio="none">
						<path d="M 0 0 L 0 182 L 90 126.5 L 180 182 L 180 0 L 0 0 z " /></svg>
					<figcaption>
						<h2>退出登录</h2>
						<p>离开这里</p>
						<button
							onclick="javascript:window.location.href='${pageContext.request.contextPath}/login.jsp'">退出</button>
					</figcaption>
				</figure>
			</a>
		</section>
	</div>
	<!-- /container -->
	<script>
		(function() {

			function init() {
				var speed = 300, easing = mina.backout;

				[].slice.call(document.querySelectorAll('#grid > a')).forEach(
						function(el) {
							var s = Snap(el.querySelector('svg')), path = s
									.select('path'), pathConfig = {
								from : path.attr('d'),
								to : el.getAttribute('data-path-hover')
							};

							el.addEventListener('mouseenter', function() {
								path.animate({
									'path' : pathConfig.to
								}, speed, easing);
							});

							el.addEventListener('mouseleave', function() {
								path.animate({
									'path' : pathConfig.from
								}, speed, easing);
							});
						});
			}
			init();
		})();
	</script>
</body>
</html>