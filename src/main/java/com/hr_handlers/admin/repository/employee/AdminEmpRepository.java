package com.hr_handlers.admin.repository.employee;

import com.hr_handlers.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminEmpRepository extends JpaRepository<Employee, Long>, AdminEmpCustomRepository {
    Page<Employee> findAll(Pageable pageable);

    List<Employee> findByPositionAndDepartmentDeptName(String position, String deptName);
}