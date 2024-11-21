package com.hr_handlers.board.service;


import com.hr_handlers.board.repository.PostRepository;
import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hr_handlers.board.dto.PostResponseDto;
import com.hr_handlers.board.dto.PostDetailResponseDto;
import com.hr_handlers.board.mapper.PostMapper;
import com.hr_handlers.board.entity.Post;


import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    // 게시글 목록 조회
    public SuccessResponse<List<PostResponseDto>> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        if (posts.isEmpty()) {
            throw new GlobalException(ErrorCode.POSTS_NOT_FOUND);
        }

        List<PostResponseDto> response = posts.stream()
                .map(postMapper::toPostResponseDto)
                .toList();

        return SuccessResponse.of("게시글 목록 조회 성공", response);
    }

    // 게시글 상세 조회
    public SuccessResponse<PostDetailResponseDto> getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        PostDetailResponseDto response = postMapper.toPostDetailResponseDto(post);
        return SuccessResponse.of("게시글 상세 조회 성공", response);
    }
}
