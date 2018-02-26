<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:choose>
	<c:when
		test="${requestScope.pageNation.pageNum==requestScope.pageNation.first}">
		<a href="${param.url}=${requestScope.pageNation.first}">首页</a>
	</c:when>
	<c:otherwise>
		<a href="${param.url}=${requestScope.pageNation.first}">首页</a>
		<a href="${param.url}=${requestScope.pageNation.previous}">前一页</a>
	</c:otherwise>
</c:choose>
<c:forEach var="i" begin="${requestScope.pageNation.startNav}"
	end="${requestScope.pageNation.endNav}">
	<c:choose>
		<c:when test="${requestScope.pageNation.pageNum==i}">
			${i}
		</c:when>
		<c:otherwise>
			<a href="${param.url}=${i}">${i}</a>
		</c:otherwise>
	</c:choose>
</c:forEach>
<c:choose>
	<c:when
		test="${requestScope.pageNation.pageNum==requestScope.pageNation.last}">
		<a href="${param.url}=${requestScope.pageNation.last}">末页</a>
	</c:when>
	<c:otherwise>
		<a href="${param.url}=${requestScope.pageNation.next}">后一页</a>
		<a href="${param.url}=${requestScope.pageNation.last}">末页</a>
	</c:otherwise>
</c:choose>