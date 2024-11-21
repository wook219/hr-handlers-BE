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
    private final EmpRepository empRepository; // 작성자 확인을 위한 Repository

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
    }
}
