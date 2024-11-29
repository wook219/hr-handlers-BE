package com.hr_handlers.board.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostRequestDto {
    private String title;
    private String content;
    private List<String> hashtagContent;
    private String imageUrl;
}
