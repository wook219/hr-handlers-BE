package com.hr_handlers.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentRequestDto {
    private String content;
}
