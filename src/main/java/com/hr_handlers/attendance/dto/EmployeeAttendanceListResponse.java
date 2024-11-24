package com.hr_handlers.attendance.dto;

import com.hr_handlers.attendance.entity.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAttendanceListResponse {

    private AttendanceStatus status;

    private Timestamp checkInTime;

    private Timestamp checkOutTime;

    private String employeeName;
}
