package com.sparta.taskflow.domain.comment.service;

import com.sparta.taskflow.domain.comment.dto.CreateCommentRequestDto;
import com.sparta.taskflow.domain.comment.dto.CreateCommentResponseDto;
import com.sparta.taskflow.domain.comment.entity.Comment;
import com.sparta.taskflow.domain.comment.repository.CommentRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CreateCommentResponseDto createComment(CreateCommentRequestDto requestDto) {
        Comment comment = Comment.builder()
                                 .content(requestDto.getContent())
                                 .taskId(requestDto.getTaskId())
                                 .userId(requestDto.getUserId())
                                 .build();

        Comment saved = commentRepository.save(comment);
        return CreateCommentResponseDto.of(saved);
    }

    public List<CreateCommentResponseDto> getCommentsByTask(Long taskId) {
        List<Comment> comments = commentRepository.findAllByTaskIdAndIsDeletedFalseOrderByCreatedAtDesc(
            taskId);
        return comments.stream()
                       .map(CreateCommentResponseDto::of)
                       .collect(Collectors.toList());
    }
}