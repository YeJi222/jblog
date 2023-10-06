<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<a href="${pageContext.request.contextPath }/">
	<h1 class="logo">JBlog</h1>
</a>
<ul class="menu">
	<c:choose>
		<c:when test="${empty authUser }">
			<li><a href="${pageContext.request.contextPath }/user/login">로그인</a></li>
			<li><a href="${pageContext.request.contextPath }/user/join">회원가입</a></li>
			<li class="menu-session">로그아웃</li>
			<li class="menu-session">내블로그</li>
		</c:when>
		<c:otherwise>
			<li class="menu-session">로그인</li>
			<li class="menu-session">회원가입</li>
			<li><a href="${pageContext.request.contextPath }/user/logout/main">로그아웃</a></li>
			<li><a href="${pageContext.request.contextPath }/${authUser.id }">내블로그</a></li>
		</c:otherwise>
	</c:choose>
</ul>