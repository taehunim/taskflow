package com.sparta.taskflow.domain.comment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommentRequestDto {

    private String content;
    private Long taskId;
    private Long userId;

    public CreateCommentRequestDto(String content, Long taskId, Long userId) {
        this.content = content;
        this.taskId = taskId;
        this.userId = userId;
    }
}