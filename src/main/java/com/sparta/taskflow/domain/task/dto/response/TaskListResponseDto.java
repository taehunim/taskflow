package com.sparta.taskflow.domain.task.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskListResponseDto {

    private List<TaskResponseDto> content;
    private long totalElements;
    private int totalPages;
    private int size;
    private int number;

    public static TaskListResponseDto of(Page<TaskResponseDto> pageData) {
        return TaskListResponseDto.builder()
                                  .content(pageData.getContent())
                                  .totalElements(pageData.getTotalElements())
                                  .totalPages(pageData.getTotalPages())
                                  .size(pageData.getSize())
                                  .number(pageData.getNumber())
                                  .build();
    }
}
