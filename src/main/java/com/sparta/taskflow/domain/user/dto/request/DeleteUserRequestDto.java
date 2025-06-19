package com.sparta.taskflow.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteUserRequestDto {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
