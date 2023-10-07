# Assignment04 - JBlog03(Spring)

### [구현하면서 정리한 md files]
🐋 [Transaction 적용 방법](https://github.com/YeJi222/jblog/blob/main/transaction.md)    
🐋 [정규표현식 사용 방법](https://github.com/YeJi222/jblog/blob/main/regex.md)

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

### [구현 결과]
#### (jblog 메인 화면)
- http://localhost:8080/jblog03/   
- 로그인 세션 정보가 있을 때 :
  - '로그인, 회원가입'만 활성화
  - '로그아웃, 내블로그'는 연한 색 표시, 링크 연결되지 않도록
- 등록된 회원들의 블로그 리스트 보여주기(클릭하면 해당 블로그 링크로 이동할 수 있도록)
<img width="475" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/455dbde3-4036-4380-8139-254fe18d5231">

- JBlog 로고를 누르면 항상 'http://localhost:8080/jblog03/' 경로의 메인 화면으로 이동할 수 있다
<img width="156" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/5f970228-4491-4a4f-abeb-1edf35ded8fb">


#### (회원정보 관련 - user)
1. 회원가입
- 회원정보 등록
<img width="260" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/9dd9c12e-f295-4b32-8397-72dc65ee0477">

- validation
  - 이름, 아이디, 패스워드에 validation 적용 
  <img width="275" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/e9dba47e-9be3-49ca-8822-8df137a10b32">

- 예외처리
  - 이미 등록된 아이디를 입력하는 경우, 기존에 존재하는 아이디임을 알려준다  
  <img width="275" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/63395431-a20b-4206-afaa-c1ed12ce4ccf">

- 회원가입 완료
<img width="428" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/9f2cfaec-b499-4015-a867-cab57b11147f">

2. 로그인
- 로그인 정보 입력
<img width="268" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/2c5331c1-b829-4c9a-8849-c3d3119624e5">

- 패스워드 틀린 경우
<img width="278" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/50de21e7-7626-440a-8235-1f4194d75884">

- 로그인 성공
  - 로그인 세션이 등록되고, '로그아웃, 내블로그'만 활성화
  - '로그인, 회원가입'는 연한 색 표시, 링크 연결되지 않도록
  - 블로그 리스트에 'hong's Blog'가 추가된 것을 확인할 수 있다
  <img width="406" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/ef17dae9-831b-4b20-aade-ee7de420be9d">






