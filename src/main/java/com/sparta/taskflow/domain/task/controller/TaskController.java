package com.sparta.taskflow.domain.task.controller;

import com.sparta.taskflow.domain.task.dto.request.CreateTaskRequestDto;
import com.sparta.taskflow.domain.task.dto.request.UpdateTaskRequestDto;
import com.sparta.taskflow.domain.task.dto.request.UpdateTaskStatusRequestDto;
import com.sparta.taskflow.domain.task.dto.response.CreateTaskResponseDto;
import com.sparta.taskflow.domain.task.dto.response.TaskListResponseDto;
import com.sparta.taskflow.domain.task.dto.response.TaskResponseDto;
import com.sparta.taskflow.domain.task.service.TaskService;
import com.sparta.taskflow.domain.task.type.StatusType;
import com.sparta.taskflow.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
                "Task가 생성되었습니다.",
                responseDto
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<TaskListResponseDto>> getTasks(
        @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable,
        @RequestParam(required = false) StatusType status,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) Long assigneeId
    ) {

        TaskListResponseDto taskList = taskService.getTasks(pageable, status, search, assigneeId);
        ApiResponse<TaskListResponseDto> response = ApiResponse.success(
            "Task 목록을 조회했습니다.",
            taskList
        );

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskResponseDto>> getTask(
        @PathVariable Long taskId
    ) {

        TaskResponseDto responseDto = taskService.getTask(taskId);
        ApiResponse<TaskResponseDto> response = ApiResponse.success(
            "Task를 조회했습니다.",
            responseDto
        );

        return ResponseEntity.ok(response);

    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskResponseDto>> updateTask(
        @PathVariable Long taskId,
        @Valid @RequestBody UpdateTaskRequestDto requestDto
    ) {

        TaskResponseDto responseDto = taskService.updateTask(taskId, requestDto);
        ApiResponse<TaskResponseDto> response = ApiResponse.success(
            "Task가 수정되었습니다.",
            responseDto
        );

        return ResponseEntity.ok(response);

    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<ApiResponse<TaskResponseDto>> updateTaskStatus(
        @PathVariable Long taskId,
        @Valid @RequestBody UpdateTaskStatusRequestDto requestDto
    ) {
        TaskResponseDto responseDto = taskService.updateStatus(taskId, requestDto.getStatus());

        ApiResponse<TaskResponseDto> response = ApiResponse.success(
            "작업 상태가 업데이트되었습니다.",
            responseDto
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(
        @PathVariable Long taskId
    ) {

        taskService.deleteTask(taskId);
        ApiResponse<Void> response = ApiResponse.success(
            "Task가 삭제되었습니다.",
            null
        );

        return ResponseEntity.ok(response);
    }

}
