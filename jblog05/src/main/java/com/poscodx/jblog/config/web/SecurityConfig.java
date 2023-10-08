package com.poscodx.jblog.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.poscodx.jblog.interceptor.AdminInterceptor;
import com.poscodx.jblog.interceptor.LoginInterceptor;
import com.poscodx.jblog.interceptor.LogoutInterceptor;

@Configuration
@EnableWebMvc
public class SecurityConfig implements WebMvcConfigurer {

	// Interceptors
	@Bean
	public HandlerInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}
	
	@Bean
	public HandlerInterceptor logoutInterceptor() {
		return new LogoutInterceptor();
	}
	
	@Bean
	public HandlerInterceptor adminInterceptor() {
		return new AdminInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(loginInterceptor())
			.addPathPatterns("/user/auth");
		
		registry
			.addInterceptor(logoutInterceptor())
			.addPathPatterns("/user/logout/main", "/user/logout/**");
		
		registry
			.addInterceptor(adminInterceptor())
			.addPathPatterns("/**/admin/**");
	}
}
