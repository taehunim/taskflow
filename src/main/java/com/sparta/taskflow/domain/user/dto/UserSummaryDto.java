package com.sparta.taskflow.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSummaryDto {

    private Long id;
    private String username;
    private String name;
    private String email;

}
