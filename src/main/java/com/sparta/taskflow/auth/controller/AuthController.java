package com.sparta.taskflow.auth.controller;

import com.sparta.taskflow.auth.service.AuthService;
import com.sparta.taskflow.auth.dto.request.RegisterRequestDto;
import com.sparta.taskflow.domain.user.dto.request.DeleteUserRequestDto;
import com.sparta.taskflow.domain.user.dto.response.UserResponseDto;
import com.sparta.taskflow.domain.user.service.UserService;
import com.sparta.taskflow.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> register(
        @Valid @RequestBody RegisterRequestDto requestDto) {
        UserResponseDto responseDto = authService.register(requestDto);

        ApiResponse<UserResponseDto> response = ApiResponse.success("회원가입이 완료되었습니다.", responseDto);

        return ResponseEntity.ok(response);
    }

    // TODO : SpringSecurity 적용 이후 로그인 유저 정보 받는 방법으로 변경 필요.
    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<Void>> withdraw(
        @RequestParam Long loginUserId,
        @RequestBody @Valid DeleteUserRequestDto deleteUserDto
    ) {
        userService.deleteUser(loginUserId, deleteUserDto);
        ApiResponse<Void> response = ApiResponse.success("회원탈퇴가 완료되었습니다.", null);
        return ResponseEntity.ok(response);
    }
}
