<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ul class="admin-menu">
	<c:choose>
		<c:when test="${selected.equals('basic')}">
			<li class="selected">기본설정</li>
			<li><a href="${pageContext.request.contextPath}/${blogVo.blogId}/admin/category">카테고리</a></li>
			<li><a href="${pageContext.request.contextPath}/${blogVo.blogId}/admin/write">글작성</a></li>
		</c:when>
		<c:when test="${selected.equals('category')}">
			<li><a href="${pageContext.request.contextPath}/${blogVo.blogId}/admin/basic">기본설정</a></li>
			<li class="selected">카테고리</li>
			<li><a href="${pageContext.request.contextPath}/${blogVo.blogId}/admin/write">글작성</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="${pageContext.request.contextPath}/${blogVo.blogId}/admin/basic">기본설정</a></li>
			<li><a href="${pageContext.request.contextPath}/${blogVo.blogId}/admin/category">카테고리</a></li>
			<li class="selected">글작성</li>
		</c:otherwise>
	</c:choose>
</ul>