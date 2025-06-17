package com.sparta.taskflow.domain.task.dto.response;

import com.sparta.taskflow.domain.task.entity.Task;
import com.sparta.taskflow.domain.user.dto.UserSummaryDto;
import com.sparta.taskflow.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreateTaskResponseDto {

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

    public static CreateTaskResponseDto of(Task task) {
        User assignee = task.getAssignee();
        return CreateTaskResponseDto.builder()
                                    .id(task.getId())
                                    .title(task.getTitle())
                                    .description(task.getDescription())
                                    .priority(task.getPriority())
                                    .status(task.getStatus())
                                    .assigneeId(assignee.getId())
                                    .assignee(UserSummaryDto.builder()
                                                            .id(assignee.getId())
                                                            .username(assignee.getUsername())
                                                            .name(assignee.getName())
                                                            .email(assignee.getEmail())
                                                            .build())
                                    .dueDate(task.getDueDate())
                                    .createdAt(task.getCreatedAt())
                                    .updatedAt(task.getUpdatedAt())
                                    .build();
    }
}
