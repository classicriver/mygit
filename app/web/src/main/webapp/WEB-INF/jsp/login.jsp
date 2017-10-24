<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>管理系统</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/login/style.css" />
<script
	src="${pageContext.request.contextPath}/resource/js/jquery/jquery-2.2.4.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/js/login/common.js"></script>
<!--背景图片自动更换-->
<script
	src="${pageContext.request.contextPath}/resource/js/login/supersized.3.2.7.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/js/login/supersized-init.js"></script>
<!--表单验证-->
<script
	src="${pageContext.request.contextPath}/resource/js/login/jquery.validate.min.js?var1.14.0"></script>
<body>
	<div class="login-container">
		<h1>管理系统</h1>
		<div class="connect">
			<p>Management System</p>
		</div>
		<form method="post" id="loginForm"
			action="${pageContext.request.contextPath}/j_spring_security_check">
			<div>
				<input type="text" name="j_username" id="j_username" class="username"
					placeholder="用户名" autocomplete="off" />
			</div>
			<div>
				<input type="password" name="j_password" id="j_password" class="password"
					placeholder="密码" oncontextmenu="return false"
					onpaste="return false" />
			</div>
			<button id="submit" type="submit">登 陆</button>
		</form>
	</div>
</body>
</html>