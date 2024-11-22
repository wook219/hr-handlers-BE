package com.hr_handlers.employee.mapper;

import com.hr_handlers.admin.dto.employee.EmpRegisterDto;
import com.hr_handlers.employee.entity.Employee;

public class EmpMapper {

    // EmpRegisterDto -> Employee
    public static Employee toEmployeeEntity(EmpRegisterDto registerRequest, String encodedPassword) {
        return Employee.builder()
                .empNo(registerRequest.getEmpNo())
                .password(encodedPassword) // 암호화된 비밀번호
                .leaveBalance(registerRequest.getLeaveBalance())
                .role(registerRequest.getRole())
                .contractType(registerRequest.getContractType())
                .joinDate(registerRequest.getJoinDate())
                .name(registerRequest.getName())
                .position(registerRequest.getPosition())
                .email(registerRequest.getEmail())
                .phone(registerRequest.getPhone())
                .birthDate(registerRequest.getBirthDate())
                .build();
    }
}
