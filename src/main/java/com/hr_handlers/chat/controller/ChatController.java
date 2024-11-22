package com.hr_handlers.chat.controller;

import com.hr_handlers.chat.dto.ChatRequestDto;
import com.hr_handlers.chat.dto.ChatResponseDto;
import com.hr_handlers.chat.service.ChatService;
import com.hr_handlers.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    // TODO 시큐리티에 맞춰서 로그인한 사원 번호 꺼내오도록 변경할 것.

    // 참여한 채팅 목록 조회 -> GetMapping의 id는 임시. 시큐리티 적용 후 제외할 것.
    @GetMapping("/{employeeId}")
    public SuccessResponse<List<ChatResponseDto>> getChats(@PathVariable("employeeId") Long employeeId) {
        return chatService.getChats(employeeId);
    }

    // 채팅방 퇴장
    @DeleteMapping("/{chatRoomId}")
    public SuccessResponse<Long> deleteChat(@PathVariable("chatRoomId") Long chatRoomId, @RequestBody ChatRequestDto chatRequestDto) {
        return chatService.exitChatRoom(chatRoomId, chatRequestDto.getEmployeeId());
    }

}
