# Spring 갤러리 백엔드

이미지와 글을 등록하고 페이지 단위로 조회하는 웹 실습입니다. HTTP 요청이 Controller, Service, MyBatis를 거쳐 DB와 화면으로 연결되는 흐름을 구현했습니다.

## 사용 기술

Java 21, Spring Boot 3.4.3, Spring MVC, MyBatis, MariaDB, Thymeleaf, Lombok, Gradle을 사용합니다. 이미지 크기 조절에는 java-image-scaling을 사용합니다.

## 요청과 데이터 흐름

```text
브라우저 → GalleryController → GalleryService → GalleryMapper → MariaDB
                 ↓                   ↓
          Thymeleaf / JSON      이미지 파일·thumbnail
```

`tb_gallery`에는 제목, 작성자, 원본·저장 파일명, 경로와 생성·수정 시각을 저장합니다. 파일 자체와 DB의 메타데이터를 나눠 다룹니다.

| 요청 | 역할 |
|---|---|
| GET /gall/list | 목록 화면 |
| GET /gall/list/data | 페이지별 목록과 페이지 정보 |
| POST /gall/add | 글과 이미지 등록 |

## 구현하면서 나눈 책임

목록 화면과 데이터 요청을 분리해 화면은 Thymeleaf로, 페이지 데이터는 JSON으로 전달합니다. Service의 `PageVO`에서 offset과 페이지 정보를 계산하고 Mapper에서 정렬·조회 범위를 적용합니다.

파일 등록에서는 원본명과 저장명을 구분하고 thumbnail을 만듭니다. SQL과 파일 처리까지 Controller에 모으지 않고 Service·Mapper·이미지 utility로 나눈 것이 주요 설계 연습입니다. DB 연결과 저장 경로는 환경변수로 받습니다.

## 실행

MariaDB에 [GalleryMapper.xml](src/main/resources/mapper/gallery/GalleryMapper.xml)의 `tb_gallery` 구조와 맞는 테이블을 준비합니다. DB 생성·migration은 자동 제공하지 않습니다.

`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, `UPLOAD_DIR`를 설정하고 설치된 Gradle로 실행합니다.

시작 클래스는 [MyBackendApplication.java](src/main/java/kr/it/code/main/MyBackendApplication.java)입니다.

```bash
gradle bootRun
```

## 남은 범위

기본 갤러리 흐름을 연결한 실습이며 인증·권한 관리나 업로드 보안 정책을 완성한 서비스는 아닙니다. 파일 형식·크기 제한, 파일 저장과 DB 처리의 일관성, 접근 권한은 후속 설계가 필요합니다.

[구현 노트](docs/implementation.md) · [소스 목록](docs/source-index.csv)
