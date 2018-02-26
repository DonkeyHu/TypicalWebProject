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
	<form method="POST" enctype="multipart/form-data" action="../UpFileServlet">
  		<input type="file" name="upfile"><br/>
 		<input type="text" name="note"><br/>
  		<input type="submit" value="提交"> 
	</form>
</body>
</html>