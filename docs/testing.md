# 테스트와 실행 범위

Java 21과 Gradle의 `compileJava`로 소스를 컴파일합니다. DB 통합에는 Mapper와 일치하는 MariaDB 테이블이 필요합니다. 업로드·권한·트랜잭션 경계는 컴파일과 별도의 테스트 대상입니다.
