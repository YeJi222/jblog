package com.poscodx.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.UserVo;

public class AdminInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String requestURI = request.getRequestURI();
    	String blogId = requestURI.split("/")[2];
		
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		
		if(userVo == null || !(userVo.getId().equals(blogId))) { // 권한 없는 경우 
			System.out.println("자신의 블로그 관리자 페이지에만 들어갈 수 있습니다.");
			response.sendRedirect(request.getContextPath() + "/" + blogId);
			
			return false;
		}
		
		return true;
	}
}