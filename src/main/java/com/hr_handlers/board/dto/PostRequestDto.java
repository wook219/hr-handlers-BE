package com.hr_handlers.board.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostRequestDto {
    private String title;
    private Long employeeId; // 작성자 ID (수정 시 필요 없으면 null 허용)
    private String content;
    private List<String> hashtagContent;
    private String imageUrl;
}
