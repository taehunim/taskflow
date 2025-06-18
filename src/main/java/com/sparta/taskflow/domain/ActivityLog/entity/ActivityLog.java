package com.sparta.taskflow.domain.ActivityLog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId; // 활동한 사용자 Id

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType activityType; // 활동 유형

    @Column(nullable = false)
    private Long targetId; // 작업, 댓글, 유저 아이디

    @Column(nullable = false)
    private String method;

    @Column(nullable = false)
    private String url; // 요청 URL

    @Column(nullable = false)
    private String ipAddress; // Ip 주소

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public ActivityLog(Long userId, ActivityType activityType, Long targetId,
                       String method, String url, String ipAddress,
                       String content, LocalDateTime timestamp) {
        this.userId = userId;
        this.activityType = activityType;
        this.targetId = targetId;
        this.method = method;
        this.url = url;
        this.ipAddress = ipAddress;
        this.content = content;
        this.timestamp = timestamp;
    }
}
