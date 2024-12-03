package com.hr_handlers.employee.dto.response;

import com.hr_handlers.employee.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class EmpDetailResponseDto {
    private String empNo;
    private String name;
    private String email;
    private String phone;
    private String position;
    private Role role;
    private LocalDate birthDate;
    private String contractType;
    private LocalDate joinDate;
    private String introduction;
    private Double leaveBalance;
    private String profileImageUrl;
    private String deptName;
    private String password;
}