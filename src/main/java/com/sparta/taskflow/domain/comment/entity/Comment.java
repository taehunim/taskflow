package com.sparta.taskflow.domain.comment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Long taskId;

    private Long userId;

    private LocalDateTime createdAt;

    private Boolean isDeleted = false;

    private LocalDateTime deletedAt;

    @Builder
    public Comment(String content, Long taskId, Long userId) {
        this.content = content;
        this.taskId = taskId;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.isDeleted = false;
    }
}