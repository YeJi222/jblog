<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="header">
	<a href="${pageContext.request.contextPath}" style="display: grid; grid-template-columns: 35px 40px">
		<img src="/jblog03/assets/images/jlogo.png" width="25px;" style="margin: 5px;" />
		<span style="font-size: 18px; margin-top: 9px; color: white;">JBlog</span>
	</a>
	<h1 class="title-area" style="margin-top: -30px;">
		<a class="header-title" href="${pageContext.request.contextPath}/${blogVo.blogId}">${blogVo.title }</a>
	</h1>
	<ul>
		<c:choose>
			<c:when test="${empty authUser }">
				<li><a href="${pageContext.request.contextPath }/user/login">로그인</a></li>
				<li class="header-session">로그아웃</li>
				<li class="header-session">블로그 관리</li>
			</c:when>
			<c:when test="${authUser.id != blogVo.blogId}">
				<li class="header-session">로그인</li>
				<li><a href="${pageContext.request.contextPath }/user/logout/${blogVo.blogId}">로그아웃</a></li>
				<li class="header-session">블로그 관리</li>
			</c:when>
			<c:otherwise>
				<li class="header-session">로그인</li>
				<li><a href="${pageContext.request.contextPath }/user/logout/${blogVo.blogId}">로그아웃</a></li>
				<li><a href="${pageContext.request.contextPath }/${blogVo.blogId}/admin/basic">블로그 관리</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>