package com.hr_handlers.vacation.dto;

import com.hr_handlers.vacation.entity.VacationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VacationModifyRequest {
    private String title;
    private VacationType type;
    private Timestamp startDate;
    private Timestamp endDate;
    private String reason;
}
