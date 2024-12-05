package com.hr_handlers.chat.repository;

import com.hr_handlers.chat.dto.ChatResponseDto;
import com.hr_handlers.chat.entity.Chat;

import java.util.List;

public interface ChatCustomRepository {

    // 참여한 채팅 목록 조회
    List<Chat> findByEmployeeId(Long employeeId);

    // Chat 객체 찾기
    Chat findByChatId(Long chatRoomId, Long employeeId);

    // 채팅 참여
    Chat insertChat(Long chatRoomId, Long employeeId);

    // 채팅방 참여 사원 목록 조회
    List<ChatResponseDto> findJoinedEmployees(Long chatRoomId);

    // 채팅 참여 삭제
    void deleteChatByChatRoomId(Long chatRoomId);

    // 채팅 참여 인원 조회
    Long countChatByChatRoomId(Long chatRoomId);
}
