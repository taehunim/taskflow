package com.sparta.taskflow.domain.user.controller;

import com.sparta.taskflow.domain.user.dto.request.DeleteUserRequestDto;
import com.sparta.taskflow.domain.user.dto.response.UserResponseDto;
import com.sparta.taskflow.domain.user.service.UserService;
import com.sparta.taskflow.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // TODO : SpringSecurity 적용 이후 로그인 유저 정보 받는 방법으로 변경 필요.
    // TODO : 조회 시 isDeleted = false인 유저만 조회하도록 변경
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserByLoginUser(@RequestParam Long loginUserId) {
        UserResponseDto response = userService.getUser(loginUserId);
        return ResponseEntity.ok(ApiResponse.success("사용자 정보를 조회했습니다.", response));
    }

}
