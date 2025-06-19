package com.sparta.taskflow.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    private String content;
    private Long userId;

    public CommentRequestDto(String content, Long userId) {
        this.content = content;
        this.userId = userId;
    }
}