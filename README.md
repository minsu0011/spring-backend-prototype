# Spring Gallery Backend

이미지와 글을 등록하고 페이지 단위로 조회하는 Spring Boot 갤러리 실습입니다. Controller·Service·MyBatis·MariaDB를 연결하고 이미지 파일과 썸네일을 관리합니다.

**Java 21 · Spring Boot 3.4.3 · MyBatis · MariaDB · Thymeleaf · Gradle**

## API

| 요청 | 동작 |
| --- | --- |
| `GET /gall/list` | 목록 화면 |
| `GET /gall/list/data` | 페이지별 데이터 |
| `POST /gall/add` | 글과 이미지 등록 |

DB에는 제목·작성자·파일 메타데이터를 저장하고, 이미지 원본과 썸네일은 파일 시스템에 저장합니다.

## 실행

1. [GalleryMapper.xml](src/main/resources/mapper/gallery/GalleryMapper.xml)에 맞는 `tb_gallery` 테이블을 준비합니다.
2. `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, `UPLOAD_DIR`를 설정합니다.
3. 설치된 Gradle로 실행합니다.

```bash
gradle bootRun
```

진입점은 [MyBackendApplication.java](src/main/java/kr/it/code/main/MyBackendApplication.java)입니다. DB migration과 인증·권한·업로드 보안 정책은 별도 구현이 필요합니다.

[구현 노트](docs/implementation.md) · [소스 목록](docs/source-index.csv)

[출처 및 이용 조건](ATTRIBUTION.md)

[전체 문서 안내](docs/README.md)
