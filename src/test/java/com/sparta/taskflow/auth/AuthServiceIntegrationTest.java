package com.sparta.taskflow.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.sparta.taskflow.auth.dto.request.RegisterRequestDto;
import com.sparta.taskflow.auth.service.AuthService;
import com.sparta.taskflow.domain.user.dto.response.UserResponseDto;
import com.sparta.taskflow.global.exception.CustomException;
import com.sparta.taskflow.global.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest // Mockito 의존성 없어 일단 SpringBootTest 사용
@Transactional
@ActiveProfiles("test")
class AuthServiceIntegrationTest {

    @Autowired
    AuthService authService;

    @Test
    void 정상_회원가입() {
        // given
        RegisterRequestDto requestDto = getRegisterRequestDto("user@email.com", "username");

        // when
        UserResponseDto responseDto = authService.register(requestDto);

        // then
        assertThat(responseDto.getId()).isNotNull();
    }

    @Test
    void 회원가입_이메일_중복() {
        // given
        RegisterRequestDto request1 = getRegisterRequestDto("user@email.com", "username1");
        RegisterRequestDto duplicateEmailRequest = getRegisterRequestDto("user@email.com",
            "username2");

        // when
        authService.register(request1);
        Throwable thrown = catchThrowable(() -> authService.register(duplicateEmailRequest));

        // then
        assertThat(thrown)
            .isInstanceOf(CustomException.class);
        CustomException exception = (CustomException) thrown;
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.DUPLICATE_EMAIL);
    }

    @Test
    void 회원가입_유저네임_중복() {
        // given
        RegisterRequestDto request1 = getRegisterRequestDto("user@email.com", "username1");
        RegisterRequestDto duplicateUsernameRequest = getRegisterRequestDto("user2@email.com",
            "username1");

        // when
        authService.register(request1);
        Throwable thrown = catchThrowable(() -> authService.register(duplicateUsernameRequest));

        // then
        assertThat(thrown)
            .isInstanceOf(CustomException.class);
        CustomException exception = (CustomException) thrown;
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.DUPLICATE_USERNAME);
    }

    private static RegisterRequestDto getRegisterRequestDto(String email, String username) {
        RegisterRequestDto requestDto = new RegisterRequestDto();
        ReflectionTestUtils.setField(requestDto, "email", email);
        ReflectionTestUtils.setField(requestDto, "username", username);
        ReflectionTestUtils.setField(requestDto, "password", "password");
        ReflectionTestUtils.setField(requestDto, "name", "name");
        return requestDto;
    }
}