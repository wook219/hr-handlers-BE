package com.hr_handlers.employee.repository;

import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.employee.repository.custom.EmpCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpRepository extends JpaRepository<Employee, Long>, EmpCustomRepository {
    Optional<Employee> findByEmpNo(String empNo);
}