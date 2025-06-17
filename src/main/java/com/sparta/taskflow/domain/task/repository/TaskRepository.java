package com.sparta.taskflow.domain.task.repository;

import com.sparta.taskflow.domain.task.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByStatusContainingIgnoreCaseAndTitleContainingIgnoreCaseAndDescriptionContainingIgnoreCase(
        String status,
        String title,
        String description,
        Pageable pageable
    );

}
