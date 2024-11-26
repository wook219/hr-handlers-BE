package com.hr_handlers.employee.repository;

import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.admin.repository.search.EmpSearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpRepository extends JpaRepository<Employee, Long>, EmpSearchRepository {
    Optional<Employee> findByEmpNo(String empNo);
}