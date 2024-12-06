package com.hr_handlers.admin.repository.employee;

import com.hr_handlers.admin.dto.employee.request.AdminEmpUpdateRequestDto;
import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.global.dto.SearchRequestDto;
import org.springframework.data.domain.Page;

public interface AdminEmpCustomRepository {
    // 사원 이름으로 검색
    Page<Employee> findEmpByName(SearchRequestDto requestDto);

    void updateEmp(String empNo, AdminEmpUpdateRequestDto updateRequest);

    void deleteEmp(String empNo);
}