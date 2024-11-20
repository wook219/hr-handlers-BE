package com.hr_handlers.vacation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PendingVacationResponse {
    private String doc_num;
    private String title;
    private Timestamp createdAt;
    private Long employeeId;
}

