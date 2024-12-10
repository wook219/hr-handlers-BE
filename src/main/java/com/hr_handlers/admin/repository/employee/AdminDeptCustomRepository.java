package com.hr_handlers.admin.repository.employee;

import com.hr_handlers.employee.entity.Department;
import com.hr_handlers.global.dto.SearchRequestDto;
import org.springframework.data.domain.Page;

public interface AdminDeptCustomRepository {
    // 부서 이름으로 검색
    Page<Department> findDeptByName(SearchRequestDto requestDto);

    void updateDept(Long id, String deptName);

    void deleteDept(Long id);
}