package com.sparta.taskflow.auth.dto.request;

import com.sparta.taskflow.auth.annotation.ValidPasswordPattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RegisterRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "4-20자의 영문과 숫자를 입력하세요.")
    @NotNull
    private String username;

    @Email
    @NotNull
    private String email;

    @ValidPasswordPattern
    @NotNull
    private String password;

    @Size(min = 2, max = 50)
    @NotNull
    private String name;
}
