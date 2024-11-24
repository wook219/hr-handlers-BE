package com.hr_handlers.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoRequest {

    private String title;

    private String content;

    private Timestamp startTime;

    private Timestamp endTime;

    private Long employeeId;
}
