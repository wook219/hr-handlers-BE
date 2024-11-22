package com.hr_handlers.admin.repository;

import com.hr_handlers.admin.dto.salary.response.AdminSalaryResponse;

import java.util.List;

public interface AdminSalaryRepositoryCustom {

    List<AdminSalaryResponse> findAllSalary();
}
