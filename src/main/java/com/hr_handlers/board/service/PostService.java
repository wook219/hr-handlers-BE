package com.hr_handlers.board.service;

import com.hr_handlers.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hr_handlers.board.dto.PostResponseDto;
import com.hr_handlers.board.entity.HashTag; // HashTag 클래스의 정확한 경로


import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    // 게시글 목록 조회
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
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
}
