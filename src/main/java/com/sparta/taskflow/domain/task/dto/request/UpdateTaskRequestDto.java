package com.sparta.taskflow.domain.task.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UpdateTaskRequestDto {

    @NotNull(message = "제목은 필수입니다.")
    private String title;

    private String description;

    private LocalDateTime dueDate;

    @NotNull(message = "우선순위는 필수입니다.")
    private String priority;

    private String status;

    private Long assigneeId;

}
