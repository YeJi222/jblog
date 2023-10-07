package com.poscodx.jblog.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import com.poscodx.jblog.vo.UserVo;

public class AdminInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String requestURI = request.getRequestURI();
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		
		/* 정규 표현식 패턴
		   'http://localhost:8080/jblog03/'와 그 다음 슬래시('/') 사이 [blogId] 세그먼트 추출
			[^/] : '/' 문자를 제외(^ 부정을 의미)한 모든 문자와 매치한다는 의미
			+ : 바로 앞의 패턴([^/]+)이 하나 이상의 문자를 나타내야 한다는 의미
			즉, '/'가 아닌 문자가 하나 이상 나오는 경우를 추출
			jblog03/와 / 사이에 오는 blogId를 추출할 수 있다
			
			Ex) requestURI : http://localhost:8080/jblog03/yizi/admin/basic
			=> blogId : yizi
		*/
        Pattern pattern = Pattern.compile("/jblog03/([^/]+)/");
        Matcher matcher = pattern.matcher(requestURI);

        String blogId = "";
        if (matcher.find()) { // 패턴과 일치하는 부분 찾기
            blogId = matcher.group(1);
        }
		
		if(userVo == null || !(userVo.getId().equals(blogId))) { // 권한 없는 경우 
			System.out.println("자신의 블로그 관리자 페이지에만 들어갈 수 있습니다.");
			response.sendRedirect(request.getContextPath() + "/" + blogId);
			
			return false;
		}
		
		return true;
	}
}