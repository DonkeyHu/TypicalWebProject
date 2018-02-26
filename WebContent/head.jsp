<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<c:choose>
	<c:when test="${empty sessionScope.user}">
		<a href="reg.jsp">注册</a>
		<a href="login.jsp">登录</a>
	</c:when>
	<c:otherwise>
		欢迎您,${sessionScope.user.uname}<br/>
		<a href="/TypicalWebProject/admin/index.jsp">个人管理中心</a><br/>
		<a href="/TypicalWebProject/UserServlet?method=exit">退出系统</a>
		<!--这里是超链接直接跳转，默认提交方式为get方法。 -->
	</c:otherwise>
</c:choose>

<br/><br/><br/><br/>
<hr color="red"/>
