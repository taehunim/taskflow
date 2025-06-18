package com.sparta.taskflow.domain.user.repository;

import com.sparta.taskflow.domain.user.entity.User;
import com.sparta.taskflow.global.exception.CustomException;
import com.sparta.taskflow.global.exception.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    default User findByIdOrElseThrow(Long loginUserId) {
        return findById(loginUserId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
