package com.sparta.taskflow.domain.ActivityLog.service;

import com.sparta.taskflow.domain.ActivityLog.Enum.ActivityType;
import com.sparta.taskflow.domain.ActivityLog.dto.ActivityLogResponseDto;
import com.sparta.taskflow.domain.ActivityLog.entity.ActivityLog;
import com.sparta.taskflow.domain.ActivityLog.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityLogService {

    public final ActivityLogRepository activityLogRepository;

    public List<ActivityLogResponseDto> getLogsByFillter(
            Long userId,
            ActivityType activityType,
            Long targetId,
            LocalDate startAt,
            LocalDate endAt,
            String sort
    ) {

        // 해당 유저의 모든 로그 조회
        List<ActivityLog> activityLogs = activityLogRepository.findAllByUserId(userId);

        // activityType 필터
        if (activityType != null) {
            activityLogs = activityLogs.stream()
                    .filter(activityLog -> activityLog.getActivityType() == activityType)
                    .collect(Collectors.toList());
        }

        // targerId 필터
        if (targetId != null) {
            activityLogs = activityLogs.stream()
                    .filter(activityLog -> activityLog.getTargetId().equals(targetId))
                    .collect(Collectors.toList());
        }

        // 기간 필터
        if (startAt != null && endAt != null) {
            LocalDateTime start = startAt.atStartOfDay();
            LocalDateTime end = endAt.atTime(LocalTime.MAX);
            activityLogs = activityLogs.stream()
                    .filter(activityLog -> !activityLog.getTimestamp().isBefore(start)
                            && !activityLog.getTimestamp().isAfter(end))
                    .collect(Collectors.toList());
        }

        // 정렬
        if ("activity".equals(sort)) {
            activityLogs.sort((logA, logB) -> logA.getActivityType().name().compareTo(logB.getActivityType().name()));
        } else { // 시간순
            activityLogs.sort((logA, logB) -> logB.getTimestamp().compareTo(logA.getTimestamp()));
        }

        return activityLogs.stream().
                map(ActivityLogResponseDto::new)
                .collect(Collectors.toList());
    }

}

