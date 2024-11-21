package com.hr_handlers.chat.service;

import com.hr_handlers.chat.dto.ChatRoomRequestDto;
import com.hr_handlers.chat.dto.ChatRoomResponseDto;
import com.hr_handlers.chat.entity.ChatRoom;
import com.hr_handlers.chat.mapper.ChatRoomMapper;
import com.hr_handlers.chat.repository.ChatRoomRepository;
import com.hr_handlers.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;

    // 채팅방 생성
    public SuccessResponse<ChatRoomResponseDto> createChatRoom(ChatRoomRequestDto chatRoomRequestDto) {
        ChatRoom chatRoom = ChatRoom.builder()
                .title(chatRoomRequestDto.getTitle())
                .userCount(0)
                .build();

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        ChatRoomResponseDto chatRoomResponseDto = chatRoomMapper.toDto(savedChatRoom);
        return SuccessResponse.of("채팅방 생성 성공", chatRoomResponseDto);
    }

    // 채팅방 조회
    public SuccessResponse<List<ChatRoomResponseDto>> getChatRooms() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();

        List<ChatRoomResponseDto> chatRoomResponseDtos = new ArrayList<>();

        for (ChatRoom chatRoom : chatRooms) {
            chatRoomResponseDtos.add(chatRoomMapper.toDto(chatRoom));
        }
        return SuccessResponse.of("생성된 채팅방 목록 조회 성공", chatRoomResponseDtos);
    }
}
