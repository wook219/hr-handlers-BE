package com.hr_handlers.chat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ChatRoomCustomeRepositoryImpl {

    private final JPAQueryFactory jpaQueryFactory;

}
