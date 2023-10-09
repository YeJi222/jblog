## jblog05 Manual
### 1. web.xml 없애고 자바 파일로 설정하기
(기존 web.xml 파일)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" version="2.5">
  <display-name>jblog04</display-name>
  
 	<context-param>
     	<param-name>contextClass</param-name>
     	<param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
	</context-param> 	
	<context-param>
     	<param-name>contextConfigLocation</param-name>
     	<param-value>com.poscodx.jblog.config.AppConfig</param-value>
	</context-param> 	
  
  <!-- ContextLoad Listener -->
  <listener>
	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  
  <!-- Encoding Filter -->
  <filter>
    <filter-name>encodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>encodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

  <!-- Dispatcher Servlet -->
  <servlet>
		<servlet-name>spring</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<init-param>
			<param-name>contextClass</param-name>
			<param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
		</init-param>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>com.poscodx.jblog.config.WebConfig</param-value>
		</init-param>
	</servlet>
	<servlet-mapping>
		<servlet-name>spring</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
  
  <!-- common error page -->
  <error-page>
  	<error-code>404</error-code>
  	<location>/WEB-INF/views/error/404.jsp</location>
  </error-page>
  
  <error-page>
  	<error-code>500</error-code>
  	<location>/WEB-INF/views/error/500.jsp</location>
  </error-page>
  
  <!-- default files -->
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
</web-app>
```

- com.poscodx.jblog.initializer 패키지 생성
```xml
 <!-- ContextLoad Listener -->
<listener>
	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```
- xml 파일의 ContextLoad Listener 태그 자바 파일로 설정하려면, AbstractAnnotationConfigDispatcherServletInitializer를 상속하는 JblogWebApplicationInitializer.java 생성
```java
package com.poscodx.jblog.initializer;

import javax.servlet.Filter;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.poscodx.jblog.config.AppConfig;
import com.poscodx.jblog.config.WebConfig;

public class JblogWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {AppConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] {new CharacterEncodingFilter("utf-8")};
	}

	@Override
	protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
		DispatcherServlet servlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
		servlet.setThrowExceptionIfNoHandlerFound(true);
		
		return servlet;
	}
}
```
- getRootConfigClasses() 메소드 : 아래의 xml 설정을 대체
```xml
<context-param>
     	<param-name>contextClass</param-name>
     	<param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
	</context-param> 	
	<context-param>
     	<param-name>contextConfigLocation</param-name>
     	<param-value>com.poscodx.jblog.config.AppConfig</param-value>
</context-param> 	
```
- getServletConfigClasses() 메소드 : 아래의 xml 설정을 대체
```xml
<servlet>
...
	<init-param>
		<param-name>contextClass</param-name>
		<param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
	</init-param>
	<init-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>com.poscodx.jblog.config.WebConfig</param-value>
	</init-param>
</servlet>
```
- getServletMappings() 메소드 : 아래의 xml 설정을 대체
```xml
<servlet-mapping>
	<servlet-name>spring</servlet-name>
	<url-pattern>/</url-pattern>
</servlet-mapping>
```
- getServletFilters() 메소드 : 아래의 xml 설정을 대체
```xml
<!-- Encoding Filter -->
<filter>
    <filter-name>encodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>encodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```
- createDispatcherServlet(WebApplicationContext servletAppContext) 메소드 : 아래의 xml 설정을 대체
```xml
<servlet>
	<servlet-name>spring</servlet-name>
	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
...
</servlet>
```
- default files 부분은 그냥 지워도 OK
- common error page 에러 페이지 설정은 MvcConfig.java에서 configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) 메소드 주석처리 후, GlobalExceptionHandler.java에서 설정
- **web.xml 파일을 지우고, 톰캣 실행 및 브라우저 접속해야 에러 발생하지 않는다 
  
### 2. assets 파일 src/main/resources로 옮기기
(파일 구조)
```txt
[src/main/java]
/com
 |-- /poscodx
 |     |-- /jblog
 |     |     |-- /config
 |     |     |     |-- AppConfig.java
 |     |     |     |-- WebConfig.java
 |     |     |     |-- /app
 |     |     |     |    |-- DBConfig.java
 |     |     |     |    |-- MyBatisConfig.java
 |     |     |     |-- /web
 |     |     |     |    |-- MvcConfig.java
 |     |     |     |    |-- SecurityConfig.java
 |     |     |     |    |-- MessageSourceConfig.java
 |     |     |     |    |-- FileuploadConfig.java
 |     |     |-- /initializer
 |     |     |      |-- JblogWebApplicationInitializer
 |     |     |-- /exception
 |     |     |      |-- FileUploadServiceException
 |     |     |      |-- GlobalExceptionHandler
 |     |     |-- /controller 
 |     |     |-- /service
 |     |     |-- /repository
 |     |     |-- /interceptor
 |     |     |-- /vo

[src/main/resources]
/assets
 |-- /css
 |-- /images
 |-- /js
/com
 |-- /poscodx
 |     |-- /jblog
 |     |     |-- /config
 |     |     |     |-- /app
 |     |     |     |    |-- jdbc.properties 
 |     |     |     |    |-- /mybatis
 |     |     |     |    |     |-- configuration.xml
 |     |     |     |    |     |-- /mappers
 |     |     |     |    |     |     |-- blog.xml
 |     |     |     |    |     |     |-- category.xml
 |     |     |     |    |     |     |-- post.xml
 |     |     |     |    |     |     |-- user.xml
 |     |     |     |-- /web
 |     |     |     |    |-- fileupload.properties
 |     |     |     |    |-- /messages
 |     |     |     |    |     |-- messages_en.properties 
 |     |     |     |    |     |-- messages_ko.properties
```
- 옮긴 경로 MvcConfig.java에 addResourceHandlers 메소드에 설정
(MvcConfig.java)
```java
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
	registry
		.addResourceHandler("/assets/**")
		.addResourceLocations("classpath:assets/");		
}
```

### 3. 에러페이지 설정 
- com/poscodx/jblog/exception 패키지에 GlobalExceptionHandler.java 파일 생성
- @ControllerAdvice : 스프링 컨트롤러에서 발생하는 예외를 처리하는 어노테이션

(기존 web.xml에 있던 error-page 설정)
```xml
<!-- common error page -->
<error-page>
	<error-code>404</error-code>
	<location>/WEB-INF/views/error/404.jsp</location>
	</error-page>
	
	<error-page>
	<error-code>500</error-code>
	<location>/WEB-INF/views/error/500.jsp</location>
</error-page>
```
(java 파일로 구성)
```java
package com.poscodx.jblog.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLSyntaxErrorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice // 스프링 컨트롤러에서 발생하는 예외를 처리하는 어노테이션 
public class GlobalExceptionHandler {
	private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class); 

	@ExceptionHandler(Exception.class)
	public String handlerException(Model model, Exception e) {
		
		// 1. 404 Error 처리 
		if(e instanceof NoHandlerFoundException) {
			return "error/404";
		}
		
		// 2. 로깅(Logging)
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		// System.out.println(errors.toString());
		logger.error(errors.toString());
		
		// 3. 404 에러 제외한 나머지 에러 처리(500 에러포함)
		model.addAttribute("errors", errors.toString());
		return "error/exception";
	}
}
```














