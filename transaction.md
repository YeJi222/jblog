# Transaction 사용법 정리 

### [pom.xml에 dependency 추가]
- spring-tx 추가 
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-tx</artifactId>
    <version>6.0.11</version>
</dependency>
```

### [applicationContext.xml]
- transaction 부분 추가 
```xml
<!-- Transaction -->
	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="dataSource" />
</bean>

<!-- @Transactional 어노테이션 활성화 -->
<tx:annotation-driven />
```

- tx추가 하기 위해, xmlns도 추가해줘야 한다
```xml
<beans ...
xmlns:tx="http://www.springframework.org/schema/tx"
...
xsi:schemaLocation="
...
http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.3.xsd">
```
- applicationContext.xml 전체 코드
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

### [@Transactional 어노테이션에 대하여]
- service, repository 단에서만 사용 가능
- UserService에서 유저 회원가입할 때 user 테이블, blog 테이블, category 테이블에 insert하게 되는데, 이때 transaction 사용한다
- 세 개의 기능 중 하나라도 실패하면 rollback이 일어나도록 transaction을 반영한다
```java
@Transactional
public void join(@Valid UserVo userVo, CategoryVo categoryVo) {
  userRepository.insert(userVo);
  blogRepository.insert(userVo);
  blogRepository.insertCategory(categoryVo);
}
```
- blogRepository.insert(userVo); 가 실패하도록 테스트해보기
- blog.xml에서 sql문 에러 발생 시키기
```xml
<insert id="insert" parameterType="blogvo">
  <![CDATA[
    nsert
      into blog (blog_id, title, image)
    values(#{blogId}, #{title }, #{image })
  ]]>
</insert>
```
- userRepository.insert(userVo);가 성공해도, blogRepository.insert(userVo);가 실패하므로, user insert, blog insert, category insert 모두 실행되지 않도록 롤백을 발생시킨다
- 롤백이 실행되었는지 디비를 확인할 수 있다 
