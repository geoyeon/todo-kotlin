# Todo API(Kotlin)
```declarative
해당 ReadMe 문서는 gemini로 생성함
```

Spring Boot와 Kotlin, MongoDB를 사용한 RESTful Todo API 프로젝트입니다.


## 기술 스택

- Kotlin
- Spring Boot
- MongoDB
- Gradle
- Jakarta Validation

## API 명세

### Todo 생성
- **URL**: `/todos/`
- **Method**: `POST`
- **Headers**:
  - Content-Type: application/json
- **Request Body**:

```json
{
    "title": "할 일 제목",
    "content": "할 일 내용"
}
```

- **Response**: 200 OK
    - Body: 생성된 Todo 객체

### Todo 목록 조회
- **URL**: `/todos/`
- **Method**: `GET`
- **Query Parameters**:
    - page: 페이지 번호 (선택, 기본값: 1)
    - isComplete: 완료 여부 필터링 (선택)
    - search: 검색어 (선택)
- **Response**: 200 OK
    - Body: TodoListResponse 객체

### Todo 단일 조회
- **URL**: `/todos/{id}`
- **Method**: `GET`
- **Path Parameters**:
    - id: Todo ID (문자열)
- **Response**:
    - 200 OK: Todo 객체
    - 404 Not Found: Todo를 찾을 수 없는 경우

### Todo 수정
- **URL**: `/todos/{id}`
- **Method**: `PATCH`
- **Path Parameters**:
    - id: Todo ID (문자열)
- **Request Body**: TodoUpdateRequest 객체
- **Response**: 204 No Content

## 주요 기능

- MongoDB Auditing 활성화 (`@EnableMongoAuditing`)
- Slf4j를 통한 API 호출 로깅
- Jakarta Validation을 사용한 요청 데이터 검증
- 커스텀 예외 처리 (TodoException)
- ResponseEntity를 통한 HTTP 응답 처리

## 에러 처리

- `ErrorCode.NOT_FOUND_TODO`: Todo를 찾을 수 없는 경우
- 유효성 검증 실패: 400 Bad Request
```