package com.hr_handlers.chat.entity;

import com.hr_handlers.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat")
public class Chat {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    @EmbeddedId
    private ChatId id;

    @MapsId("chatRoomId")
    @ManyToOne(targetEntity=ChatRoom.class)
    private ChatRoom chatRoom;

    @MapsId("employeeId")
    @ManyToOne(targetEntity=Employee.class)
    private Employee employee;

}
