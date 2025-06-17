package com.sparta.taskflow.domain.comment.entity;

import com.sparta.taskflow.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 연관관계 관련 논의 후 수정
    private String content;

    private Long taskId;

    private Long userId;

    private Boolean isDeleted = false;

    private LocalDateTime deletedAt;

    @Builder
    public Comment(String content, Long taskId, Long userId) {
        this.content = content;
        this.taskId = taskId;
        this.userId = userId;
        this.isDeleted = false;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}