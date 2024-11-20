package com.hr_handlers.board.service;


import com.hr_handlers.board.repository.PostRepository;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hr_handlers.board.dto.PostResponseDto;
import com.hr_handlers.board.dto.PostDetailResponseDto;
import com.hr_handlers.board.entity.HashTag;
import com.hr_handlers.board.entity.Post;


import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    // 게시글 목록 조회
    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        // 게시글이 없는 경우 예외 발생
        if (posts.isEmpty()) {
            throw new GlobalException(ErrorCode.POSTS_NOT_FOUND);
        }

        return posts.stream()
                .map(post -> PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .employeeId(post.getEmployee().getId())
                        .employeeName(post.getEmployee().getName())
                        .createdAt(post.getCreatedAt())
                        .hashtagContent(post.getHashTags().stream()
                                .map(HashTag::getHashtagContent)
                                .toList())
                        .imageUrl(post.getImageUrl())
                        .build())
                .toList();
    }


    //게시글 상세조회
    public PostDetailResponseDto getPostById(Long id) {
        return postRepository.findById(id)
                .map(post -> PostDetailResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .employeeId(post.getEmployee().getId())
                        .employeeName(post.getEmployee().getName())
                        .createdAt(post.getCreatedAt())
                        .hashtagContent(post.getHashTags().stream()
                                .map(HashTag::getHashtagContent)
                                .toList())
                        .imageUrl(post.getImageUrl())
                        .build())
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND)); // 예외 발생
    }
}
