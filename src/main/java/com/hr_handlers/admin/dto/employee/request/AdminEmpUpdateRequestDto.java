package com.hr_handlers.admin.dto.employee.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminEmpUpdateRequestDto {
    private String empNo;
    private String position;
    private String contractType;
    private Double leaveBalance;
    private String departmentName;
}
