package com.sparta.taskflow.domain.user.dto;

import com.sparta.taskflow.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryDto {

    private Long id;
    private String username;
    private String name;
    private String email;

    public static UserSummaryDto of(User user) {
        return UserSummaryDto.builder()
                             .id(user.getId())
                             .username(user.getUsername())
                             .name(user.getName())
                             .email(user.getEmail())
                             .build();
    }

}
