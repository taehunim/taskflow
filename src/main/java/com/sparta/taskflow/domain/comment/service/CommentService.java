package com.sparta.taskflow.domain.comment.service;

import com.sparta.taskflow.domain.comment.dto.request.CreateCommentRequestDto;
import com.sparta.taskflow.domain.comment.dto.response.CommentResponseDto;
import com.sparta.taskflow.domain.comment.dto.response.CreateCommentResponseDto;
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

    // 댓글 생성 메서드
    public CreateCommentResponseDto createComment(CreateCommentRequestDto requestDto) {
        Comment comment = Comment.builder()
                                 .content(requestDto.getContent())
                                 .taskId(requestDto.getTaskId())
                                 .userId(requestDto.getUserId())
                                 .build();

        Comment saved = commentRepository.save(comment);
        return CreateCommentResponseDto.of(saved);
    }

    // 댓글 목록 조회 메서드 (페이지네이션, user 정보 포함)
    public Page<CommentResponseDto> getCommentsByTask(Long taskId, Pageable pageable) {
        Page<Comment> comments = commentRepository
            .findAllByTaskIdAndIsDeletedFalseOrderByCreatedAtDesc(taskId, pageable);

        return comments.map(comment -> {
            User user = userRepository.findById(comment.getUserId())
                                      .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
            UserResponseDto userDto = UserResponseDto.of(user);
            return CommentResponseDto.of(comment, userDto);
        });
    }
}