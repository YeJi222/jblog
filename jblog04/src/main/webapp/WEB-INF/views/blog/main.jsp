<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% pageContext.setAttribute("newline", "\n"); %>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css?after">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		
		<div id="wrapper">
			<div id="content">
				<!-- 포스트 타이틀, 내용 -->
				<div class="blog-content">
					<h4>${postList[postIdx].title }</h4>
					<p>
						${fn:replace(postList[postIdx].contents, newline, "<br>") }
					<p>
				</div>
				
				<!-- 글 리스트 목차 -->
				<ul class="blog-list">
					<c:set var="count" value="${fn:length(postList) }" />
					<c:forEach items="${postList }" var="vo" varStatus="status">
						<li><a href="${pageContext.request.contextPath}/${blogVo.blogId}/${vo.categoryNo}/${vo.no}?type=${type}">${vo.title }</a> <span>${vo.regDate }</span></li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<!-- 블로그 로고 사진 -->
		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}${blogVo.image}">
			</div>
		</div>

		<!-- 블로그 카테고리 리스트 -->
		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:set var="count" value="${fn:length(categoryList) }" />
				<c:forEach items="${categoryList }" var="vo" varStatus="status">
					<li><a href="${pageContext.request.contextPath}/${vo.blogId}/${vo.no}">${vo.name }</a></li>
				</c:forEach>
			</ul>
		</div>
		
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>