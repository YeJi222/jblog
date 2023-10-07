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

3. 블로그 주소에서, 인증 유무에 따른 Top 상태 변화
- 로그인 전 Top
  - '로그인'만 활성화
  - '블로그 관리, 로그아웃'은 연한 색 표시, 링크 연결되지 않도록
  <img width="827" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/34ea6480-b174-421f-b8af-220ecfc0c3e7">

- 로그인 후 Top
  - '블로그 관리, 로그아웃'만 활성화
  - '로그인'는 연한 색 표시, 링크 연결되지 않도록
  <img width="826" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/b793b956-546a-462c-b920-1ea53c3e9b66">

- 자신의 블로그가 아닌 다른 블로그 주소에서 로그인 후 Top
  - '로그아웃'만 활성화
  - '블로그 관리, 로그인'는 연한 색 표시, 링크 연결되지 않도록
  <img width="827" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/a48c5010-65e3-428d-bf28-2210af1f8536">
- 블로그 이름을 클릭하면, 항상 'http://localhost:8088/jblog/[사용자 아이디]' 주소로 접근하여 블로그 링크로 이동가능
- 왼쪽 상단의 JBlog를 클릭하면, 'http://localhost:8080/jblog03/' 경로의 메인 화면으로 이동가능
<img width="75" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/58a7f507-807c-4348-b1be-f354d7acebdb">

#### (블로그 관련 - blog)
- http://localhost:8088/jblog/[사용자 아이디]
1. 블로그 화면
- 회원가입을 하면 해당 사용자의 블로그가 지동 생성
- 자동 생성된 블로그에서 블로그 이름, 카테고리, 게시글 등이 디폴트로 설정된다
- 블로그 관리에서 블로그 이름, 카테고리, 게시글 등 추가 및 세팅할 수 있다 
<img width="826" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/8d4d4ba9-0607-423c-904f-036015088440">

- 생성된 블로그는 블로그 주인뿐만 아니라 인증 없는 외부 접근이 가능
- 로그인 세션 정보가 없는 상태에서도, 블로그 주소 접근 가능
<img width="826" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/79c84ae5-7d80-4a8c-bfef-ffd3ff559e00">

- 오른쪽에는 블로그 로고 사진, 카테고리 리스트가 위치하고 있다
- 'http://localhost:8088/jblog/[사용자 아이디]'로 접속하면, 기본적으로 해당 블로그의 전체 블로그 리스트와 가장 최근 게시글을 보여준다
- URL : http://localhost:8080/jblog03/yizi
<img width="831" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/461fb595-665c-4595-879e-042f6c34a100">

- 카테고리를 누르면, 카테고리에 해당되는 게시글 리스트와 카테고리 내, 가장 최근 게시글을 보여준다
- '스프링' 카테고리를 클릭했을 때
- URL : http://localhost:8080/jblog03/yizi/6
- jblog03/사용자ID/카테고리Num
<img width="828" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/8c3daf8e-a628-403a-9a6d-8a2e831aa078">

- 게시글 리스트에서 게시글 제목을 누르면, 해당 게시글의 제목과 내용을 보여준다
- URL : http://localhost:8080/jblog03/yizi/6/7?type=category
- jblog03/사용자ID/카테고리Num/게시글Num
<img width="826" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/473a5491-c8d8-4039-bf1e-2d6a64bc0c39">






2. 블로그 관리 페이지 
- 인증된 사용자가 자신의 블로그에 접근하면 관리메뉴가 나타나고 관리 메뉴를 통해 블로그 관리페이지에 접근할 수 있다
- 관리 페이지에서는 블로그의 여러 설정을 변경할 수 있다









