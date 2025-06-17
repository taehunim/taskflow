package com.sparta.taskflow.domain.user.dto;

import lombok.Builder;

@Builder
public class UserSummaryDto {

    private Long id;
    private String username;
    private String name;
}
