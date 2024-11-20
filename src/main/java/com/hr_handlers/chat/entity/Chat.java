//package com.hr_handlers.chat.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "chat")
//public class Chat {
//
//    @EmbeddedId
//    private ChatId id;
//
//    @ManyToOne
//    @JoinColumn(name = "chat_room_id", referencedColumnName = "id", nullable = false)
//    private ChatRoom chatRoom;
//
//    @ManyToOne
//    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
//    private Employee employee;
//}
