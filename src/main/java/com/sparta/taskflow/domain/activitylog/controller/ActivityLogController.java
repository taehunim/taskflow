package com.sparta.taskflow.domain.activitylog.controller;

import org.springframework.security.core.userdetails.User;


import com.sparta.taskflow.domain.activitylog.Enum.ActivityType;
import com.sparta.taskflow.domain.activitylog.dto.ActivityLogResponseDto;
import com.sparta.taskflow.domain.activitylog.service.ActivityLogService;
import com.sparta.taskflow.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) ActivityType activityType,
            @RequestParam(required = false) Long targetId,

            @RequestParam(name = "startAt", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startAT,

            @RequestParam(name = "endAt", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endAt,
            @RequestParam(defaultValue = "timestamp") String sortBy
    ) {

        Long userId = Long.valueOf(user.getUsername());

        // 필터 조건을 서비스 계층으로 전달 후 리스트로 반환받고
        List<ActivityLogResponseDto> logs = activityLogService.getLogsByFilter(
                userId, activityType, targetId, startAT, endAt, sortBy
        );

        // 공통 응답 형식으로 래핑하서 프론트에 반환
        return ApiResponse.success("활동로그 조회 성공", logs);
    }
}
