package com.hr_handlers.board.service;


import com.hr_handlers.board.dto.PostActionResponseDto;
import com.hr_handlers.board.dto.PostRequestDto;
import com.hr_handlers.board.entity.HashTag;
import com.hr_handlers.board.repository.PostRepository;
import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.employee.repository.EmpRepository;
import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.hr_handlers.board.dto.PostResponseDto;
import com.hr_handlers.board.dto.PostDetailResponseDto;
import com.hr_handlers.board.mapper.PostMapper;
import com.hr_handlers.board.entity.Post;


import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final EmpRepository empRepository;

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


    // 게시글 생성
    public SuccessResponse<PostActionResponseDto> createPost(PostRequestDto request) {
        // 작성자(Employee) 정보 확인
        Employee employee = empRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        try {
            // 요청 DTO를 기반으로 Post 엔티티 생성
            Post post = Post.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .imageUrl(request.getImageUrl())
                    .employee(employee)
                    .isDelete("N") // 삭제 여부 기본값 설정
                    .build();

            if (request.getHashtagContent() != null && !request.getHashtagContent().isEmpty()) {
                List<HashTag> hashTags = request.getHashtagContent().stream()
                        .map(tagContent -> HashTag.builder()
                                .hashtagContent(tagContent)
                                .post(post)
                                .build())
                        .toList();
                post.setHashtagContent(hashTags);
            }

            Post savedPost = postRepository.save(post);


            // 생성된 게시글 정보를 Action Response로 변환
            PostActionResponseDto responseDto = PostActionResponseDto.builder()
                    .id(savedPost.getId())
                    .timestamp(savedPost.getCreatedAt().toString()) // 생성 시간 반환
                    .build();

            return SuccessResponse.of("게시글 생성 성공", responseDto);
        }catch (Exception e){
            throw new GlobalException(ErrorCode.POST_CREATE_FAILED);
        }
    }

    // 게시글 수정
    @Transactional
    public SuccessResponse<PostActionResponseDto> updatePost(Long postId, PostRequestDto dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        // 기존 해시태그 가져오기
        List<HashTag> existingHashTags = post.getHashtagContent();

        // DTO로 전달된 해시태그 리스트
        List<String> newHashtagContents = dto.getHashtagContent();

        // 기존 해시태그 업데이트 또는 추가
        Map<String, HashTag> existingHashTagMap = existingHashTags.stream()
                .collect(Collectors.toMap(HashTag::getHashtagContent, Function.identity()));

        for (String newContent : newHashtagContents) {
            if (existingHashTagMap.containsKey(newContent)) {
                // 이미 존재하는 해시태그는 그대로 둠
                existingHashTagMap.remove(newContent);
            } else {
                // 새로 추가할 해시태그 생성
                HashTag newHashTag = HashTag.builder()
                        .post(post)
                        .hashtagContent(newContent)
                        .build();
                existingHashTags.add(newHashTag);
            }
        }

        // DTO에 없는 기존 해시태그는 제거
        for (HashTag orphanHashTag : existingHashTagMap.values()) {
            existingHashTags.remove(orphanHashTag);
        }

        // Post 정보 업데이트
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setImageUrl(dto.getImageUrl());

        Post updatedPost = postRepository.save(post);

        PostActionResponseDto responseDto = PostActionResponseDto.builder()
                .id(updatedPost.getId())
                .timestamp(updatedPost.getUpdatedAt().toString()) // updatedAt 활용
                .build();

        return SuccessResponse.of("게시글이 성공적으로 수정되었습니다.", responseDto);
    }


    // 게시글 삭제
    public SuccessResponse<String> deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));
        try {
            post.setIsDelete("Y");
            postRepository.save(post);
            return SuccessResponse.of("게시글 삭제 성공", "게시글이 삭제되었습니다.");
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.POST_DELETE_FAILED);
        }
    }
}
