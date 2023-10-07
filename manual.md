# Assignment04 - JBlog03(Spring)

### [실행방법]
- 레포지토리 클론 
```sh
$ git clone https://github.com/YeJi222/jblog.git
```

### [파일 위치]
- jblog 모델 mwb 파일 : jblog/schema/jblog.mwb
<img width="521" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/ecc2ddaa-df69-49e0-9012-9df54522c8ba">

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

### [구현하면서 정리한 md files]
🐋 [Transaction 적용 방법](https://github.com/YeJi222/jblog/blob/main/transaction.md)    
🐋 [정규표현식 사용 방법](https://github.com/YeJi222/jblog/blob/main/regex.md)







