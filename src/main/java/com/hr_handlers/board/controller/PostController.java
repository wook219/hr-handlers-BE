package com.hr_handlers.board.controller;

import com.hr_handlers.board.dto.*;
import com.hr_handlers.board.service.PostService;
import com.hr_handlers.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {

    private final PostService postService;

    /**
     * 회원 기능 추가 이후 사용자 구분 예정
     ***/
    @GetMapping
    public SuccessResponse<PostListResponseDto> getAllPosts(
            @PageableDefault(size = 10) Pageable pageable) {
        return postService.getAllPosts(pageable);
    }

    // 특정 게시글 상세 조회
    @GetMapping("/{id}")
    public SuccessResponse<PostDetailResponseDto> getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    // 게시글 생성
    @PostMapping
    public SuccessResponse<PostActionResponseDto> createPost(@RequestBody PostRequestDto request, Authentication authentication) {
        String empNo = authentication.getName();
        return postService.createPost(request, empNo);
    }


    // 특정 게시글 수정
    @PutMapping("/{id}")
    public SuccessResponse<PostActionResponseDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto request) {
        return postService.updatePost(id, request);
    }

    // 특정 게시글 삭제
    @DeleteMapping("/{id}")
    public SuccessResponse<String> deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }

    /*
    // 특정 해시태그로 게시글 조회
    @GetMapping(params = "hashtag")
    public SuccessResponse<List<PostResponseDto>> getPostsByHashtag(@RequestParam String hashtag) {
        return postService.getPostsByHashtag(hashtag);
    }
    */
}
