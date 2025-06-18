package com.sparta.taskflow.global.validator;

import com.sparta.taskflow.global.exception.CustomException;
import com.sparta.taskflow.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordValidator {

    private final PasswordEncoder passwordEncoder;

    /**
     * 입력받은 비밀번호와 기존의 비밀번호의 일치여부에 따른 예외 처리
     *
     * @param inputPassword    입력받은 비밀번호
     * @param originalPassword 기존의 비밀번호
     */
    public void verifyMatch(String inputPassword, String originalPassword) {
        if (!passwordEncoder.matches(inputPassword, originalPassword)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
    }

}
