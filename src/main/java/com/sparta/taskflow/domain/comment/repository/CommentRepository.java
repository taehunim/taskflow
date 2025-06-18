package com.sparta.taskflow.domain.comment.repository;

import com.sparta.taskflow.domain.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByTaskIdAndIsDeletedFalseOrderByCreatedAtDesc(Long taskId); // 조회

    List<Comment> findAllByContentContainingIgnoreCaseAndIsDeletedFalseOrderByCreatedAtDesc(String keyword); // 검색
}