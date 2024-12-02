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
    public SuccessResponse<ChatResponseDto> enterChatRoom(Long chatRoomId, String empNo) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new GlobalException(ErrorCode.CHAT_ROOM_NOT_FOUND));
        Employee employee = empRepository.findByEmpNo(empNo)
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        Chat existingChat = chatRepository.findByChatId(chatRoom.getId(), employee.getId());

        if (existingChat == null) {
            // ChatId 객체 생성
            ChatId chatId = ChatId.builder()
                    .chatRoomId(chatRoom.getId())
                    .employeeId(employee.getId())
                    .build();

            // Chat 객체 생성
            Chat chat = Chat.builder()
                    .id(chatId)
                    .chatRoom(chatRoom)
                    .employee(employee)
                    .build();

            Chat enteredChat = chatRepository.save(chat);
            return SuccessResponse.of("채팅 참여에 성공했습니다.", chatMapper.toChatResponseDto(enteredChat));
        } else {
            // 이미 참여 중인 채팅방인 경우
            return SuccessResponse.of("이미 이 채팅방에 참여 중입니다.", chatMapper.toChatResponseDto(existingChat));
        }
    }
    
    // 참여한 채팅 목록 조회
    public SuccessResponse<List<ChatResponseDto>> getChats(String empNo) {
        Employee employee = empRepository.findByEmpNo(empNo)
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));
        List<Chat> chats = chatRepository.findByEmployeeId(employee.getId());
        List<ChatResponseDto> chatResponseDtos = new ArrayList<>();

        for (Chat chat : chats) {
            chatResponseDtos.add(chatMapper.toChatResponseDto(chat));
        }

        return SuccessResponse.of(
                "참여한 채팅 목록 조회에 성공했습니다.",
                chatResponseDtos
        );
    }

    // 채팅방 참여인원 조회
    public SuccessResponse<List<ChatResponseDto>> getJoinedEmployees(Long chatRoomId) {
        return SuccessResponse.of(
                "채팅방에 참여한 사원 목록 조회에 성공했습니다.",
                chatRepository.findJoinedEmployees(chatRoomId)
        );
    }

    // 채팅방 탈퇴
    public SuccessResponse<Long> exitChatRoom(Long chatRoomId, String empNo) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new GlobalException(ErrorCode.CHAT_ROOM_NOT_FOUND));
        Employee employee = empRepository.findByEmpNo(empNo)
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        // Chat 객체 찾기
        Chat chat = chatRepository.findByChatId(chatRoom.getId(), employee.getId());

        // Chat 객체가 없는 경우
        if (chat == null) {
            throw new GlobalException(ErrorCode.CHAT_NOT_FOUND); 
        }

        chatRepository.delete(chat);

        return SuccessResponse.of(
                "채팅방 퇴장에 성공했습니다.",
                chatRoomId
        );
    }
}