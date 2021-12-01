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
 
## Spring jdbc namedParameterJdbcTemplate, MySQL 연동
1. Mybatis가 아닌 jdbcTemplate 사용
    - 왜그런지에 대해선 https://hello-bryan.tistory.com/335 참고
2. mybatis dependency를 제거하고 jdbc dependency 추가하기
3. 테스트하려는 table에 맞게 model과 repository 생성
4. 소스 관리를 위해 class를 만들어서 Groovy의 Multiline String으로 선언
    - Groovy를 사용하기 위해 plugins과 dependencies를 추가
    - repository 패키지 아래에 groovy file 생성(sql 옮기기. SQL 관리용) !!! vscode인지 무슨 문제인지 모르겠지만 groovy를 못찾아서 그냥 자바로 처리했음
    - RowMapper도 별도로 분리하기 위해 CityRowMapper.java 생성(RowMapper 옮기기)
5. service와 controller에도 list mapping 추가

## DBCP - HikariCP
1. Connection Pool 중 성능이 제일 좋은 HikariCP 선택

## jdbcTemplate - Select
1. Controller에 RequestMapping을 추가해서 route 관리
2. PathVariable 또는 RequestParam을 통해 파라미터 전달
3. service에 method 추가
4. sql 추가
    - statement 파라미터가 :columnName으로 되어있는데 여기로 전달함
5. repository에 파라미터 포함해서 method 추가
    - 파라미터 전달을 위해 MapSqlParameterSource 사용
6. 정확히 서순은 sql -> repository -> service -> controller 순으로 하면 맞을듯

## GetMapping, PostMapping

## jdbcTemplate - Insert, Update, Delete
1. PostMapping 으로 CRUD 구현

## File Upload / Download / List
1. file 관련 설정을 application.yml에 추가
2. file 관련 서비스 처리를 위해 storage package 생성
    - interface로 service java 생성
    - file을 처리할 수 있는 method 추가
3. controller에 stoargeservice를 사용하기 위해 생성자 주입 및 method 추가
4. download 구현을 위해 implement 메소드 중 load와 loadAsResource 작성
5. controller에 다운로드 api 추가
6. delete method 구현을 위해 코드 추가
7. file list의 경우 file들의 정보를 list에 담아서 리턴해야하기 때문에 FileData라는 class 추가

## form-data + json list string

## json string model class mapping