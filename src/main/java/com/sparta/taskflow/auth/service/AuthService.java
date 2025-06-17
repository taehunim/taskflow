package com.sparta.taskflow.auth.service;

import com.sparta.taskflow.auth.dto.request.RegisterRequestDto;
import com.sparta.taskflow.domain.user.dto.response.UserResponseDto;
import com.sparta.taskflow.domain.user.entity.User;
import com.sparta.taskflow.domain.user.repository.UserRepository;
import com.sparta.taskflow.domain.user.type.RoleType;
import com.sparta.taskflow.global.exception.CustomException;
import com.sparta.taskflow.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
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
