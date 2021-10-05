# Portfolio-SpringBoot-Board
## 사용 기술
* Java11
* Gradle
* Spring Boot
* Spring Security
* Thymeleaf
* JPA
* MariaDB
* Bootstrap5.1
<br/>

## 구현된 기능
* 에러 페이지 처리(@ControllerAdvice)
* 게시글 목록(댓글 개수 표시)
* 게시글 상세보기
* 검색(제목, 내용, 제목+내용)
* 회원가입(비밀번호 암호화는 Bcrypt를 활용)
* 로그인/로그아웃(Spring Security 활용)

> (로그인 후)  
* 게시글 작성, 본인 게시글 수정/삭제
* 댓글/대댓/대대댓 작성, 본인 댓글 삭제
* 회원정보 조회
* 비번 변경
* 탈퇴(작성한 게시글과 댓글 모두 삭제 후 탈퇴 처리)
* 본인이 작성한 게시글 조회
