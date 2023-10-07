# Regex

### [정규표현식으로 URI에서 blogId 추출하기]
- 정규 표현식 import : Pattern, Matcher
```java
import java.util.regex.Matcher;
import java.util.regex.Pattern;
```

- 정규 표현식 패턴]
- 'http://localhost:8080/jblog03/'와 그 다음 슬래시('/') 사이 [blogId] 세그먼트 추출
- [^/] : '/' 문자를 제외(^ 부정을 의미)한 모든 문자와 매치한다는 의미
- '+' : 바로 앞의 패턴([^/]+)이 하나 이상의 문자를 나타내야 한다는 의미
- 즉, '/'가 아닌 문자가 하나 이상 나오는 경우를 추출
- jblog03/와 / 사이에 오는 blogId를 추출할 수 있다

Ex) requestURI : http://localhost:8080/jblog03/yizi/admin/basic   
추출 => blogId : yizi

```java  
  String requestURI = request.getRequestURI();

  Pattern pattern = Pattern.compile("/jblog03/([^/]+)/");
  Matcher matcher = pattern.matcher(requestURI);

  String blogId = "";
  if (matcher.find()) { // 패턴과 일치하는 부분 찾기
      blogId = matcher.group(1);
  }
```
