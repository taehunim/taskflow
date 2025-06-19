package com.sparta.taskflow.domain.comment.dto;

import com.sparta.taskflow.domain.comment.entity.Comment;
import com.sparta.taskflow.domain.user.dto.response.UserResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponseDto {

    private Long id;
    private String content;
    private Long taskId;
    private Long userId;
    private UserResponseDto user;  // 사용자 정보 포함
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentResponseDto of(Comment comment, UserResponseDto userDto) {
        return CommentResponseDto.builder()
                                 .id(comment.getId())
                                 .content(comment.getContent())
                                 .taskId(comment.getTaskId())
                                 .userId(comment.getUserId())
                                 .user(userDto)
                                 .createdAt(comment.getCreatedAt())
                                 .updatedAt(comment.getUpdatedAt())
                                 .build();
    }
}