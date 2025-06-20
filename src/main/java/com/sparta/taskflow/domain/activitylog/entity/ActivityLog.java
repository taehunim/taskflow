package com.sparta.taskflow.domain.activitylog.entity;

import com.sparta.taskflow.domain.activitylog.Enum.ActivityType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType activityType;

    @Column(nullable = true, updatable = false)
    private Long targetId;

    @Column(nullable = false, updatable = false)
    private String method;

    @Column(nullable = false, updatable = false)
    private String url;

    @Column(nullable = false, updatable = false)
    private String ipAddress;

    @Column(columnDefinition = "TEXT", nullable = true, updatable = false)
    private String content;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public ActivityLog(Long userId, ActivityType activityType, Long targetId,
                       String method, String url, String ipAddress,
                       String content) {
        this.userId = userId;
        this.activityType = activityType;
        this.targetId = targetId;
        this.method = method;
        this.url = url;
        this.ipAddress = ipAddress;
        this.content = content;
    }
}
