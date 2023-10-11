package com.poscodx.jblog.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestURI = request.getRequestURI();
		
		Pattern pattern = Pattern.compile("/logout/([^/]+)");
	    Matcher matcher = pattern.matcher(requestURI);
	
	    String blogId = "";
	    if (matcher.find()) { 
	        blogId = matcher.group(1);
	    }
		
		HttpSession session = request.getSession();
		session.removeAttribute("authUser");
		session.invalidate();
	
		if("main".equals(blogId)) { // jblog03 경로에서 로그아웃하는 경우 
			response.sendRedirect(request.getContextPath());
		} else { // 누군가의 블로그 경로에서 로그아웃하는 경우 
			response.sendRedirect(request.getContextPath() + "/" + blogId);
		}
		
		return false;
    }
}
