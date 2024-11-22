package com.hr_handlers.admin.dto.employee;

import com.hr_handlers.employee.enums.ContractType;
import com.hr_handlers.employee.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmpRegisterDto {
    private String empNo;
    private String password;
    private double leaveBalance;
    private Role role;
    private ContractType contractType;
    private LocalDate joinDate;
    private String name;
    private String position;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String deptName;
    // private String introduction;
}
