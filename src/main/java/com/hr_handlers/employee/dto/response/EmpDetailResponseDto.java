package com.hr_handlers.employee.dto.response;

import com.hr_handlers.employee.enums.ContractType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class EmpDetailResponseDto {
    private Long id;
    private String empNo;
    private String name;
    private String email;
    private String phone;
    private String position;
    private LocalDate birthDate;
    private String contractType;
    private LocalDate joinDate;
    private String introduction;
    private Double leaveBalance;
    // private Role role;
    private String profileImageUrl;
    private String departmentName;
}
