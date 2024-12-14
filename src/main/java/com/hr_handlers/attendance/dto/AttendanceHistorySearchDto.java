package com.hr_handlers.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceHistorySearchDto {
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
}
