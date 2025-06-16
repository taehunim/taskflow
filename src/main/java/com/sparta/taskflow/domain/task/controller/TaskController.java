package com.sparta.taskflow.domain.task.controller;

import com.sparta.taskflow.domain.task.dto.request.CreateTaskRequestDto;
import com.sparta.taskflow.domain.task.dto.response.CreateTaskResponseDto;
import com.sparta.taskflow.domain.task.dto.response.TaskListResponseDto;
import com.sparta.taskflow.domain.task.dto.response.TaskResponseDto;
import com.sparta.taskflow.domain.task.service.TaskService;
import com.sparta.taskflow.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateTaskResponseDto>> createTask(
            @Valid @RequestBody CreateTaskRequestDto requestDto
    ) {

        CreateTaskResponseDto responseDto = taskService.createTask(requestDto);
        ApiResponse<CreateTaskResponseDto> response = ApiResponse.success(
                "테스크가 생성되었습니다.",
                responseDto
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<TaskListResponseDto>> getTasks(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "createdAt,asc") String sort,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String description
    ) {

        TaskListResponseDto taskList = taskService.getTasks(page, size, sort, status, title, description);
        ApiResponse<TaskListResponseDto> response = ApiResponse.success("테스크 목록을 조회했습니다.", taskList);

        return ResponseEntity.ok(response);

    }

}
