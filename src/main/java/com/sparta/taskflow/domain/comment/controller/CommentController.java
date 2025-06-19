package com.sparta.taskflow.domain.comment.controller;

import com.sparta.taskflow.domain.comment.dto.request.CreateCommentRequestDto;
import com.sparta.taskflow.domain.comment.dto.response.CommentResponseDto;
import com.sparta.taskflow.domain.comment.dto.response.CreateCommentResponseDto;
import com.sparta.taskflow.domain.comment.service.CommentService;
import com.sparta.taskflow.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성 API (명세서에 맞게 /api/tasks/{taskId}/comments 로 변경)
    @PostMapping("/tasks/{taskId}/comments")
    public ResponseEntity<ApiResponse<CreateCommentResponseDto>> createComment(
        @PathVariable Long taskId,
        @Valid @RequestBody CreateCommentRequestDto requestDto
    ) {
        // pathVariable로 받은 taskId를 requestDto에 세팅
        requestDto.setTaskId(taskId);

        CreateCommentResponseDto responseDto = commentService.createComment(requestDto);
        ApiResponse<CreateCommentResponseDto> response = ApiResponse.success(
            "댓글이 생성되었습니다.",
            responseDto
        );

        return ResponseEntity.status(201).body(response);
    }

    // 댓글 목록 조회 API (페이지네이션 적용, 명세서대로 /api/tasks/{taskId}/comments)
    @GetMapping("/tasks/{taskId}/comments")
    public ResponseEntity<ApiResponse<Page<CommentResponseDto>>> getCommentsByTask(
        @PathVariable Long taskId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<CommentResponseDto> responseDtoPage = commentService.getCommentsByTask(taskId, pageable);

        ApiResponse<Page<CommentResponseDto>> response = ApiResponse.success(
            "댓글 목록을 조회했습니다.",
            responseDtoPage
        );

        return ResponseEntity.ok(response);
    }
}