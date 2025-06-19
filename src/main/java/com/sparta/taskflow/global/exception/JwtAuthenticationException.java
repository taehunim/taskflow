package com.sparta.taskflow.global.exception;

import lombok.Getter;

@Getter
public class JwtAuthenticationException extends RuntimeException {

    private final String errorCode;

    public JwtAuthenticationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
