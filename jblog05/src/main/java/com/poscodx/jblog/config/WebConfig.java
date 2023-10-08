package com.poscodx.jblog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.poscodx.jblog.config.web.FileuploadConfig;
import com.poscodx.jblog.config.web.MessageResourceConfig;
import com.poscodx.jblog.config.web.MvcConfig;
import com.poscodx.jblog.config.web.SecurityConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.poscodx.jblog.controller", "com.poscodx.jblog.exception"})
@Import({MvcConfig.class, SecurityConfig.class, FileuploadConfig.class, MessageResourceConfig.class})
public class WebConfig implements WebMvcConfigurer {
}
