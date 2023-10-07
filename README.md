# Assignment04 - JBlog03(Spring)

### [구현하면서 정리한 md files]
🐋 [Transaction 적용 방법](https://github.com/YeJi222/jblog/blob/main/transaction.md)    
🐋 [정규표현식 사용 방법](https://github.com/YeJi222/jblog/blob/main/regex.md)

### [파일 위치]
- jblog 모델 mwb 파일 : jblog/schema/jblog.mwb
<img width="521" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/ecc2ddaa-df69-49e0-9012-9df54522c8ba">

- 주요 files 
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
- 로그인 세션 정보가 있을 때
  - '로그인, 회원가입'만 활성화
  - '로그아웃, 내블로그'는 연한 색 표시, 링크 연결되지 않도록
- 등록된 회원들의 블로그 리스트 보여주기(클릭하면 해당 블로그 링크로 이동할 수 있도록)
<img width="475" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/455dbde3-4036-4380-8139-254fe18d5231">

- JBlog 로고를 누르면 항상 'http://localhost:8080/jblog03/' 경로의 메인 화면으로 이동할 수 있다
<img width="156" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/5f970228-4491-4a4f-abeb-1edf35ded8fb">

***

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

- ** 트랜잭션 사용
  - 회원가입 시, user 테이블에 회원 정보 삽입, blog 테이블에 블로그 정보 삽입, category 테이블에 '미분류' 카테고리 삽입이 이루어진다
  - 하나라도 실패하면, 롤백이 일어나도록 트랜잭션을 적용하였다 

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

***

#### (블로그 화면)
- http://localhost:8088/jblog/[사용자 아이디]
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

- 등록된 게시글이 없는 경우 
<img width="826" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/94d5d319-6abb-412f-8133-671cbd4e3066">

***

#### (블로그 관리 페이지 화면)
- 인증된 사용자가 자신의 블로그에 접근하면 관리메뉴가 나타나고 관리 메뉴를 통해 블로그 관리페이지에 접근할 수 있다
- 관리 페이지에서는 블로그의 여러 설정을 변경할 수 있다

1. 기본설정(admin-main)
- 블로그 제목, 로고 이미지 변경 가능
- 변경 전
<img width="830" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/84ccdbb2-2cae-454e-b475-e958aef6f1d6">

- 변경 후
<img width="829" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/44fd6aff-2b5f-4e59-b80d-3428d6fa0de1">

- 블로그 화면에도 블로그 제목과 로고 이미지가 변경된 것을 확인할 수 있다
<img width="826" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/babb5472-c692-4924-8d98-07e5a89382bf">

2. 카테고리(admin-category) 및 글작성(admin-write)
- 회원가입 후, 자동 생성된 블로그는 디폴트로 '미분류' 카테고리가 함께 생성된다
- 카테고리 페이지에서 새로운 카테고리를 생성 및 삭제할 수 있다
- 카테고리를 삭제하면, 해당 카테고리에 있던 게시글들이 모두 삭제 된 후, 카테고리가 삭제된다
- ** 이때, 트랜잭션을 사용하여, 게시글 삭제나 카테고리 삭제에 하나라도 문제가 있을 경우 롤백이 일어나게 하였다   

- 카테고리 추가
<img width="218" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/85384dec-7951-4314-9472-a272814764eb">

- 추가된 것 확인 
<img width="661" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/44ee0258-5f68-4847-8d49-5d0f51be52bb">

- 카테고리 리스트에도 추가된 것 확인 가능 
<img width="180" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/7f340202-86a4-477e-b4a5-dc0842a61737">

- 글 작성시, 추가한 카테고리 선택 가능 
<img width="488" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/38bad89a-767c-49d5-949d-e19db7d77c65">

- 글 작성하기
<img width="495" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/f4b94d2b-d57a-43f4-be96-b84258fb5869">

- 추가한 게시글 확인
<img width="820" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/24022edd-56d9-4a2d-a8f9-dffebf95fde4">

- 카테고리 테이블 포스트 수에도 반영된 것을 확인 가능 
 <img width="666" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/3f305d7e-57b1-46f3-ae14-cc7d2bda2a83">

- 카테고리 삭제
<img width="663" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/6f885bd9-8dce-49e3-b378-be7ae35b9e13">

- 미분류 카테고리에 게시글 2개를 추가한 후, 삭제해보기
- '미분류' 카테고리와 해당 카테고리에 있던 게시글 모두 삭제된 것을 확인
<img width="663" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/2fbecd46-de61-4e2f-84c0-4ee23f0a3181">

- 블로그 화면에서도 '미분류' 카테고리가 삭제되고, 전체 리스트에도 해당 게시글들이 삭제된 것을 확인할 수 있다
<img width="824" alt="image" src="https://github.com/YeJi222/jblog/assets/70511859/e15c7687-ca0f-4634-b1e4-ef52319be8d6">

- (+) 로그인을 하더라도, 자신의 블로그가 아닌데 블로그 관리 페이지로 접속하려고 시도하면, 해당 블로그 주인의 블로그 화면으로 이동하도록 Interceptor를 구성하였다

