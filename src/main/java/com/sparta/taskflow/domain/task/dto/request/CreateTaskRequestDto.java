package com.sparta.taskflow.domain.task.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateTaskRequestDto {

    @NotNull(message = "제목은 필수입니다.")
    private String title;

    private String description;

    @NotNull(message = "마감일은 필수입니다.")
    private LocalDateTime dueDate;

    @NotNull(message = "우선순위는 필수입니다.")
    private String priority;

    @NotNull(message = "담당자는 필수입니다.")
    private Long assigneeId;
}
