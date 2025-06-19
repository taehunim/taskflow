package com.sparta.taskflow.domain.user.repository;

import com.sparta.taskflow.domain.user.entity.User;
import java.util.Optional;
import com.sparta.taskflow.global.exception.CustomException;
import com.sparta.taskflow.global.exception.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsernameAndIsDeletedFalse(String username);
  
    Optional<User> findByIdAndIsDeletedFalse(Long id);

    default User findByIdAndIsDeletedFalseOrElseThrow(Long loginUserId) {
        return findByIdAndIsDeletedFalse(loginUserId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
