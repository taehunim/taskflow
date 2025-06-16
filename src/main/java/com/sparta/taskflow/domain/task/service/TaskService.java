package com.sparta.taskflow.domain.task.service;

import com.sparta.taskflow.domain.task.dto.request.CreateTaskRequestDto;
import com.sparta.taskflow.domain.task.dto.response.CreateTaskResponseDto;
import com.sparta.taskflow.domain.task.dto.response.TaskListResponseDto;
import com.sparta.taskflow.domain.task.dto.response.TaskResponseDto;
import com.sparta.taskflow.domain.task.entity.Task;
import com.sparta.taskflow.domain.task.repository.TaskRepository;
import java.util.ArrayList;
import java.util.List;
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

    public CreateTaskResponseDto createTask(CreateTaskRequestDto requestDto) {

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

        // TODO : USER ENTITY 생성 후 수정
        return CreateTaskResponseDto.of(savedTask, "담당자");
    }

    public TaskListResponseDto getTasks(int page, int size, String sort, String status, String title, String description) {

        String[] sortParams = sort.split(",");
        Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);
        String sortBy = sortParams[0];
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        status = (status == null || status.isBlank()) ? "" : status;
        title = (title == null || title.isBlank()) ? "" : title;
        description = (description == null || description.isBlank()) ? "" : description;

        Page<Task> tasks = taskRepository.findByStatusContainingIgnoreCaseAndTitleContainingIgnoreCaseAndDescriptionContainingIgnoreCase(
            status, title, description, pageable
        );
        List<TaskResponseDto> taskDtos = new ArrayList<>();

        // TODO : USER ENTITY 생성 후 수정
        for (Task task : tasks) {
            String assigneeName = "담당자";
            //String assigneeName = userService.getUserNameById(task.getAssigneeId());
            TaskResponseDto responseDto = TaskResponseDto.of(task, assigneeName);
            taskDtos.add(responseDto);
        }

        return TaskListResponseDto.of(new PageImpl<>(taskDtos, pageable, tasks.getTotalElements()), sort);

    }

}
