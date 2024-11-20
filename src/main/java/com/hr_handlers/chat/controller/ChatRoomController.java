package com.hr_handlers.chat.controller;

import com.hr_handlers.chat.dto.ChatRoomRequestDto;
import com.hr_handlers.chat.dto.ChatRoomResponseDto;
import com.hr_handlers.chat.service.ChatMessageService;
import com.hr_handlers.chat.service.ChatRoomService;
import com.hr_handlers.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    // 채팅방 생성
    @PostMapping
    public SuccessResponse<ChatRoomResponseDto> createChatRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto) {
        return chatRoomService.createChatRoom(chatRoomRequestDto);
    }

    // 채팅방 목록 조회
    @GetMapping
    public SuccessResponse<List<ChatRoomResponseDto>> getChatRooms() {
        return chatRoomService.getChatRooms();
    }
}
