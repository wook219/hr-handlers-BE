package com.hr_handlers.board.controller;

import com.hr_handlers.board.dto.CommentActionResponseDto;
import com.hr_handlers.board.dto.CommentRequestDto;
import com.hr_handlers.board.dto.CommentResponseDto;
import com.hr_handlers.board.service.CommentService;
import com.hr_handlers.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 특정 게시글의 댓글 조회
    @GetMapping("/post/{postId}/comment")
    public SuccessResponse<Page<CommentResponseDto>> getCommentsByPost(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return commentService.getCommentsByPost(postId, page, size);
    }


    // 댓글/대댓글 작성
    @PostMapping("/post/{postId}/comment")
    public SuccessResponse<CommentActionResponseDto> createComment(
            @PathVariable Long postId,
            @RequestBody CommentRequestDto request,
            Authentication authentication
    ) {
        return commentService.createComment(postId, request, authentication.getName());
    }


    // 댓글/대댓글 수정
    @PutMapping("/comment/{id}")
    public SuccessResponse<CommentActionResponseDto> updateComment(
            @PathVariable Long id,
            @RequestBody CommentRequestDto request,
            Authentication authentication
    ) {
        return commentService.updateComment(id, request, authentication.getName());
    }


    // 댓글/대댓글 삭제
    @DeleteMapping("/comment/{id}")
    public SuccessResponse<String> deleteComment(
            @PathVariable Long id,
            Authentication authentication
    ) {
        commentService.deleteComment(id, authentication.getName());
        return SuccessResponse.of("댓글 삭제 성공", "댓글이 삭제되었습니다.");
    }
}
