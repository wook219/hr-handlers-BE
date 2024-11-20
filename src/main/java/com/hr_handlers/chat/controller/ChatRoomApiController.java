package com.hr_handlers.chat.controller;

import com.hr_handlers.chat.dto.ChatRoomRequestDto;
import com.hr_handlers.chat.dto.ChatRoomResponseDto;
import com.hr_handlers.chat.service.ChatMessageService;
import com.hr_handlers.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatroom")
@RequiredArgsConstructor
public class ChatRoomApiController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    // 채팅방 생성
    @PostMapping
    public ResponseEntity createChatRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto) {
        ChatRoomResponseDto chatRoomResponseDto = chatRoomService.createChatRoom(chatRoomRequestDto);

        return ResponseEntity.ok()
                .body(chatRoomResponseDto);
    }
}
