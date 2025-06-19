package com.sparta.taskflow.domain.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    @NotNull(message = "내용은 필수입니다.")
    private String content;

    @NotNull(message = "유저ID는 필수입니다.")
    private Long userId;

    public CommentRequestDto(String content, Long userId) {
        this.content = content;
        this.userId = userId;
    }
}