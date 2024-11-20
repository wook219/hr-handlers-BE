package com.hr_handlers.chat.controller;

import com.hr_handlers.chat.dto.ChatRoomRequestDto;
import com.hr_handlers.chat.dto.ChatRoomResponseDto;
import com.hr_handlers.chat.service.ChatMessageService;
import com.hr_handlers.chat.service.ChatRoomService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 채팅방 목록 조회
    @GetMapping
    public ResponseEntity<List<ChatRoomResponseDto>> getChatRooms() {
        List<ChatRoomResponseDto> chatRooms = chatRoomService.getChatRooms();

        return ResponseEntity.ok(chatRooms);
    }
}
