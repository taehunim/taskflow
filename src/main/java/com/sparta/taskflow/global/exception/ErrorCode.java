package com.sparta.taskflow.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 모든 예외 상황을 Enum으로 구성하여 HTTP 상태코드와 에러 내용을 명확하게 정의한다
 */
@Getter
public enum ErrorCode {

    // 공통 에러 정의
    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생하였습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원하는 HTTP 메서드가 아닙니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "입력 값이 유효하지 않습니다."),

    // User 관련 에러 정의
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자명입니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    // 인증 관련 에러
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "잘못된 사용자명 또는 비밀번호입니다."),

    // Task 관련 에러 정의
    ASSIGNEE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않거나 탈퇴된 담당자입니다."),
    INVALID_STATUS_TRANSITION(HttpStatus.BAD_REQUEST, "잘못된 상태 변경입니다."),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 작업을 찾을 수 없습니다."),

    // Comment 관련 에러
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),
    COMMENT_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 삭제된 댓글입니다."),
    COMMENT_TASK_MISMATCH(HttpStatus.BAD_REQUEST, "해당 댓글은 지정된 태스크에 속하지 않습니다.");

    // 필드
    // HTTP 상태코드
    private final HttpStatus status;

    // 클라이언트 측에 전달되는 내용
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}