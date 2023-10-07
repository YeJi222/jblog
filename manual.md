# Assignment04 - JBlog03(Spring)

### [실행방법]
- 레포지토리 클론 
```sh
$ git clone https://github.com/YeJi222/jblog.git
```

### [파일 위치]
- jblog 모델 mwb 파일 : jblog/schema/jblog.mwb
- java file
```txt
[src/main/java]
/com
 |-- /poscodx
 |     |-- /jblog
 |     |     |-- /controller 
 |     |     |      |-- BlogController.java
 |     |     |      |-- MainController.java
 |     |     |      |-- UserController.java
 |     |     |-- /exception
 |     |     |      |-- FileUploadServiceException.java
 |     |     |-- /interceptor
 |     |     |      |-- AdminInterceptor.java
 |     |     |      |-- LoginInterceptor.java
 |     |     |      |-- LogoutInterceptor.java
 |     |     |-- /repository
 |     |     |      |-- BlogRepository.java
 |     |     |      |-- UserRepository.java
 |     |     |-- /service
 |     |     |      |-- BlogService.java
 |     |     |      |-- FileUploadService.java
 |     |     |      |-- UserService.java
 |     |     |-- /vo
 |     |     |      |-- BlogVo.java
 |     |     |      |-- CategoryVo.java
 |     |     |      |-- PostVo.java
 |     |     |      |-- UserVo.java

[src/main/resources]
/messages
 |-- messages_en.properties
 |-- messages_ko.properties
/mybatis
 |-- /mappers
 |     |-- blog.xml
 |     |-- category.xml
 |     |-- post.xml
 |     |-- user.xml
 |-- configuration.xml
applicationContext.xml 
```
