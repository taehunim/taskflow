package com.sparta.taskflow.domain.activitylog.service;

import com.sparta.taskflow.domain.activitylog.Enum.ActivityType;
import com.sparta.taskflow.domain.activitylog.dto.ActivityLogResponseDto;
import com.sparta.taskflow.domain.activitylog.entity.ActivityLog;
import com.sparta.taskflow.domain.activitylog.repository.ActivityLogRepository;
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

    private final ActivityLogRepository activityLogRepository;

    public List<ActivityLogResponseDto> getLogsByFilter(
            Long userId,
            ActivityType activityType,
            Long targetId,
            LocalDate startAt,
            LocalDate endAt,
            String sort
    ) {

        // 1. 해당 유저의 모든 로그 조회
        List<ActivityLog> activityLogs = activityLogRepository.findAllByUserId(userId);

        // 2. activityType 필터
        if (activityType != null) {
            activityLogs = activityLogs.stream()
                    .filter(activityLog -> activityLog.getActivityType() == activityType)
                    .collect(Collectors.toList());
        }

        // 3. targerId 필터
        if (targetId != null) {
            activityLogs = activityLogs.stream()
                    .filter(activityLog -> activityLog.getTargetId().equals(targetId))
                    .collect(Collectors.toList());
        }

        // 4. 기간 필터
        if (startAt != null && endAt != null) {
            LocalDateTime start = startAt.atStartOfDay(); // 00:00:00 시 부터
            LocalDateTime end = endAt.atTime(LocalTime.MAX); // 23:59:59 시 까지
            activityLogs = activityLogs.stream()
                    .filter(activityLog -> !activityLog.getCreatedAt().isBefore(start)
                            && !activityLog.getCreatedAt().isAfter(end))
                    .collect(Collectors.toList());
        }

        // 5. 정렬
        if ("activity".equals(sort)) {
            // 알파벳 순으로 정렬
            activityLogs.sort((logA, logB)
                    -> logA.getActivityType().name().compareTo(logB.getActivityType().name()));
        } else { // 시간순 정렬, 기본 정렬
            activityLogs.sort((logA, logB) -> logB.getCreatedAt().compareTo(logA.getCreatedAt()));
        }
        // 6. 해당 값들을 응답 DTO로 변환하여 반환
        return activityLogs.stream().
                map(ActivityLogResponseDto::new)
                .collect(Collectors.toList());
    }

}

