package com.hr_handlers.chat.mapper;

import com.hr_handlers.chat.dto.ChatResponseDto;
import com.hr_handlers.chat.entity.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "chatRoom.id", target = "chatRoomId")
    ChatResponseDto toChatResponseDto(Chat chat);

}
