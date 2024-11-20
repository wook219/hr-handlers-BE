package com.hr_handlers.board.controller;

import com.hr_handlers.board.dto.DeleteResponseDto;
import com.hr_handlers.board.dto.PostDetailResponseDto;
import com.hr_handlers.board.dto.PostResponseDto;
import com.hr_handlers.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostApiController {

    private final PostService postService;


    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    /*
    @GetMapping("/{id}")
    public ResponseEntity<PostDetailResponseDto> getPostById(@PathVariable Long id) {
        // 특정 게시글 상세 조회 로직
        return ResponseEntity.ok().body();
    }

    @PostMapping
    public ResponseEntity<PostDetailResponseDto> createPost(@RequestBody PostDetailResponseDto request) {
        // 게시글 생성 로직
        return ResponseEntity.status(201).body();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDetailResponseDto> updatePost(@PathVariable Long id, @RequestBody PostDetailResponseDto request) {
        // 특정 게시글 수정 로직
        return ResponseEntity.ok().body();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponseDto> deletePost(@PathVariable Long id) {
        // 특정 게시글 삭제 로직
        return ResponseEntity.ok().body(new DeleteResponseDto("게시글이 삭제되었습니다."));
    }

    @GetMapping(params = "hashtag")
    public ResponseEntity<List<PostResponseDto>> getPostsByHashtag(@RequestParam String hashtag) {
        // 특정 해시태그로 게시글 조회 로직
        return ResponseEntity.ok().body();
    }
    */
}
