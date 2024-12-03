package com.hr_handlers.chat.repository;

import com.hr_handlers.chat.dto.ChatResponseDto;
import com.hr_handlers.chat.entity.Chat;
import com.hr_handlers.chat.entity.ChatRoom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hr_handlers.chat.entity.QChat.chat;

@Repository
@AllArgsConstructor
public class ChatCustomRepositoryImpl implements ChatCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Chat> findByEmployeeId(Long employeeId) {
        return jpaQueryFactory
                .selectFrom(chat)
                .where(
                        chat.employee.id.eq(employeeId)
                )
                .fetch();
    }

    @Override
    public Chat findByChatId(Long chatRoomId, Long employeeId) {
        return jpaQueryFactory
                .selectFrom(chat)
                .where(
                    chat.chatRoom.id.eq(chatRoomId)
                            .and(chat.employee.id.eq(employeeId))
                )
                .fetchOne();
    }

    @Override
    public List<ChatResponseDto> findJoinedEmployees(Long chatRoomId) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ChatResponseDto.class,
                                chat.employee.id,
                                chat.chatRoom.id,
                                chat.chatRoom.title,
                                chat.employee.empNo,
                                chat.employee.name,
                                chat.employee.position,
                                chat.employee.department.deptName
                        )
                )
                .from(chat)
                .join(chat.chatRoom)
                .join(chat.employee)
                .where(
                        chat.chatRoom.id.eq(chatRoomId)
                )
                .fetch();
    }

    // 채팅 참여 삭제
    @Override
    public void deleteChatByChatRoomId(Long chatRoomId) {
        jpaQueryFactory
                .delete(chat)
                .where(
                        chat.chatRoom.id.eq(chatRoomId)
                )
                .execute();
    }

    // 채팅 참여 인원 확인
    @Override
    public Long countChatByChatRoomId(Long chatRoomId) {
        return jpaQueryFactory
                .select(chat.count())
                .from(chat)
                .where(
                        chat.chatRoom.id.eq(chatRoomId)
                )
                .fetchOne();
    }
}
