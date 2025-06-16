package com.sparta.taskflow.domain.task.service;

import com.sparta.taskflow.domain.task.dto.request.CreateTaskRequestDto;
import com.sparta.taskflow.domain.task.dto.response.CreateTaskResponseDto;
import com.sparta.taskflow.domain.task.entity.Task;
import com.sparta.taskflow.domain.task.repository.TaskRepository;
import com.sparta.taskflow.global.exception.CustomException;
import com.sparta.taskflow.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public CreateTaskResponseDto createTask(CreateTaskRequestDto requestDto) {

        if (requestDto.getTitle() == null || requestDto.getTitle().isBlank()) {
            throw new CustomException(ErrorCode.VALIDATION_ERROR);
        }

        Task task = Task.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .priority(requestDto.getPriority())
                .status(requestDto.getStatus())
                .assigneeId(requestDto.getAssigneeId())
                .dueAt(requestDto.getDueAt())
                .isDeleted(false)
                .build();

        Task savedTask = taskRepository.save(task);

        return CreateTaskResponseDto.of(savedTask, "담당자");
    }
}
