<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:import url="head.jsp"></c:import>
	<table border=1 style="border-collapse:collapse" bordercolor="blue">
		<tr>
			<th>ID</th><th>姓名</th><th>密码</th><th>邮件</th><th>电话</th>
			<th>年龄</th><th>性别</th><th>地址</th><th>学历</th><th>加入时间
			</th><th>IP地址</th>
		</tr>
	<c:forEach items="${requestScope.pageNation.list}" var="u">
		<tr>
			<td>${u.id}</td><td>${u.uname}</td><td>${u.pwd}</td><td>${u.email}</td>
			<td>${u.phone}</td><td>${u.age}</td><td>${u.gender}</td><td>${u.address}</td>
			<td>${u.degree}</td><td>${u.joinTime}</td><td>${u.ip}</td>
		</tr>
	</c:forEach>
	</table>
	<br/>
	<!-- 下面引入导航page功能 -->
	<c:import url="pageNavUtil.jsp">
		<c:param name="url" value="UserServlet?method=listUsers&pageNum"></c:param>
	</c:import>
	
	<c:import url="foot.jsp"></c:import>
</body>
</html>