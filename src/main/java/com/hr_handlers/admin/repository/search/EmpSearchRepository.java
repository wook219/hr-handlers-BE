package com.hr_handlers.admin.repository.search;

import com.hr_handlers.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface EmpSearchRepository {
    // 사원 이름으로 검색
    Page<Employee> findEmpByName(@Param("name") String name, Pageable pageable);
}
