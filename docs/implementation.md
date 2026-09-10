# 요청과 저장 처리

`GET /gall/list`는 목록 template을 반환하고 `GET /gall/list/data`는 `nowPage`를 Service에 전달해 목록·페이지 정보를 JSON으로 반환합니다. Mapper XML과 MariaDB의 `tb_gallery` 구조가 맞아야 합니다.

DB 연결은 환경변수, 파일 저장은 `UPLOAD_DIR`로 설정합니다. Java 21과 Lombok annotation processing이 빌드에 필요합니다. 업로드 형식·크기 제한, 권한, 파일과 DB 처리의 일관성은 운영 전에 보완할 범위입니다.
