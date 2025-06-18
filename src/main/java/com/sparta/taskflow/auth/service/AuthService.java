package com.sparta.taskflow.auth.service;

import com.sparta.taskflow.auth.dto.request.RegisterRequestDto;
import com.sparta.taskflow.auth.dto.response.TokenResponse;
import com.sparta.taskflow.auth.jwt.JwtUtil;
import com.sparta.taskflow.domain.user.dto.response.UserResponseDto;
import com.sparta.taskflow.domain.user.entity.User;
import com.sparta.taskflow.domain.user.repository.UserRepository;
import com.sparta.taskflow.domain.user.type.RoleType;
import com.sparta.taskflow.global.exception.CustomException;
import com.sparta.taskflow.global.exception.ErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto register(RegisterRequestDto requestDto) {
        checkDuplicatesOrThrow(requestDto.getUsername(), requestDto.getEmail());

        User user = User.builder().username(requestDto.getUsername()).email(requestDto.getEmail())
                        .password(passwordEncoder.encode(requestDto.getPassword()))
                        .name(requestDto.getName())
                        .role(RoleType.USER) // Todo: Role이 요청에 없는데 응답에는 있다
                        .isDeleted(false).build();

        User savedUser = userRepository.save(user);

        return UserResponseDto.of(savedUser);
    }

    public TokenResponse login(String username, String password) {
        // 사용자를 찾을 수 없는 경우에도 자세한 이유는 숨김
        User foundUser = userRepository.findByUsername(username).orElseThrow(
            () -> new CustomException(ErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(password, foundUser.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        String token = jwtUtil.createToken(foundUser.getId(), foundUser.getRole());
        return new TokenResponse(token);
    }

    private void checkDuplicatesOrThrow(String username, String email) {
        // username 중복 확인
        boolean hasDuplicateUsername = userRepository.existsByUsername(username);
        if (hasDuplicateUsername) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

        // email 중복 확인
        boolean hasDuplicateEmail = userRepository.existsByEmail(email);
        if (hasDuplicateEmail) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
    }
}
