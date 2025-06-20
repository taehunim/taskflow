package com.sparta.taskflow.domain.comment.controller;

import com.sparta.taskflow.domain.comment.dto.CommentRequestDto;
import com.sparta.taskflow.domain.comment.dto.CommentResponseDto;
import com.sparta.taskflow.domain.comment.service.CommentService;
import com.sparta.taskflow.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks/{taskId}/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(
        @PathVariable Long taskId,
        @Valid @RequestBody CommentRequestDto requestDto
    ) {
        CommentResponseDto responseDto = commentService.createComment(requestDto, taskId);
        ApiResponse<CommentResponseDto> response = ApiResponse.success("댓글이 생성되었습니다.", responseDto);
        return ResponseEntity.status(201).body(response);
    }

    // 댓글 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CommentResponseDto>>> getCommentsByTask(
        @PathVariable Long taskId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<CommentResponseDto> responsePage = commentService.getCommentsByTask(taskId, pageable);
        ApiResponse<Page<CommentResponseDto>> response = ApiResponse.success("댓글 목록을 조회했습니다.",
            responsePage);
        return ResponseEntity.ok(response);
    }

    // 댓글 검색
    @GetMapping("/comments/search")
    public ResponseEntity<ApiResponse<Page<CommentResponseDto>>> searchComments(
        @RequestParam String keyword,
        @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        Page<CommentResponseDto> result = commentService.searchComments(keyword, pageable);
        return ResponseEntity.ok(ApiResponse.success("댓글 검색 결과입니다.", result));
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
        @PathVariable Long taskId,
        @PathVariable Long commentId
    ) {
        commentService.deleteComment(taskId, commentId);
        return ResponseEntity.ok(ApiResponse.success("댓글이 삭제되었습니다.", null));
    }
}