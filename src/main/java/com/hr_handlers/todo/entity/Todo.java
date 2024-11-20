package com.hr_handlers.todo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    @Size(max = 30)
    private String title;

    @Column(name = "content")
    @Size(max = 200)
    private String content;

    @Column(name = "start_time", nullable = false)
    private Timestamp start_time;

    @Column(name = "end_time", nullable = false)
    private Timestamp end_time;

    @Column(name = "employee_id", nullable = false)
    private Long employee_id;

//    @ManyToOne
//    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
//    private User user;
}
