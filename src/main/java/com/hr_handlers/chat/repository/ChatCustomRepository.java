package com.hr_handlers.chat.repository;

import com.hr_handlers.chat.entity.Chat;

import java.util.List;

public interface ChatCustomRepository {

    // 참여한 채팅 목록 조회
    List<Chat> findByEmployeeId(Long employeeId);

    // Chat 객체 찾기
    Chat findByChatId(Long chatRoomId, Long employeeId);
}
