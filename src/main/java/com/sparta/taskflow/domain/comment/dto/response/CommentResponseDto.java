package com.sparta.taskflow.domain.comment.dto.response;

import com.sparta.taskflow.domain.comment.entity.Comment;
import com.sparta.taskflow.domain.user.dto.response.UserResponseDto;
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
    private UserResponseDto user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentResponseDto of(Comment comment, UserResponseDto user) {
        return CommentResponseDto.builder()
                                 .id(comment.getId())
                                 .content(comment.getContent())
                                 .taskId(comment.getTaskId())
                                 .userId(comment.getUserId())
                                 .user(user)
                                 .createdAt(comment.getCreatedAt())
                                 .updatedAt(comment.getUpdatedAt())
                                 .build();
    }
}