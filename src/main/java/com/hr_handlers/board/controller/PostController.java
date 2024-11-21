package com.hr_handlers.board.controller;

import com.hr_handlers.board.dto.PostDetailResponseDto;
import com.hr_handlers.board.dto.PostResponseDto;
import com.hr_handlers.board.service.PostService;
import com.hr_handlers.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping
    public SuccessResponse<List<PostResponseDto>> getAllPosts() {
        return postService.getAllPosts();
    }

    // 특정 게시글 상세 조회
    @GetMapping("/{id}")
    public SuccessResponse<PostDetailResponseDto> getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    /*
    // 게시글 생성
    @PostMapping
    public SuccessResponse<PostDetailResponseDto> createPost(@RequestBody PostDetailResponseDto request) {
        PostDetailResponseDto createdPost = postService.createPost(request);
        return SuccessResponse.of("게시글 생성 성공", createdPost);
    }

    // 특정 게시글 수정
    @PutMapping("/{id}")
    public SuccessResponse<PostDetailResponseDto> updatePost(@PathVariable Long id, @RequestBody PostDetailResponseDto request) {
        PostDetailResponseDto updatedPost = postService.updatePost(id, request);
        return SuccessResponse.of("게시글 수정 성공", updatedPost);
    }

    // 특정 게시글 삭제
    @DeleteMapping("/{id}")
    public SuccessResponse<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return SuccessResponse.of("게시글 삭제 성공", "게시글이 삭제되었습니다.");
    }

    // 특정 해시태그로 게시글 조회
    @GetMapping(params = "hashtag")
    public SuccessResponse<List<PostResponseDto>> getPostsByHashtag(@RequestParam String hashtag) {
        return postService.getPostsByHashtag(hashtag);
    }
    */
}
