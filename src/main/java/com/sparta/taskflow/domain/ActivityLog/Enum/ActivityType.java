package com.sparta.taskflow.domain.ActivityLog.Enum;

public enum ActivityType {

    TASK_CREATED(false),
    TASK_UPDATED(true),
    TASK_DELETED(true),
    TASK_STATUS_CHANGED(true),

    COMMENT_CREATED(false),
    COMMENT_UPDATED(true),
    COMMENT_DELETED(true),

    USER_LOGGED_IN(false),
    USER_LOGGED_OUT(false);

    private final boolean haveTargetId;

    ActivityType(boolean havaTargetId) {
        this.haveTargetId = havaTargetId;
    }

    public boolean getHaveTargetId() {
        return haveTargetId;
    }
}
