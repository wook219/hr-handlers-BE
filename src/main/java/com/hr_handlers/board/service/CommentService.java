package com.hr_handlers.board.service;

import com.hr_handlers.board.dto.CommentActionResponseDto;
import com.hr_handlers.board.dto.CommentRequestDto;
import com.hr_handlers.board.dto.CommentResponseDto;
import com.hr_handlers.board.entity.Comment;
import com.hr_handlers.board.entity.Post;
import com.hr_handlers.board.repository.CommentRepository;
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
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final EmpRepository empRepository;

    // 댓글 조회
    public SuccessResponse<List<CommentResponseDto>> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        // 최상위 댓글만 조회하고 대댓글은 재귀적으로 매핑
        List<CommentResponseDto> comments = commentRepository
                .findByPostAndParentIsNullAndIsDelete(post, "N")
                .stream()
                .map(comment -> mapToDto(comment, 0))  // level 0부터 시작
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

        Comment comment = Comment.builder()
                .post(post)
                .employee(employee)
                .commentContent(request.getContent())
                .isDelete("N")
                .build();

        // 대댓글인 경우 부모 댓글 설정
        if (request.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));
            comment.setParent(parentComment);
        }

        comment = commentRepository.save(comment);

        return SuccessResponse.of("댓글 작성 성공",
                CommentActionResponseDto.builder()
                        .id(comment.getId())
                        .timestamp(comment.getCreatedAt().toString())
                        .build());
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, String empNo) {
        Employee employee = empRepository.findByEmpNo(empNo)
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getEmployee().equals(employee)) {
            throw new GlobalException(ErrorCode.COMMENT_DELETE_UNAUTHORIZED);
        }

        comment.setIsDelete("Y");
        commentRepository.save(comment);
    }

    private CommentResponseDto mapToDto(Comment comment, int level) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getCommentContent())
                .employeeId(comment.getEmployee().getId())
                .employeeName(comment.getEmployee().getName())
                .createdAt(comment.getCreatedAt())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .level(level)
                .replies(
                        comment.getChildren().stream()
                                .filter(child -> "N".equals(child.getIsDelete()))
                                .map(child -> mapToDto(child, level + 1))  // 재귀적으로 대댓글 매핑
                                .collect(Collectors.toList())
                )
                .build();
    }
}
