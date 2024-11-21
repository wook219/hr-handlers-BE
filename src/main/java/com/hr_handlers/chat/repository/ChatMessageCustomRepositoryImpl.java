package com.hr_handlers.chat.repository;

import com.hr_handlers.chat.entity.ChatMessage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hr_handlers.chat.entity.QChatMessage.chatMessage;

@Repository
@AllArgsConstructor
public class ChatMessageCustomRepositoryImpl implements ChatMessageCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ChatMessage> findChatMessagesByChatRoomId(Long chatRoomId) {
        return jpaQueryFactory
                .selectFrom(chatMessage)
                .where(
                        chatMessage.chatRoom.id.eq(chatRoomId)
                )
                .fetch();
    }

    @Override
    @Transactional
    public void deleteChatMessagesByChatRoomId(Long chatRoomId) {
        jpaQueryFactory
                .delete(chatMessage)
                .where(
                        chatMessage.chatRoom.id.eq(chatRoomId)
                )
                .execute();
    }
}
