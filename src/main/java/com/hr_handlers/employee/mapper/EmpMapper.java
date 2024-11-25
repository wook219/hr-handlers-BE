package com.hr_handlers.employee.mapper;

import com.hr_handlers.admin.dto.employee.EmpRegisterDto;
import com.hr_handlers.employee.dto.response.EmpDetailResponseDto;
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

    // Employee -> EmpDetailResponseDto
    public static EmpDetailResponseDto toEmpDetailResponseDto(Employee employee) {
        return EmpDetailResponseDto.builder()
                .id(employee.getId())
                .empNo(employee.getEmpNo())
                .name(employee.getName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .position(employee.getPosition())
                .birthDate(employee.getBirthDate())
                .contractType(employee.getContractType() != null ? employee.getContractType().name() : null) // Enum 처리
                .joinDate(employee.getJoinDate())
                .introduction(employee.getIntroduction())
                .leaveBalance(employee.getLeaveBalance())
                .profileImageUrl(employee.getProfileImage() != null ? employee.getProfileImage().getProfileImageUrl() : null) // 프로필 이미지 URL
                .departmentName(employee.getDepartment() != null ? employee.getDepartment().getDeptName() : null) // 부서 이름
                .build();
    }
}
