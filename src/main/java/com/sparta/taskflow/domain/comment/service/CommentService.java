package com.sparta.taskflow.domain.comment.service;

import com.sparta.taskflow.domain.comment.dto.CommentRequestDto;
import com.sparta.taskflow.domain.comment.dto.CommentResponseDto;
import com.sparta.taskflow.domain.comment.entity.Comment;
import com.sparta.taskflow.domain.comment.repository.CommentRepository;
import com.sparta.taskflow.domain.user.dto.UserSummaryDto;
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
    public CommentResponseDto createComment(CommentRequestDto requestDto, Long taskId, Long loginUserId) {
        Comment comment = Comment.builder()
                                 .content(requestDto.getContent())
                                 .userId(loginUserId)
                                 .taskId(taskId)
                                 .build();

        Comment saved = commentRepository.save(comment);

        User user = userRepository.findById(saved.getUserId())
                                  .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        UserSummaryDto userSummary = UserSummaryDto.of(user);

        return CommentResponseDto.of(saved, userSummary);
    }

    // 댓글 조회
    public Page<CommentResponseDto> getCommentsByTask(Long taskId, Pageable pageable) {
        Page<Comment> comments = commentRepository
            .findAllByTaskIdAndIsDeletedFalseOrderByCreatedAtDesc(taskId, pageable);

        return comments.map(comment -> {
            User user = userRepository.findById(comment.getUserId())
                                      .orElseThrow(
                                          () -> new CustomException(ErrorCode.USER_NOT_FOUND));
            UserSummaryDto userSummary = UserSummaryDto.of(user);
            return CommentResponseDto.of(comment, userSummary);
        });
    }

    // 댓글 검색
    public Page<CommentResponseDto> searchComments(String keyword, Pageable pageable) {
        Page<Comment> comments = commentRepository
            .findByContentContainingIgnoreCaseAndIsDeletedFalseOrderByCreatedAtDesc(keyword,
                pageable);

        return comments.map(comment -> {
            User user = userRepository.findById(comment.getUserId())
                                      .orElseThrow(
                                          () -> new CustomException(ErrorCode.USER_NOT_FOUND));
            UserSummaryDto userDto = UserSummaryDto.of(user);
            return CommentResponseDto.of(comment, userDto);
        });
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findByIdAndIsDeletedFalse(commentId).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (comment.getIsDeleted()) {
            throw new CustomException(ErrorCode.COMMENT_ALREADY_DELETED);
        }

        comment.softDelete();
        commentRepository.save(comment);
    }
}