package com.hr_handlers.board.controller;

import com.hr_handlers.board.dto.CommentResponseDto;
import com.hr_handlers.board.dto.DeleteResponseDto;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")
public class CommentApiController {
    /*
    @GetMapping("/post/{post_id}/comment")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPost(@PathVariable Long post_id) {
        // 특정 게시글의 댓글 조회 로직
        return ResponseEntity.ok().body();
    }

    @PostMapping("/post/{post_id}/comment")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long post_id, @RequestBody CommentResponseDto request) {
        // 특정 게시글에 댓글 작성 로직
        return ResponseEntity.status(201).body();
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentResponseDto request) {
        // 특정 댓글 수정 로직
        return ResponseEntity.ok().body();
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<DeleteResponseDto> deleteComment(@PathVariable Long id) {
        // 특정 댓글 삭제 로직
        return ResponseEntity.ok().body(new DeleteResponseDto("댓글이 삭제되었습니다."));
    }

    @GetMapping("/reply/{pcomment_id}")
    public ResponseEntity<List<CommentResponseDto>> getRepliesByComment(@PathVariable Long pcomment_id) {
        // 대댓글 조회 로직
        return ResponseEntity.ok().body();
    }

    @PostMapping("/reply/{pcomment_id}")
    public ResponseEntity<CommentResponseDto> createReply(@PathVariable Long pcomment_id, @RequestBody CommentResponseDto request) {
        // 대댓글 등록 로직
        return ResponseEntity.status(201).body();
    }

    @PutMapping("/reply/{id}")
    public ResponseEntity<CommentResponseDto> updateReply(@PathVariable Long id, @RequestBody CommentResponseDto request) {
        // 대댓글 수정 로직
        return ResponseEntity.ok().body();
    }

    @DeleteMapping("/reply/{id}")
    public ResponseEntity<DeleteResponseDto> deleteReply(@PathVariable Long id) {
        // 대댓글 삭제 로직
        return ResponseEntity.ok().body(new DeleteResponseDto("대댓글이 삭제되었습니다."));
    }
    */
}
