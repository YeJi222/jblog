## jblog04 Manual
(패키지 구조)
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
 |     |     |-- /controller 
 |     |     |-- /service
 |     |     |-- /repository
  

[src/main/resources]
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
 |     |     |     |    |     |-- messages_ko.properties 
```

☘️ applicationContext.xml을 AppConfig.java로 관리, spring-servlet.xml을 WebConfig.java로 관리한다    
☘️ AppConfig, WebConfig는 web.xml에서 세팅된다 

### 1. jblog03의 applicationContext.xml없애고 자바 파일로 설정하기
- applicationContext.xml를 없애고, com/poscodx/jblog/config/app 패키지에 DBConfig, MyBatisConfig 자바 파일을 생성하여 세팅한 후, AppConfig에 import 해주는 과정이 필요

(기존 applicationContext.xml)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee.xsd
        http://www.springframework.org/schema/lang http://www.springframework.org/schema/lang/spring-lang.xsd
        http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd
        http://www.springframework.org/schema/task http://www.springframework.org/schema/task/spring-task.xsd
        http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.3.xsd">
	<!-- auto proxy -->
	<aop:aspectj-autoproxy />
	
	<!-- Connection Pool DataSource-->
	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
		<property name="driverClassName" value="org.mariadb.jdbc.Driver" />
		<property name="url" value="jdbc:mariadb://192.168.64.9:3307/jblog?charset=utf8" />
		<property name="username" value="jblog" />
		<property name="password" value="jblog" />
	</bean>
	
	<!-- Transaction -->
	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	    <property name="dataSource" ref="dataSource" />
	</bean>
	
	<!-- @Transactional 어노테이션 활성화 -->
    <tx:annotation-driven />

	<!-- SqlSessionFactory --> 
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean"> 
		<property name="dataSource" ref="dataSource" /> 
		<property name="configLocation" value="classpath:mybatis/configuration.xml" /> 
	</bean>
	
	<!-- SqlSession -->
	<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
		<constructor-arg ref="sqlSessionFactory" />
	</bean>
	
	<context:annotation-config />
	<context:component-scan base-package="com.poscodx.jblog.service, com.poscodx.jblog.repository">
		<context:include-filter type="annotation" expression="org.springframework.stereotype.Repository" />
		<context:include-filter type="annotation" expression="org.springframework.stereotype.Service" />
		<context:include-filter type="annotation" expression="org.springframework.stereotype.Component" />			
	</context:component-scan>
</beans>
```

(1) 아래 xml 코드에서 aspectj-autoproxy를 @EnableAspectJAutoProxy 어노테이션으로 대체
```xml
<!-- auto proxy -->
<aop:aspectj-autoproxy />
```
- AppConfig.java에서 @EnableAspectJAutoProxy 어노테이션 사용
```java
@EnableAspectJAutoProxy
```

(2) 아래 xml 태그 내용을 AppConfig.java에 자바 어노테이션으로 수정   
(xml)
```xml
<context:annotation-config />
	<context:component-scan base-package="com.poscodx.jblog.service, com.poscodx.jblog.repository">
		<context:include-filter type="annotation" expression="org.springframework.stereotype.Repository" />
		<context:include-filter type="annotation" expression="org.springframework.stereotype.Service" />
		<context:include-filter type="annotation" expression="org.springframework.stereotype.Component" />			
	</context:component-scan>
```
(AppConfig.java)
```java
@ComponentScan({"com.poscodx.jblog.service", "com.poscodx.jblog.repository"})
```

(3) 아래 xml 태그 내용을 DBConfig.java에 설정(+ jdbc.properties)   

(xml)
```xml
<!-- Connection Pool DataSource-->
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
  <property name="driverClassName" value="org.mariadb.jdbc.Driver" />
  <property name="url" value="jdbc:mariadb://192.168.64.9:3307/jblog?charset=utf8" />
  <property name="username" value="jblog" />
  <property name="password" value="jblog" />
</bean>
```

(jdbc.properties)
```properties
jdbc.driverClassName=org.mariadb.jdbc.Driver
jdbc.url=jdbc:mariadb://192.168.64.9:3307/jblog?charset=utf8
jdbc.username=jblog
jdbc.password=jblog
jdbc.initialSize=10
jdbc.maxActive=20
```
- DBConfig에서 jdbc.properties 경로 설정해주고, Environment를 사용하여 property를 불러온다 
(DBConfig.java)
```java
package com.poscodx.jblog.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:com/poscodx/jblog/config/app/jdbc.properties")
public class DBConfig {
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.username"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		dataSource.setInitialSize(env.getProperty("jdbc.initialSize", Integer.class));
		dataSource.setMaxActive(env.getProperty("jdbc.maxActive", Integer.class));
		
		return dataSource;
	}
}
```

