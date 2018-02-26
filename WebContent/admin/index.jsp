<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<c:import url="../head.jsp"></c:import>
	<iframe name="left" src="left.jsp" width="200" height="500"></iframe>
	<iframe name="right" src="main.jsp" width="1000" height="500"></iframe>
	
</body>
</html>