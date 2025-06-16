package com.sparta.taskflow.domain.comment.dto;

import com.sparta.taskflow.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponseDto {

    private Long id;
    private String content;
    private Long taskId;
    private Long userId;
    private LocalDateTime createdAt;

    public static CommentResponseDto of(Comment comment) {
        return CommentResponseDto.builder()
                                 .id(comment.getId())
                                 .content(comment.getContent())
                                 .taskId(comment.getTaskId())
                                 .userId(comment.getUserId())
                                 .createdAt(comment.getCreatedAt())
                                 .build();
    }
}