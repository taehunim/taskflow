package com.sparta.taskflow.domain.comment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCommentRequestDto {

    private String content;
    private Long taskId;
    private Long userId;
}