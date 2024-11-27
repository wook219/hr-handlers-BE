package com.hr_handlers.chat.controller;

import com.hr_handlers.chat.dto.*;
import com.hr_handlers.chat.service.ChatMessageService;
import com.hr_handlers.chat.service.ChatRoomService;
import com.hr_handlers.chat.service.ChatService;
import com.hr_handlers.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;

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
    
    // 채팅방 메시지 내역 조회
    @GetMapping("/{chatRoomId}")
    public SuccessResponse<List<ChatMessageResponseDto>> getChatMessages(@PathVariable("chatRoomId") Long chatRoomId) {
        return chatMessageService.getChatMessages(chatRoomId);
    }

    // 채팅방 참여
    @PostMapping("/{chatRoomId}")
    public SuccessResponse<ChatResponseDto> enterChat(
            @PathVariable("chatRoomId") Long chatRoomId,
            Authentication authentication
    ) {
        String empNo = authentication.getName();
        return chatService.enterChatRoom(chatRoomId, empNo);
    }

    // 채팅방 삭제
    @DeleteMapping("/{chatRoomId}")
    public SuccessResponse<Long> deleteChatRoom(@PathVariable("chatRoomId") Long chatRoomId) {
        return chatRoomService.deleteChatRoom(chatRoomId);
    }
}
