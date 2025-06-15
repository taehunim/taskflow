package com.sparta.taskflow.global.exception;

import lombok.Getter;

/**
 * ErrorCode 받아서 예외를 던져주면
 * 글로벌 핸들러가 예외를 받아서
 * 내용과 상태코드를 반환
 */
@Getter
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // 부모 RuntimeException의 message 필드 할당
        this.errorCode = errorCode; // 본인의 필드의 할당
    }
}