(4) 아래 xml 태그 내용을 MyBatisConfig.java에 설정   
(xml)
```xml
<!-- SqlSessionFactory --> 
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean"> 
  <property name="dataSource" ref="dataSource" /> 
  <property name="configLocation" value="classpath:mybatis/configuration.xml" /> 
</bean>

<!-- SqlSession -->
<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
  <constructor-arg ref="sqlSessionFactory" />
</bean>

<!-- Transaction -->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="dataSource" />
</bean>

<!-- @Transactional 어노테이션 활성화 -->
  <tx:annotation-driven />
```
(MyBatisConfig.java)
```java
package com.poscodx.jblog.config.app;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class MyBatisConfig {	
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setConfigLocation(applicationContext.getResource("classpath:com/poscodx/jblog/config/app/mybatis/configuration.xml"));
		
		return sqlSessionFactory.getObject();
	}
	
	@Bean
	public SqlSession sqlSession(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	@Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
}
```
- DBConfig.java와 MyBatisConfig.java로 설정해주었으므로, AppConfig.java에 클래스를 import 해주어야 한다 
```java
@Import({DBConfig.class, MyBatisConfig.class})
```

### 2. jblog03의 spring-servlet.xml없애고 자바 파일로 설정하기
- spring-servlet.xml을 없애고, com/poscodx/jblog/config/web 패키지에 FileUploadConfig, MessageResourceConfig, MvcConfig, SecurityConfig 자바 파일을 생성하여 세팅한 후, WebConfig에 import 해주는 과정이 필요   

(기존 spring-servlet.xml)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop" 
	xmlns="http://www.springframework.org/schema/beans"
	xmlns:p="http://www.springframework.org/schema/p" 
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xsi:schemaLocation="http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
	http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
	http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
	http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
	<!-- auto proxy -->
	<aop:aspectj-autoproxy />

	<!-- 
		Validator
		Default Servlet Handler
		Message Converter
		Argument Resolver
	 -->
	<mvc:annotation-driven />

	<!-- Default Servlet Handler -->
	<mvc:default-servlet-handler/>

	<!-- Interceptors -->
	<mvc:interceptors>
		<mvc:interceptor>
			<mvc:mapping path="/user/auth"/>
			<bean class="com.poscodx.jblog.interceptor.LoginInterceptor" />
		</mvc:interceptor>
		<mvc:interceptor>
			<mvc:mapping path="/user/logout/main"/>
			<mvc:mapping path="/user/logout/**"/>
			<bean class="com.poscodx.jblog.interceptor.LogoutInterceptor" />
		</mvc:interceptor>
		<mvc:interceptor>
			<mvc:mapping path="/**/admin/**"/>
			<bean class="com.poscodx.jblog.interceptor.AdminInterceptor" />
		</mvc:interceptor>
	</mvc:interceptors>
	
	<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
	   <property name="basenames">
	      <list>
			<value>messages/messages_ko</value>
			<value>messages/messages_en</value>
	      </list>
	   </property>
	</bean>
	
	<!-- Multipart Resolver -->
	<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
		<!-- 최대업로드 가능한 바이트크기 -->
		<property name="maxUploadSize" value="52428800" />
		<!-- 디스크에 임시 파일을 생성하기 전에 메모리에 보관할수있는 최대 바이트 크기 -->
		<property name="maxInMemorySize" value="52428800" />
		<!-- defaultEncoding -->
		<property name="defaultEncoding" value="utf-8" />
	</bean>

	<!-- mvc url-resource mapping -->
	<mvc:resources mapping="/assets/upload-images/**" location="file:/Users/yeji/jblog-uploads/" />
		
	<!-- View Resolver -->
	<bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
	   <property name="viewClass" value="org.springframework.web.servlet.view.JstlView" />
	   <property name="prefix" value="/WEB-INF/views/" />
	   <property name="suffix" value=".jsp" />
	</bean>
	
	<context:annotation-config />
	<context:component-scan base-package="com.poscodx.jblog.controller, com.poscodx.mysite.exception" />
</beans>
```

(1) 아래 xml 코드에서 aspectj-autoproxy를 @EnableAspectJAutoProxy 어노테이션으로 대체
```xml
<!-- auto proxy -->
<aop:aspectj-autoproxy />
```
- WebConfig.java에서 @EnableAspectJAutoProxy 어노테이션 사용
```java
@EnableAspectJAutoProxy
```

(2) 아래 xml 코드에서 default-servlet-handler를 MvcConfig.java에서 configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) 메소드로 정의   
(xml)
```xml
<!-- Default Servlet Handler -->
<mvc:default-servlet-handler/>
```
(java)
```java
@Override
public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	configurer.enable();
}
```

(3) 아래 xml 코드를 MvcConfig.java에서 viewResolver() 메소드로 정의   
(xml)
```xml
<!-- View Resolver -->
<bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
   <property name="viewClass" value="org.springframework.web.servlet.view.JstlView" />
   <property name="prefix" value="/WEB-INF/views/" />
   <property name="suffix" value=".jsp" />
