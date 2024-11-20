package com.hr_handlers.salary.repository;


import com.hr_handlers.salary.dto.response.SalaryResponse;

import java.util.List;

public interface SalaryRepositoryCustom {

    List<SalaryResponse> findSalaryByEmployeeId(Long employeeId);
}
