package com.hr_handlers.admin.repository.search;

import com.hr_handlers.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminEmpRepository extends JpaRepository<Employee, Long>, EmpSearchRepository {
    Page<Employee> findAll(Pageable pageable);
}