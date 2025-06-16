package com.sparta.taskflow.domain.comment.service;

import com.sparta.taskflow.domain.comment.dto.CommentRequestDto;
import com.sparta.taskflow.domain.comment.dto.CommentResponseDto;
import com.sparta.taskflow.domain.comment.entity.Comment;
import com.sparta.taskflow.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        Comment comment = Comment.builder()
                                 .content(requestDto.getContent())
                                 .taskId(requestDto.getTaskId())
                                 .userId(requestDto.getUserId())
                                 .build();

        Comment saved = commentRepository.save(comment);
        return CommentResponseDto.of(saved);
    }

}