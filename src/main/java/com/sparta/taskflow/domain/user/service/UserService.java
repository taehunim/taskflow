package com.sparta.taskflow.domain.user.service;

import com.sparta.taskflow.domain.user.dto.response.UserResponseDto;
import com.sparta.taskflow.domain.user.entity.User;
import com.sparta.taskflow.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto getUser(Long loginUserId) {
        User findUser = userRepository.findByIdOrElseThrow(loginUserId);
        return UserResponseDto.of(findUser);
    }
}
