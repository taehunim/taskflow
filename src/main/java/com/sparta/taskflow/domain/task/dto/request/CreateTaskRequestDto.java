package com.sparta.taskflow.domain.task.dto.request;

import com.sparta.taskflow.domain.task.type.PriorityType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CreateTaskRequestDto {

    @NotNull(message = "제목은 필수입니다.")
    @Size(min = 1, max = 100, message = "제목은 최소 1자, 최대 100자입니다.")
    private String title;

    @Size(max = 1000, message = "설명은 최대 1000자입니다.")
    private String description;

    @Future(message = "마감시각은 현재보다 이후여야합니다.")
    private LocalDateTime dueDate;

    @NotNull(message = "우선순위는 필수입니다.")
    private PriorityType priority;

    private Long assigneeId;
}
