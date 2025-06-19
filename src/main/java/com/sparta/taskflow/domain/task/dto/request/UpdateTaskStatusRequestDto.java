package com.sparta.taskflow.domain.task.dto.request;

import com.sparta.taskflow.domain.task.type.StatusType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateTaskStatusRequestDto {

    @NotNull(message = "상태는 필수입니다.")
    private StatusType status;

}
