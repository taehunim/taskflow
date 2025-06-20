package com.sparta.taskflow.domain.task.type;

import lombok.Getter;

@Getter
public enum StatusType {

    TODO, IN_PROGRESS, DONE;

    public boolean canChangeToStatus(StatusType target) {
        switch (this) {
            case TODO:
                return target == IN_PROGRESS || target == DONE;
            case IN_PROGRESS:
                return target == DONE;
            case DONE:
                return false;
            default:
                return false;
        }
    }

}