</bean>
```
(java)
```java
@Bean
public ViewResolver viewResolver() {
	InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	viewResolver.setViewClass(JstlView.class);
	viewResolver.setPrefix("/WEB-INF/views/");
	viewResolver.setSuffix(".jsp");
	
	return viewResolver;
}
```
** MvcConfig는 WebMvcConfigurer를 implements하고, @EnableWebMvc 어노테이션이 필요   

(4) 아래 xml 코드를 SecurityConfig.java에서 WebMvcConfigurer를 implements하여 각 Interceptor 메소드를 정의    
(xml)
```xml
<!-- Interceptors -->
<mvc:interceptors>
	<mvc:interceptor>
		<mvc:mapping path="/user/auth"/>
		<bean class="com.poscodx.jblog.interceptor.LoginInterceptor" />
	</mvc:interceptor>
	<mvc:interceptor>
		<mvc:mapping path="/user/logout/main"/>
		<mvc:mapping path="/user/logout/**"/>
		<bean class="com.poscodx.jblog.interceptor.LogoutInterceptor" />
	</mvc:interceptor>
	<mvc:interceptor>
		<mvc:mapping path="/**/admin/**"/>
		<bean class="com.poscodx.jblog.interceptor.AdminInterceptor" />
	</mvc:interceptor>
</mvc:interceptors>
```
(java)
```java
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
```
(5) 아래 xml 코드를 MessageResourceConfig.java에서 messageSource() 메소드로 정의   
(xml)
```xml
<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
   <property name="basenames">
      <list>
	<value>messages/messages_ko</value>
	<value>messages/messages_en</value>
      </list>
   </property>
</bean>
```
(java)
```java
package com.poscodx.jblog.config.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageResourceConfig {
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("com/poscodx/jblog/config/web/messages/messages_ko", "com/poscodx/jblog/config/web/messages/messages_en");
		messageSource.setDefaultEncoding("utf-8");
		
		return messageSource;
	}
}
```

(6) 아래 xml 코드를 FileuploadConfig.java에서 세팅   
(xml)
```xml
<!-- Multipart Resolver -->
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
	<!-- 최대업로드 가능한 바이트크기 -->
	<property name="maxUploadSize" value="52428800" />
	<!-- 디스크에 임시 파일을 생성하기 전에 메모리에 보관할수있는 최대 바이트 크기 -->
	<property name="maxInMemorySize" value="52428800" />
	<!-- defaultEncoding -->
	<property name="defaultEncoding" value="utf-8" />
</bean>

<!-- mvc url-resource mapping -->
<mvc:resources mapping="/assets/upload-images/**" location="file:/Users/yeji/jblog-uploads/" />
```
(java)
```java
package com.poscodx.jblog.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@PropertySource("classpath:com/poscodx/jblog/config/web/fileupload.properties")
public class FileuploadConfig implements WebMvcConfigurer {
	@Autowired
	private Environment env;
	
	// Multipart Resolver
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(env.getProperty("fileupload.maxUploadSize", Long.class));
		multipartResolver.setMaxInMemorySize(env.getProperty("fileupload.maxInMemorySize", Integer.class));
		multipartResolver.setDefaultEncoding(env.getProperty("fileupload.defaultEncoding"));
		
		return multipartResolver;
	}
	
	// url-resource mapping
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler(env.getProperty("fileupload.resourceUrl") + "/**")
			.addResourceLocations("file:" + env.getProperty("fileupload.uploadLocation") + "/");
	}
}
```

(7) 아래 xml 코드에서 component-scan를 @ComponentScan 어노테이션으로 대체   
(xml)
```xml
<context:component-scan base-package="com.poscodx.jblog.controller, com.poscodx.mysite.exception" />
```
(java)
```java
@ComponentScan({"com.poscodx.jblog.controller", "com.poscodx.jblog.exception"})
```

### 3. mappers xml 경로 수정해주기 
- 'com/poscodx/jblog/config/app/' 경로 추가해주어 com/poscodx/jblog/config/app/mybatis/mappers/xml이름.xml로 변경 
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<typeAliases>
		<typeAlias type="com.poscodx.jblog.vo.UserVo" alias="uservo"/>
		<typeAlias type="com.poscodx.jblog.vo.BlogVo" alias="blogvo"/>
		<typeAlias type="com.poscodx.jblog.vo.CategoryVo" alias="categoryvo"/>
		<typeAlias type="com.poscodx.jblog.vo.PostVo" alias="postvo"/>
    </typeAliases>
    	
	<mappers>
		<mapper resource="com/poscodx/jblog/config/app/mybatis/mappers/user.xml" />
		<mapper resource="com/poscodx/jblog/config/app/mybatis/mappers/blog.xml" />
		<mapper resource="com/poscodx/jblog/config/app/mybatis/mappers/category.xml" />
		<mapper resource="com/poscodx/jblog/config/app/mybatis/mappers/post.xml" />
	</mappers>
</configuration>
```





