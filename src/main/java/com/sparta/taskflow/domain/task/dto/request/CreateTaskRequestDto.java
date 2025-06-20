package com.sparta.taskflow.domain.task.dto.request;

import com.sparta.taskflow.domain.task.type.PriorityType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CreateTaskRequestDto {

    @NotNull(message = "제목은 필수입니다.")
    private String title;

    private String description;
    private LocalDateTime dueDate;

    @NotNull(message = "우선순위는 필수입니다.")
    private PriorityType priority;

    private Long assigneeId;
}
