package com.hr_handlers.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private Long employeeId;
    private String employeeName;
    private Timestamp createdAt;
    private List<String> hashtagContent;
    private String imageUrl;
}