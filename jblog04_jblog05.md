## jblog04
### 1. jblog03의 applicationContext.xml, spring-servlet.xml 없애고 자바 파일로 설정하기
### 2. 

***

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
- AbstractAnnotationConfigDispatcherServletInitializer를 상속하는 JblogWebApplicationInitializer.java 생성


### 2. assets 파일 src/main/resources로 옮기기
- 옮긴 경로 MvcConfig.java에 addResourceHandlers 메소드에 설정









