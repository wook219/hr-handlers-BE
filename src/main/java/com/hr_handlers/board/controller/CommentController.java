package com.hr_handlers.board.controller;

import com.hr_handlers.board.dto.CommentResponseDto;
import com.hr_handlers.board.service.CommentService;
import com.hr_handlers.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /*
    // 특정 게시글의 댓글 조회
    @GetMapping("/post/{post_id}/comment")
    public SuccessResponse<List<CommentResponseDto>> getCommentsByPost(@PathVariable Long post_id) {
        return commentService.getCommentsByPost(post_id);
    }

    // 특정 게시글에 댓글 작성
    @PostMapping("/post/{post_id}/comment")
    public SuccessResponse<CommentResponseDto> createComment(@PathVariable Long post_id, @RequestBody CommentResponseDto request) {
        return commentService.createComment(post_id, request);
    }

    // 특정 댓글 수정
    @PutMapping("/comment/{id}")
    public SuccessResponse<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentResponseDto request) {
        return commentService.updateComment(id, request);
    }

    // 특정 댓글 삭제
    @DeleteMapping("/comment/{id}")
    public SuccessResponse<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return SuccessResponse.of("댓글 삭제 성공", "댓글이 삭제되었습니다.");
    }

    // 대댓글 조회
    @GetMapping("/reply/{pcomment_id}")
    public SuccessResponse<List<CommentResponseDto>> getRepliesByComment(@PathVariable Long pcomment_id) {
        return commentService.getRepliesByComment(pcomment_id);
    }

    // 대댓글 등록
    @PostMapping("/reply/{pcomment_id}")
    public SuccessResponse<CommentResponseDto> createReply(@PathVariable Long pcomment_id, @RequestBody CommentResponseDto request) {
        return commentService.createReply(pcomment_id, request);
    }

    // 대댓글 수정
    @PutMapping("/reply/{id}")
    public SuccessResponse<CommentResponseDto> updateReply(@PathVariable Long id, @RequestBody CommentResponseDto request) {
        return commentService.updateReply(id, request);
    }

    // 대댓글 삭제
    @DeleteMapping("/reply/{id}")
    public SuccessResponse<String> deleteReply(@PathVariable Long id) {
        commentService.deleteReply(id);
        return SuccessResponse.of("대댓글 삭제 성공", "대댓글이 삭제되었습니다.");
    }
    */
}
