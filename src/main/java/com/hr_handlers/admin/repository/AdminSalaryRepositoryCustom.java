package com.hr_handlers.admin.repository;

import com.hr_handlers.admin.dto.salary.response.AdminSalaryResponseDto;

import java.util.List;

public interface AdminSalaryRepositoryCustom {

    List<AdminSalaryResponseDto> findAllSalary();
}
