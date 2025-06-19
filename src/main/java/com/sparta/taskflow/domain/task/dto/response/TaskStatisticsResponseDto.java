package com.sparta.taskflow.domain.task.dto.response;

import com.sparta.taskflow.domain.task.type.StatusType;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskStatisticsResponseDto {

    private long totalCount;
    private Map<StatusType, Long> statusCounts;
    private double completionRate;
    private long overdueCount;

    public static TaskStatisticsResponseDto of(
        long totalCount,
        Map<StatusType, Long> statusCounts,
        double completionRate,
        long overdueCount
    ) {
        return TaskStatisticsResponseDto.builder()
                                        .totalCount(totalCount)
                                        .statusCounts(statusCounts)
                                        .completionRate(completionRate)
                                        .overdueCount(overdueCount)
                                        .build();
    }
}