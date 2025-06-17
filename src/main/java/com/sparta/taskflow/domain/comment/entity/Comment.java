package com.sparta.taskflow.domain.comment.entity;

import com.sparta.taskflow.domain.task.entity.Task;
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

    private String content;

    // TODO : task entity 생성 후 수정 예정
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "task_id", nullable = false)
    // private Task task;

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