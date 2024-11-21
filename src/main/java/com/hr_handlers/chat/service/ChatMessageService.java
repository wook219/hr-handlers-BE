package com.hr_handlers.chat.service;

import com.hr_handlers.chat.dto.ChatMessageRequestDto;
import com.hr_handlers.chat.dto.ChatMessageResponseDto;
import com.hr_handlers.chat.entity.ChatMessage;
import com.hr_handlers.chat.entity.ChatRoom;
import com.hr_handlers.chat.mapper.ChatMessageMapper;
import com.hr_handlers.chat.repository.ChatMessageRepository;
import com.hr_handlers.chat.repository.ChatRoomRepository;
import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.employee.repository.EmpRepository;
import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageMapper chatMessageMapper;

    private final EmpRepository empRepository;

    // 메시지 전송
    public ChatMessageResponseDto sendMessage(Long chatRoomId, ChatMessageRequestDto chatMessageRequestDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new GlobalException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        Employee employee = empRepository.findById(chatMessageRequestDto.getEmployeeId())
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        ChatMessage chatMessage = ChatMessage.builder()
                .message(chatMessageRequestDto.getMessage())
                .chatRoom(chatRoom)
                .employee(employee)
                .build();

        ChatMessage savedChatMessage = chatMessageRepository.save(chatMessage);
        return chatMessageMapper.toChatMessageResponseDto(savedChatMessage);
    }

    // 메시지 조회
    public SuccessResponse<List<ChatMessageResponseDto>> getChatMessages(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new GlobalException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        List<ChatMessage> chatMessages = chatMessageRepository.findChatMessagesByChatRoomId(chatRoomId);
        List<ChatMessageResponseDto> chatMessageResponseDtos = new ArrayList<>();

        for (ChatMessage chatMessage : chatMessages) {
            chatMessageResponseDtos.add(chatMessageMapper.toChatMessageResponseDto(chatMessage));
        }

        return SuccessResponse.of("메시지 내역 조회 성공", chatMessageResponseDtos);
    }

    // 메시지 수정
    public ChatMessageResponseDto updateMessage(Long chatMessageId, ChatMessageRequestDto chatMessageRequestDto) {
        ChatMessage chatMessage = chatMessageRepository.findById(chatMessageId)
                .orElseThrow(() -> new GlobalException(ErrorCode.CHAT_MESSAGE_NOT_FOUND));

        if (!chatMessage.getEmployee().getId().equals(chatMessageRequestDto.getEmployeeId())) {
            throw new GlobalException(ErrorCode.CHAT_MESSAGE_UPDATE_UNAUTHORIZED);
        }

        chatMessage.updateMessage(chatMessageRequestDto.getMessage());
        ChatMessage updatedChatMessage = chatMessageRepository.save(chatMessage);

        return chatMessageMapper.toChatMessageResponseDto(updatedChatMessage);
    }

    // 메시지 삭제
    public ChatMessageResponseDto deleteMessage(ChatMessageRequestDto chatMessageRequestDto) {
        ChatMessage chatMessage = chatMessageRepository.findById(chatMessageRequestDto.getMessageId())
                .orElseThrow(() -> new GlobalException(ErrorCode.CHAT_MESSAGE_NOT_FOUND));

        if (!chatMessage.getEmployee().getId().equals(chatMessageRequestDto.getEmployeeId())) {
            throw new GlobalException(ErrorCode.CHAT_MESSAGE_DELETE_UNAUTHORIZED);
        }

        ChatMessageResponseDto deletedChatMessageResponseDto = chatMessageMapper.toChatMessageResponseDto(chatMessage);
        chatMessageRepository.delete(chatMessage);

        return deletedChatMessageResponseDto;
    }
}
