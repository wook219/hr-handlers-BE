package com.hr_handlers.chat.service;

import com.hr_handlers.chat.dto.ChatResponseDto;
import com.hr_handlers.chat.entity.Chat;
import com.hr_handlers.chat.entity.ChatId;
import com.hr_handlers.chat.entity.ChatRoom;
import com.hr_handlers.chat.mapper.ChatMapper;
import com.hr_handlers.chat.repository.ChatRepository;
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
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final EmpRepository empRepository;
    private final ChatMapper chatMapper;

    // 채팅 참여 추가 -> ChatRoom에서 추가를 받을 것
    public SuccessResponse<ChatResponseDto> enterChatRoom(Long chatRoomId, Long employeeId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new GlobalException(ErrorCode.CHAT_ROOM_NOT_FOUND));
        Employee employee = empRepository.findById(employeeId)
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        // ChatId 객체 생성
        ChatId chatId = ChatId.builder()
                .chatRoomId(chatRoomId)
                .employeeId(employeeId)
                .build();

        // Chat 객체 생성
        Chat chat = Chat.builder()
                .id(chatId)
                .chatRoom(chatRoom)
                .employee(employee)
                .build();

        chatRoomRepository.save(chatRoom);

        Chat enteredChat = chatRepository.save(chat);
        ChatResponseDto chatResponseDto = chatMapper.toChatResponseDto(enteredChat);

        return SuccessResponse.of("채팅 참여에 성공했습니다.", chatResponseDto);
    }
    
    // 참여한 채팅 목록 조회
    public SuccessResponse<List<ChatResponseDto>> getChats(Long employeeId) {

        List<Chat> chats = chatRepository.findByEmployeeId(employeeId);
        List<ChatResponseDto> chatResponseDtos = new ArrayList<>();

        for (Chat chat : chats) {
            chatResponseDtos.add(chatMapper.toChatResponseDto(chat));
        }

        return SuccessResponse.of("참여한 채팅 목록 조회에 성공했습니다.", chatResponseDtos);
    }

    // 채팅방 탈퇴
    // 탈퇴하면서 채팅방 참여 인원도 감소시킬 것
    public SuccessResponse<Long> exitChatRoom(Long chatRoomId, Long employeeId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new GlobalException(ErrorCode.CHAT_ROOM_NOT_FOUND));
        Employee employee = empRepository.findById(employeeId)
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        // Chat 객체 찾기
        Chat chat = chatRepository.findByChatId(chatRoom.getId(), employee.getId());

        // Chat 객체가 없는 경우
        if (chat == null) {
            throw new GlobalException(ErrorCode.CHAT_NOT_FOUND); 
        }

        chatRoomRepository.save(chatRoom);
        chatRepository.delete(chat);

        return SuccessResponse.of("채팅방 퇴장에 성공했습니다.", chatRoomId);
    }
}