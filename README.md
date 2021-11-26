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

## 