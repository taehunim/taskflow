package com.sparta.taskflow.domain.ActivityLog.controller;

import com.sparta.taskflow.domain.ActivityLog.Enum.ActivityType;
import com.sparta.taskflow.domain.ActivityLog.dto.ActivityLogResponseDto;
import com.sparta.taskflow.domain.ActivityLog.entity.ActivityLog;
import com.sparta.taskflow.domain.ActivityLog.service.ActivityLogService;
import com.sparta.taskflow.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logs")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    @GetMapping
    public ApiResponse<List<ActivityLogResponseDto>> getLogs(
     @RequestParam Long userId, // 지금은 직접 전달
     @RequestParam(required = false) ActivityType activityType,
     @RequestParam(required = false) Long targetId,
     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startAT,
     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate endAt,
     @RequestParam(defaultValue = "timestamp") String sortBy
    ) {
        List<ActivityLogResponseDto> logs = activityLogService.getLogsByFillter(userId, activityType, targetId, startAT, endAt, sortBy);
        return ApiResponse.success("활동로그 조회 성공", logs);
    }
}
