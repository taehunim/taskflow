package com.sparta.taskflow.domain.task.entity;

import com.sparta.taskflow.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "creator_id", nullable = false)
    //private User creator;

    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private String priority;
    private String status;
    @Column(name = "assignee_id")
    private Long assigneeId;
    @Column(name = "start_at")
    private LocalDateTime startAt;
    @Column(name = "due_at")
    private LocalDateTime dueAt;
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
