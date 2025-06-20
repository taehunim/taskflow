package com.sparta.taskflow.domain.activitylog.dto;

import com.sparta.taskflow.domain.activitylog.entity.ActivityLog;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ActivityLogResponseDto {

    private final Long id;
    private final Long userId;
    private final String activityType;
    private final Long targetId;
    private final String method;
    private final String url;
    private final String ipAddress;
    private final String content;
    private final LocalDateTime createdAt;

    public ActivityLogResponseDto(ActivityLog log) {
        this.id = log.getId();
        this.userId = log.getUserId();
        this.activityType = log.getActivityType().toString();
        this.targetId = log.getTargetId();
        this.method = log.getMethod();
        this.url = log.getUrl();
        this.ipAddress = log.getIpAddress();
        this.content = log.getContent();
        this.createdAt = log.getCreatedAt();
    }
}
