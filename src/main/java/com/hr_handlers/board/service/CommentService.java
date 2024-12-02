package com.hr_handlers.board.service;

import com.hr_handlers.board.dto.CommentActionResponseDto;
import com.hr_handlers.board.dto.CommentRequestDto;
import com.hr_handlers.board.dto.CommentResponseDto;
import com.hr_handlers.board.entity.ChildComment;
import com.hr_handlers.board.entity.ParentComment;
import com.hr_handlers.board.entity.Post;
import com.hr_handlers.board.repository.ChildCommentRepository;
import com.hr_handlers.board.repository.ParentCommentRepository;
import com.hr_handlers.board.repository.PostRepository;
import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.employee.repository.EmpRepository;
import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.global.exception.GlobalException;
import com.hr_handlers.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ParentCommentRepository parentCommentRepository;
    private final ChildCommentRepository childCommentRepository;
    private final PostRepository postRepository;
    private final EmpRepository empRepository;

    // 댓글 조회
    public SuccessResponse<List<CommentResponseDto>> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        List<CommentResponseDto> comments = parentCommentRepository.findByPostAndIsDelete(post, "N").stream()
                .map(this::mapParentCommentToDto)
                .collect(Collectors.toList());

        return SuccessResponse.of("댓글 조회 성공", comments);
    }

    // 댓글 작성
    @Transactional
    public SuccessResponse<CommentActionResponseDto> createComment(Long postId, CommentRequestDto request, String empNo) {
        Employee employee = empRepository.findByEmpNo(empNo)
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        ParentComment parentComment = ParentComment.builder()
                .post(post)
                .employee(employee)
                .commentContent(request.getContent())
                .isDelete("N")
                .build();

        parentComment = parentCommentRepository.save(parentComment);

        return SuccessResponse.of("댓글 작성 성공",
                CommentActionResponseDto.builder()
                        .id(parentComment.getId())
                        .timestamp(parentComment.getCreatedAt().toString())
                        .build());
    }

    // 댓글 수정
    @Transactional
    public SuccessResponse<CommentActionResponseDto> updateComment(Long commentId, CommentRequestDto request, String empNo) {
        Employee employee = empRepository.findByEmpNo(empNo)
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        ParentComment parentComment = parentCommentRepository.findById(commentId)
                .orElseThrow(() -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));

        if (!parentComment.getEmployee().equals(employee)) {
            throw new GlobalException(ErrorCode.COMMENT_UPDATE_UNAUTHORIZED);
        }

        parentComment.setCommentContent(request.getContent());
        parentComment = parentCommentRepository.save(parentComment);

        return SuccessResponse.of("댓글 수정 성공",
                CommentActionResponseDto.builder()
                        .id(parentComment.getId())
                        .timestamp(parentComment.getUpdatedAt().toString())
                        .build());
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, String empNo) {
        Employee employee = empRepository.findByEmpNo(empNo)
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        ParentComment parentComment = parentCommentRepository.findById(commentId)
                .orElseThrow(() -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));

        if (!parentComment.getEmployee().equals(employee)) {
            throw new GlobalException(ErrorCode.COMMENT_DELETE_UNAUTHORIZED);
        }

        parentComment.setIsDelete("Y");
        parentCommentRepository.save(parentComment);
    }

    // 대댓글 조회
    public SuccessResponse<List<CommentResponseDto>> getRepliesByComment(Long parentCommentId) {
        ParentComment parentComment = parentCommentRepository.findById(parentCommentId)
                .orElseThrow(() -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));

        List<CommentResponseDto> replies = childCommentRepository.findByParentCommentAndIsDelete(parentComment, "N").stream()
                .map(this::mapChildCommentToDto)
                .collect(Collectors.toList());

        return SuccessResponse.of("대댓글 조회 성공", replies);
    }

    // 대댓글 작성
    @Transactional
    public SuccessResponse<CommentActionResponseDto> createReply(Long parentCommentId, CommentRequestDto request, String empNo) {
        Employee employee = empRepository.findByEmpNo(empNo)
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        ParentComment parentComment = parentCommentRepository.findById(parentCommentId)
                .orElseThrow(() -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));

        ChildComment childComment = ChildComment.builder()
                .parentComment(parentComment)
                .employee(employee)
                .commentContent(request.getContent())
                .isDelete("N")
                .build();

        childComment = childCommentRepository.save(childComment);

        return SuccessResponse.of("대댓글 작성 성공",
                CommentActionResponseDto.builder()
                        .id(childComment.getId())
                        .timestamp(childComment.getCreatedAt().toString())
                        .build());
    }

    private CommentResponseDto mapParentCommentToDto(ParentComment parentComment) {
        return CommentResponseDto.builder()
                .id(parentComment.getId())
                .content(parentComment.getCommentContent())
                .employeeId(parentComment.getEmployee().getId())
                .employeeName(parentComment.getEmployee().getName())
                .createdAt(parentComment.getCreatedAt())
                .build();
    }

    private CommentResponseDto mapChildCommentToDto(ChildComment childComment) {
        return CommentResponseDto.builder()
                .id(childComment.getId())
                .content(childComment.getCommentContent())
                .employeeId(childComment.getEmployee().getId())
                .employeeName(childComment.getEmployee().getName())
                .createdAt(childComment.getCreatedAt())
                .build();
    }
}
