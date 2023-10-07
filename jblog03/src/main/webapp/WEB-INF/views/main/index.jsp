<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css?after" >
</head>
<body>
	<div class="center-content">
		<c:import url="/WEB-INF/views/includes/menu.jsp" />
		<form class="search-form">
			<fieldset>
				<input type="text" name="keyword" />
				<input type="submit" value="검색" />
			</fieldset>
			<fieldset>
				<input type="radio" name="which" value="blog-title"> <label>블로그 제목</label>
				<input type="radio" name="which" value="tag"> <label>태그</label>
				<input type="radio" name="which" value="blog-user"> <label>블로거</label>
			</fieldset>
		</form>
		
		<!-- Blog URL List -->
		<ul style="text-align: left; margin-left: 50px;">
			<c:forEach items="${userList }" var="vo" varStatus="status">
				<li style="font-size: 17px;"><a href="${pageContext.request.contextPath}/${vo.id}">🐋 ${vo.id}'s Blog</a></li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>