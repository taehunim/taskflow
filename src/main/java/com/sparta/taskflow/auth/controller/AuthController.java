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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(
        @Valid @RequestBody LoginRequestDto requestDto) {
        TokenResponse responseDto = authService.login(requestDto.getUsername(),
            requestDto.getPassword());

        ApiResponse<TokenResponse> response = ApiResponse.success("로그인이 완료되었습니다.", responseDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<Void>> withdraw(
        @AuthenticationPrincipal User user,
        @RequestBody @Valid DeleteUserRequestDto deleteUserDto
    ) {
        Long loginUserId = Long.valueOf(user.getUsername());
        userService.deleteUser(loginUserId, deleteUserDto);
        ApiResponse<Void> response = ApiResponse.success("회원탈퇴가 완료되었습니다.", null);
        return ResponseEntity.ok(response);
    }
}