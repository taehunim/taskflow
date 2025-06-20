package com.sparta.taskflow.domain.task.service;

import com.sparta.taskflow.domain.task.dto.request.CreateTaskRequestDto;
import com.sparta.taskflow.domain.task.dto.request.UpdateTaskRequestDto;
import com.sparta.taskflow.domain.task.dto.response.CreateTaskResponseDto;
import com.sparta.taskflow.domain.task.dto.response.TaskListResponseDto;
import com.sparta.taskflow.domain.task.dto.response.TaskResponseDto;
import com.sparta.taskflow.domain.task.dto.response.TaskStatisticsResponseDto;
import com.sparta.taskflow.domain.task.entity.Task;
import com.sparta.taskflow.domain.task.repository.TaskRepository;
import com.sparta.taskflow.domain.task.type.StatusType;
import com.sparta.taskflow.domain.user.entity.User;
import com.sparta.taskflow.domain.user.repository.UserRepository;
import com.sparta.taskflow.global.exception.CustomException;
import com.sparta.taskflow.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateTaskResponseDto createTask(CreateTaskRequestDto requestDto) {

        User assignee = null;

        if (requestDto.getAssigneeId() != null) {
            assignee = userRepository.findById(requestDto.getAssigneeId())
                                     .filter(user -> !user.isDeleted())
                                     .orElseThrow(
                                         () -> new CustomException(ErrorCode.ASSIGNEE_NOT_FOUND));
        }

        Task task = Task.builder()
                        .title(requestDto.getTitle())
                        .description(requestDto.getDescription())
                        .priority(requestDto.getPriority())
                        .status(StatusType.TODO)
                        .assignee(assignee)
                        .dueDate(requestDto.getDueDate())
                        .isDeleted(false)
                        .build();

        Task savedTask = taskRepository.save(task);

        return CreateTaskResponseDto.of(savedTask);

    }

    public TaskListResponseDto getTasks(Pageable pageable, StatusType status, String search,
        Long assigneeId) {

        Page<Task> taskPage = taskRepository.findAllByFilters(
            status, search, assigneeId, pageable
        );

        List<TaskResponseDto> taskResponseDtos = taskPage.stream()
                                                         .map(TaskResponseDto::of)
                                                         .collect(Collectors.toList());

        return TaskListResponseDto.of(
            new PageImpl<>(taskResponseDtos, pageable, taskPage.getTotalElements()));

    }

    public TaskResponseDto getTask(Long taskId) {

        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                                  .orElseThrow(() -> new CustomException(ErrorCode.TASK_NOT_FOUND));

        return TaskResponseDto.of(task);

    }

    @Transactional
    public TaskResponseDto updateTask(Long taskId, UpdateTaskRequestDto requestDto) {

        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                                  .orElseThrow(() -> new CustomException(ErrorCode.TASK_NOT_FOUND));

        User assignee = userRepository.findByIdAndIsDeletedFalse(requestDto.getAssigneeId())
                                      .orElseThrow(
                                          () -> new CustomException(ErrorCode.ASSIGNEE_NOT_FOUND));

        task.update(
            requestDto.getTitle(),
            requestDto.getDescription(),
            requestDto.getDueDate(),
            requestDto.getPriority(),
            requestDto.getStatus(),
            assignee
        );

        return TaskResponseDto.of(task);

    }

    @Transactional
    public TaskResponseDto updateStatus(Long taskId, StatusType newStatus) {

        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                                  .orElseThrow(() -> new CustomException(ErrorCode.TASK_NOT_FOUND));

        if (task.getStatus() != null && !task.getStatus().canChangeToStatus(newStatus)) {
            throw new CustomException(ErrorCode.INVALID_STATUS_TRANSITION);
        }

        task.updateStatus(newStatus);

        return TaskResponseDto.of(task);

    }

    public void deleteTask(Long taskId) {

        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId)
                                  .orElseThrow(() -> new CustomException(ErrorCode.TASK_NOT_FOUND));

        task.softDelete();

        taskRepository.save(task);

    }

    @Transactional
    public TaskStatisticsResponseDto getTaskStatistics() {

        List<Task> tasks = taskRepository.findAllByIsDeletedFalse();

        long totalCount = tasks.size();

        Map<StatusType, Long> statusCounts = tasks.stream()
                                                  .filter(task -> task.getStatus() != null)
                                                  .collect(Collectors.groupingBy(Task::getStatus,
                                                      Collectors.counting()));

        long doneCount = statusCounts.getOrDefault(StatusType.DONE, 0L);

        double completionRate =
            totalCount == 0 ? 0.0 : Math.round((doneCount * 10000.0 / totalCount)) / 100.0;

        LocalDateTime now = LocalDateTime.now();
        long overdueCount = tasks.stream()
                                 .filter(task -> task.getDueDate() != null)
                                 .filter(task -> (task.getStatus() == StatusType.TODO
                                     || task.getStatus() == StatusType.IN_PROGRESS))
                                 .filter(task -> task.getDueDate().isBefore(now))
                                 .count();

        return TaskStatisticsResponseDto.of(totalCount, statusCounts, completionRate, overdueCount);

    }

    @Transactional
    public TaskListResponseDto getTodayTasks(Pageable pageable) {

        Page<Task> taskPage = taskRepository.findTodayTasks(pageable);

        List<TaskResponseDto> taskResponseDtos = taskPage.stream()
                                                         .map(TaskResponseDto::of)
                                                         .collect(Collectors.toList());

        return TaskListResponseDto.of(
            new PageImpl<>(taskResponseDtos, pageable, taskPage.getTotalElements()));

    }

}
