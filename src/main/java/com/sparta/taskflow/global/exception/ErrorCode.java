package com.sparta.taskflow.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 모든 예외 상황을 Enum으로 구성하여
 * HTTP 상태코드와 에러 내용을
 * 명확하게 정의한다
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
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "이미 사용 중 인 사용자 이름입니다");

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