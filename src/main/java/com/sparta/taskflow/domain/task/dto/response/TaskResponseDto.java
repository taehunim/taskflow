package com.sparta.taskflow.domain.task.dto.response;

import com.sparta.taskflow.domain.task.entity.Task;
import com.sparta.taskflow.domain.user.dto.UserSummaryDto;
import com.sparta.taskflow.domain.user.dto.response.UserResponseDto;
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
    private String status;
    private Long assigneeId;
    private UserSummaryDto assignee;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TaskResponseDto of(Task task) {
        return TaskResponseDto.builder()
                              .id(task.getId())
                              .title(task.getTitle())
                              .description(task.getDescription())
                              .priority(task.getPriority())
                              .status(task.getStatus())
                              .assigneeId(task.getAssignee().getId())
                              .assignee(UserSummaryDto.builder()
                                                      .id(task.getAssignee().getId())
                                                      .username(task.getAssignee().getUsername())
                                                      .name(task.getAssignee().getName())
                                                      .email(task.getAssignee().getEmail())
                                                      .build())
                              .dueDate(task.getDueDate())
                              .createdAt(task.getCreatedAt())
                              .updatedAt(task.getUpdatedAt())
                              .build();
    }
}
