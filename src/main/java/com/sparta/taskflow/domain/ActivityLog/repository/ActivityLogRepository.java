package com.sparta.taskflow.domain.ActivityLog.repository;

import com.sparta.taskflow.domain.ActivityLog.Enum.ActivityType;
import com.sparta.taskflow.domain.ActivityLog.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    List<ActivityLog> findAllByUserId(Long userId);

    List<ActivityLog> findAllByUserIdAndActivityType(Long userId, ActivityType activityType);

    List<ActivityLog> findAllByUserIdAndTaskId(Long userId, Long taskId);

    List<ActivityLog> findAllBByUserIdAndTimestamp(Long UserId, LocalDateTime startAt, LocalDateTime endAt);
}
