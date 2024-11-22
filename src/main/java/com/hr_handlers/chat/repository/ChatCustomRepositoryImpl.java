package com.hr_handlers.chat.repository;

import com.hr_handlers.chat.entity.Chat;
import com.hr_handlers.chat.entity.ChatRoom;
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
}
