package com.sparta.taskflow.domain.activitylog.dto;

import com.sparta.taskflow.domain.activitylog.Enum.ActivityType;
import com.sparta.taskflow.domain.activitylog.entity.ActivityLog;

import java.time.LocalDateTime;

public class ActivityLogResponseDto {

    private Long id;
    private Long userId;
    private ActivityType activityType;
    private Long targetId;
    private String method;
    private String url;
    private String ipAddress;
    private String content;
    private LocalDateTime timestamp;

    public ActivityLogResponseDto(ActivityLog log) {
        this.userId = log.getUserId();
        this.activityType = log.getActivityType();
        this.targetId = log.getTargetId();
        this.method = log.getMethod();
        this.url = log.getUrl();
        this.ipAddress = log.getIpAddress();
        this.content = log.getContent();
        this.timestamp = log.getTimestamp();
    }
}
