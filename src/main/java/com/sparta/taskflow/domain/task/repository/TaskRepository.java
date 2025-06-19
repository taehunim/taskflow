package com.sparta.taskflow.domain.task.repository;

import com.sparta.taskflow.domain.task.entity.Task;
import com.sparta.taskflow.domain.task.type.StatusType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("""
    SELECT t 
    FROM Task t
    WHERE t.isDeleted = false
    AND (:status IS NULL OR t.status = :status)
    AND (:assigneeId IS NULL OR t.assignee.id = :assigneeId)
    AND (:search IS NULL OR 
         LOWER(t.title) LIKE LOWER(CONCAT('%', :search, '%')) OR
         LOWER(t.description) LIKE LOWER(CONCAT('%', :search, '%')))
    """)
    Page<Task> findAllByFilters(
        @Param("status") StatusType status,
        @Param("search") String search,
        @Param("assigneeId") Long assigneeId,
        Pageable pageable
    );

    @Query("""
    SELECT t
    FROM Task t
    WHERE t.isDeleted = false
    AND (t.status = 'TODO' OR t.status = 'IN_PROGRESS')
    """)
    Page<Task> findTodayTasks(Pageable pageable);

    Optional<Task> findByIdAndIsDeletedFalse(Long id);

    List<Task> findAllByIsDeletedFalse();

}
