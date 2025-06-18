package com.sparta.taskflow.domain.task.service;

import com.sparta.taskflow.domain.task.dto.request.CreateTaskRequestDto;
import com.sparta.taskflow.domain.task.dto.response.CreateTaskResponseDto;
import com.sparta.taskflow.domain.task.dto.response.TaskListResponseDto;
import com.sparta.taskflow.domain.task.dto.response.TaskResponseDto;
import com.sparta.taskflow.domain.task.entity.Task;
import com.sparta.taskflow.domain.task.repository.TaskRepository;
import com.sparta.taskflow.domain.user.dto.UserSummaryDto;
import com.sparta.taskflow.domain.user.dto.response.UserResponseDto;
import com.sparta.taskflow.domain.user.entity.User;
import com.sparta.taskflow.domain.user.repository.UserRepository;
import com.sparta.taskflow.global.exception.CustomException;
import com.sparta.taskflow.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateTaskResponseDto createTask(CreateTaskRequestDto requestDto) {

        User assignee = userRepository.findById(requestDto.getAssigneeId())
                                      .filter(user -> !user.isDeleted())
                                      .orElseThrow(() ->  new CustomException(ErrorCode.ASSIGNEE_NOT_FOUND));

        Task task = Task.builder()
                        .title(requestDto.getTitle())
                        .description(requestDto.getDescription())
                        .priority(requestDto.getPriority())
                        .status("TODO")
                        .assignee(assignee)
                        .dueDate(requestDto.getDueDate())
                        .isDeleted(false)
                        .build();

        Task savedTask = taskRepository.save(task);

        return CreateTaskResponseDto.of(savedTask);

    }

    public TaskListResponseDto getTasks(Pageable pageable, String status, String search, Long assigneeId) {

        Page<Task> taskPage = taskRepository.findAllByFilters(
            status, search, assigneeId, pageable
        );

        List<TaskResponseDto> taskResponseDtos = taskPage.stream()
                                                         .map(TaskResponseDto::of)
                                                         .collect(Collectors.toList());

        return TaskListResponseDto.of(new PageImpl<>(taskResponseDtos, pageable, taskPage.getTotalElements()));

    }

    public TaskResponseDto getTask(Long taskId) {

        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                                  .orElseThrow(() -> new CustomException(ErrorCode.TASK_NOT_FOUND));

        return TaskResponseDto.of(task);

    }

}
