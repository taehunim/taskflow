package com.sparta.taskflow.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommentRequestDto {

    private String content;
    private Long taskId;
    private Long userId;
}