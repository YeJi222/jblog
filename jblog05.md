## jblog05
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
  
### 2. assets 파일 src/main/resources로 옮기기
- 옮긴 경로 MvcConfig.java에 addResourceHandlers 메소드에 설정


