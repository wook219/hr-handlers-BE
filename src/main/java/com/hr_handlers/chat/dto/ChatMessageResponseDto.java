package com.hr_handlers.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatMessageResponseDto {
    private Long messageId;
    private Long chatRoomId;
    private String employeeName;
    private String message;
    private Timestamp createdAt;
}
