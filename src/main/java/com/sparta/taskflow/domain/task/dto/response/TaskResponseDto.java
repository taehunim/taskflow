package com.sparta.taskflow.domain.task.dto.response;

import com.sparta.taskflow.domain.task.entity.Task;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDto {

    private Long id;
    private String title;
    private String description;
    private String priority;
    private Long assigneeId;
    private String assigneeName;
    private LocalDateTime dueAt;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TaskResponseDto of(Task task, String assigneeName) {
        return TaskResponseDto.builder()
                              .id(task.getId())
                              .title(task.getTitle())
                              .description(task.getDescription())
                              .priority(task.getPriority())
                              .assigneeId(task.getAssigneeId())
                              .assigneeName(assigneeName)
                              .dueAt(task.getDueAt())
                              .status(task.getStatus())
                              .createdAt(task.getCreatedAt())
                              .updatedAt(task.getUpdatedAt())
                              .build();
    }

}
