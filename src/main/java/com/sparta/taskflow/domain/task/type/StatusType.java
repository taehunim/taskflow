package com.sparta.taskflow.domain.task.type;

import com.sparta.taskflow.global.exception.CustomException;
import com.sparta.taskflow.global.exception.ErrorCode;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum StatusType {

    TODO, IN_PROGRESS, DONE;

}
