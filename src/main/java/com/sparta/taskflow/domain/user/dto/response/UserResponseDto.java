package com.sparta.taskflow.domain.user.dto.response;

import com.sparta.taskflow.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponseDto {

    private Long id;
    private String username;
    private String email;
    private String name;
    private String role;
    private LocalDateTime createdAt;

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                              .id(user.getId())
                              .username(user.getUsername())
                              .email(user.getEmail())
                              .name(user.getName())
                              .role(user.getRole().getValue())
                              .createdAt(user.getCreatedAt())
                              .build();
    }
}
