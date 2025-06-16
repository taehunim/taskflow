package com.sparta.taskflow.domain.comment.controller;

import com.sparta.taskflow.domain.comment.dto.CommentRequestDto;
import com.sparta.taskflow.domain.comment.dto.CommentResponseDto;
import com.sparta.taskflow.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
        @RequestBody CommentRequestDto requestDto) {
        CommentResponseDto response = commentService.createComment(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}