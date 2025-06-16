package com.sparta.taskflow.domain.task.dto.response;

import com.sparta.taskflow.domain.task.entity.Task;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreateTaskResponseDto {

    private Long id;
    private LocalDateTime createdAt;
    private String title;
    private String description;
    private String priority;
    private Long assigneeId;
    private String assigneeName;
    private LocalDateTime dueAt;
    private String status;

    public static CreateTaskResponseDto of(Task task, String assigneeName) {
        return CreateTaskResponseDto.builder()
                .id(task.getId())
                .createdAt(task.getCreatedAt())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .status(task.getStatus())
                .assigneeId(task.getAssigneeId())
                .assigneeName(assigneeName)
                .dueAt(task.getDueAt())
                .build();
    }

}
