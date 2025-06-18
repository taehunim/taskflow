package com.sparta.taskflow.domain.comment.controller;

import com.sparta.taskflow.domain.comment.dto.CreateCommentRequestDto;
import com.sparta.taskflow.domain.comment.dto.CreateCommentResponseDto;
import com.sparta.taskflow.domain.comment.service.CommentService;
import com.sparta.taskflow.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateCommentResponseDto>> createComment(
        @Valid @RequestBody CreateCommentRequestDto requestDto
    ) {
        CreateCommentResponseDto responseDto = commentService.createComment(requestDto);
        ApiResponse<CreateCommentResponseDto> response = ApiResponse.success(
            "댓글이 생성되었습니다.",
            responseDto
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse<List<CreateCommentResponseDto>>> getCommentsByTask(
        @PathVariable Long taskId
    ) {
        List<CreateCommentResponseDto> responseDtoList = commentService.getCommentsByTask(taskId);
        return ResponseEntity.ok(ApiResponse.success("댓글을 조회했습니다.", responseDtoList));
    }
}