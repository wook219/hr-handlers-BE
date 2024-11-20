package com.hr_handlers.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String content;
    private Long employeeId;
    private String employeeName;
    private Timestamp createdAt;
}
