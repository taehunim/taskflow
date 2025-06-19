package com.sparta.taskflow.domain.activitylog.Enum;

public enum ActivityType {

    TASK_CREATED(true),
    TASK_UPDATED(true),
    TASK_DELETED(true),
    TASK_STATUS_CHANGED(true),

    COMMENT_CREATED(true),
    COMMENT_UPDATED(true),
    COMMENT_DELETED(true),

    USER_LOGGED_IN(false),
    USER_LOGGED_OUT(false);

    private final boolean requiredTargetId;

    ActivityType(boolean requiredTargetId) {
        this.requiredTargetId = requiredTargetId;
    }

    public boolean getRequiredTargetId() {
        return requiredTargetId;
    }
}
