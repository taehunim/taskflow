package com.sparta.taskflow.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 클라이언트 전달할 예외 정보 일원화
 */
@Getter
@AllArgsConstructor
public class ErrorResponseDto {

    private final int status; // HTTP 상태코드
    private final String errorCode; // 에러 코드 이름
    private final String message; // 클라이언트에게 전달할 에러 내용

    public ErrorResponseDto(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.errorCode = errorCode.name();
        this.message = errorCode.getMessage();
    }
}