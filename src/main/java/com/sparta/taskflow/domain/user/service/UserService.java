package com.sparta.taskflow.domain.user.service;

import com.sparta.taskflow.domain.task.repository.TaskRepository;
import com.sparta.taskflow.domain.user.dto.request.DeleteUserRequestDto;
import com.sparta.taskflow.domain.user.dto.response.UserResponseDto;
import com.sparta.taskflow.domain.user.entity.User;
import com.sparta.taskflow.domain.user.repository.UserRepository;
import com.sparta.taskflow.global.exception.CustomException;
import com.sparta.taskflow.global.exception.ErrorCode;
import com.sparta.taskflow.global.validator.PasswordValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordValidator passwordValidator;

    public UserResponseDto getUser(Long loginUserId) {
        User foundUser = userRepository.findByIdAndIsDeletedFalseOrElseThrow(loginUserId);
        return UserResponseDto.of(foundUser);
    }

    @Transactional
    public void deleteUser(Long loginUserId, DeleteUserRequestDto deleteUserDto) {
        User user = userRepository.findByIdAndIsDeletedFalse(loginUserId).orElseThrow(
            () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        passwordValidator.verifyMatch(deleteUserDto.getPassword(), user.getPassword());

        userRepository.deleteById(user.getId());

        // Task 미할당 상태로 변경
        taskRepository.unassignTasksByUserId(user.getId());
    }
}
