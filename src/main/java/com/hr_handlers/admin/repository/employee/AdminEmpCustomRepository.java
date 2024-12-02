package com.hr_handlers.admin.repository.employee;

import com.hr_handlers.admin.dto.employee.request.AdminEmpUpdateRequestDto;
import com.hr_handlers.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface AdminEmpCustomRepository {
    // 사원 이름으로 검색
    Page<Employee> findEmpByName(@Param("name") String name, Pageable pageable);

    void updateEmp(String empNo, AdminEmpUpdateRequestDto updateRequest);

    void deleteEmp(String empNo);
}
