package com.sparta.taskflow.response;

import com.sparta.taskflow.global.exception.ErrorResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    private ApiResponse(boolean success, String message, T date) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    // 성공시 응답
    // 앞에 <T>는 정적 메서드에서 제네릭을 사용하기 위해 선언
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    // 실패시 응답
    public static <T> ApiResponse<T> fail(String message, T errorData) {
        return new ApiResponse<>(false, message, errorData);
    }
}
