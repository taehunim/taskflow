package com.sparta.taskflow.domain.comment.service;

import com.sparta.taskflow.domain.comment.dto.CommentRequestDto;
import com.sparta.taskflow.domain.comment.dto.CommentResponseDto;
import com.sparta.taskflow.domain.comment.entity.Comment;
import com.sparta.taskflow.domain.comment.repository.CommentRepository;
import com.sparta.taskflow.domain.user.dto.response.UserResponseDto;
import com.sparta.taskflow.domain.user.entity.User;
import com.sparta.taskflow.domain.user.repository.UserRepository;
import com.sparta.taskflow.global.exception.CustomException;
import com.sparta.taskflow.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // 댓글 생성
    public CommentResponseDto createComment(CommentRequestDto requestDto, Long taskId) {
        Comment comment = Comment.builder()
                                 .content(requestDto.getContent())
                                 .userId(requestDto.getUserId())
                                 .taskId(taskId)
                                 .build();

        Comment saved = commentRepository.save(comment);
        User user = userRepository.findById(saved.getUserId())
                                  .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return CommentResponseDto.of(saved, UserResponseDto.of(user));
    }

    // 댓글 조회
    public Page<CommentResponseDto> getCommentsByTask(Long taskId, Pageable pageable) {
        Page<Comment> comments = commentRepository
            .findAllByTaskIdAndIsDeletedFalseOrderByCreatedAtDesc(taskId, pageable);

        return comments.map(comment -> {
            User user = userRepository.findById(comment.getUserId())
                                      .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
            return CommentResponseDto.of(comment, UserResponseDto.of(user));
        });
    }
}