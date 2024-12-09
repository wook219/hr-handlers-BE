package com.hr_handlers.chat.controller;

import com.hr_handlers.chat.dto.ChatInviteResponseDto;
import com.hr_handlers.chat.dto.ChatRequestDto;
import com.hr_handlers.chat.dto.ChatResponseDto;
import com.hr_handlers.chat.service.ChatService;
import com.hr_handlers.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public SuccessResponse<Page<ChatResponseDto>> getChats(
            Authentication authentication,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return chatService.getChats(authentication.getName(), keyword, pageable);
    }

    // 채팅방 참여인원 조회
    @GetMapping("/{chatRoomId}")
    public SuccessResponse<List<ChatResponseDto>> getJoinedEmployees(@PathVariable("chatRoomId") Long chatRoomId) {
        return chatService.getJoinedEmployees(chatRoomId);
    }

    // 채팅방 초대 목록 조회
    @GetMapping("/{chatRoomId}/invite")
    public SuccessResponse<List<ChatInviteResponseDto>> getNotExistsChat(
            @PathVariable("chatRoomId") Long chatRoomId,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
    ) {
        return chatService.getNotExistsChat(chatRoomId, keyword);
    }

    // 채팅방 인원 수 조회
    @GetMapping("/{chatRoomId}/count")
    public SuccessResponse<Long> getJoinedEmployeesCount(@PathVariable("chatRoomId") Long chatRoomId) {
        return chatService.getJoinedEmployeesCount(chatRoomId);
    }

    // 비공개 채팅방 초대
    @PostMapping("/{chatRoomId}")
    public SuccessResponse<ChatResponseDto> inviteSecretChat(@PathVariable("chatRoomId") Long chatRoomId,
                                                             @RequestBody ChatRequestDto chatRequestDto) {
        return chatService.enterChatRoom(chatRoomId, chatRequestDto.getEmpNo());
    }

    // 채팅방 퇴장
    @DeleteMapping("/{chatRoomId}")
    public SuccessResponse<Long> deleteChat(@PathVariable("chatRoomId") Long chatRoomId,
                                            Authentication authentication) {
        return chatService.exitChatRoom(chatRoomId, authentication.getName());
    }

}
