package com.sparta.taskflow.domain.task.dto.response;

import com.sparta.taskflow.domain.task.entity.Task;
import com.sparta.taskflow.domain.task.type.PriorityType;
import com.sparta.taskflow.domain.task.type.StatusType;
import com.sparta.taskflow.domain.user.dto.UserSummaryDto;
import com.sparta.taskflow.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateTaskResponseDto {

    private Long id;
    private String title;
    private String description;
    private PriorityType priority;
    private StatusType status;
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
                                    .assigneeId(assignee != null ? assignee.getId() : null)
                                    .assignee(assignee != null ? UserSummaryDto.of(assignee) : new UserSummaryDto())
                                    .dueDate(task.getDueDate())
                                    .createdAt(task.getCreatedAt())
                                    .updatedAt(task.getUpdatedAt())
                                    .build();
    }
}
