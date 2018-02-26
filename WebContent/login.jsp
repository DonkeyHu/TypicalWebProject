<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body>
	<c:import url="head.jsp"></c:import>
	<form name="regFrm" method="get" action="UserServlet" >
		用户名:<input type="text" name="uname" /><span id="unameMeg" class="error">${LoginError}</span><br/>
		密码：<input type="password" name="pwd" /><span id="pwdMeg" class="error"></span><br/>
			<!--不想在客户端显示出来，但又想把数据提交给数据库 -->
			<input type="hidden" name="method" value="login" /> 
			<input type="submit" value="登录" />
	</form>
	<c:import url="foot.jsp"></c:import>
</body>
</html>