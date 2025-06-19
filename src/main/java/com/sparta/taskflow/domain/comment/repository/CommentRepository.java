package com.sparta.taskflow.domain.comment.repository;

import com.sparta.taskflow.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByTaskIdAndIsDeletedFalseOrderByCreatedAtDesc(Long taskId,
        Pageable pageable);


    Page<Comment> findByContentContainingIgnoreCaseAndIsDeletedFalseOrderByCreatedAtDesc(
        String keyword, Pageable pageable);
}