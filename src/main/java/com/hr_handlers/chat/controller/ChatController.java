package com.hr_handlers.chat.controller;

import com.hr_handlers.chat.dto.ChatResponseDto;
import com.hr_handlers.chat.service.ChatService;
import com.hr_handlers.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    // 참여한 채팅 목록 조회
    @GetMapping
    public SuccessResponse<List<ChatResponseDto>> getChats(Authentication authentication) {
        return chatService.getChats(authentication.getName());
    }

    // 채팅방 참여인원 조회
    @GetMapping("/{chatRoomId}")
    public SuccessResponse<List<ChatResponseDto>> getJoinedEmployees(@PathVariable("chatRoomId") Long chatRoomId) {
        return chatService.getJoinedEmployees(chatRoomId);
    }

    // 비공개 채팅방 초대
    @PostMapping("/{chatRoomId}/invite")
    public SuccessResponse<ChatResponseDto> inviteSecretChat(@PathVariable("chatRoomId") Long chatRoomId,
                                                             Authentication authentication) {
        return chatService.inviteSecretChat(chatRoomId, authentication.getName());
    }

    // 채팅방 퇴장
    @DeleteMapping("/{chatRoomId}")
    public SuccessResponse<Long> deleteChat(@PathVariable("chatRoomId") Long chatRoomId,
                                            Authentication authentication) {
        return chatService.exitChatRoom(chatRoomId, authentication.getName());
    }

}
