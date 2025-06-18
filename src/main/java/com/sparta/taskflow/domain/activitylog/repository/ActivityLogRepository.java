package com.sparta.taskflow.domain.activitylog.repository;

import com.sparta.taskflow.domain.activitylog.Enum.ActivityType;
import com.sparta.taskflow.domain.activitylog.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    List<ActivityLog> findAllByUserId(Long userId);

    List<ActivityLog> findAllByUserIdAndActivityType(Long userId, ActivityType activityType);

    List<ActivityLog> findAllByUserIdAndTargetId(Long userId, Long targetId);

    List<ActivityLog> findAllByUserIdAndTimestampBetween(
            Long userId,
            LocalDateTime startAt,
            LocalDateTime endAt);
}
