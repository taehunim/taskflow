package com.sparta.taskflow.domain.task.dto.response;

import com.sparta.taskflow.domain.task.entity.Task;
import java.time.LocalDateTime;
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
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private String sort;

    public static TaskListResponseDto of(Page<TaskResponseDto> pageData, String sort) {
        return TaskListResponseDto.builder()
                                  .content(pageData.getContent())
                                  .page(pageData.getNumber())
                                  .size(pageData.getSize())
                                  .totalElements(pageData.getTotalElements())
                                  .totalPages(pageData.getTotalPages())
                                  .sort(sort)
                                  .build();
    }

}
