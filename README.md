# Spring Boot for REST API 표준 설정
### 아래 서순은 참고용도로만 사용할 것

## 기본 설정 서순
1. eclipse or vscode로 spring boot 프로젝트 생성(아래는 vscode로 할 경우 서순)
    - 여기선 Gradle로 사용
    - 필수 플러그인
        a) Java Extension Pack (jdk 11 이상 필요)
        b) Srping Boot Extension Pack
        c) Lombok Annotations Support for VS Code
        d) Spring Initializr Java Support
        e) Spring Boot Dashboard
    - Dependencies는 필요한 것들만 추가(여기선 5개 사용)
        a) Spring Boot DevTools
        b) Lombok
        c) Mybatis Framework
        d) MySQL Driver
        e) Spring Web
2. properties 설정을 위해 application.yml 파일 생성
    - database source code 입력
3. 정상 실행 확인을 위해 테스트 코드 생성
    - RestController와 GetMapping annotation 사용

## json 리턴 테스트

## slf4j + logback 로그 설정

## Service 생성
1. Service Annotation 활용
2. 필드 주입이 아닌 생성자 주입 방법을 권장
    - 생성자 주입 방법의 장점
        a) 순환참조 방지 : A가 B를 참조하고 B가 A를 참조할 때의 문제
        b) 테스트하기에 좋음
        c) final 선언 가능
        d) 오류 방지 
3. Service Annotation을 통해 bean에 service 등록
4. Autowired Annotation이 추가된 생성자에서 BeanFactory에서 해당 Service를 찾음
5. 생성자 주입
6. 해당 서비스 호출 후 사용

## 