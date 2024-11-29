package com.hr_handlers.admin.repository.employee;

import com.hr_handlers.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminEmpRepository extends JpaRepository<Employee, Long>, AdminEmpCustomRepository {
    Page<Employee> findAll(Pageable pageable);

    Optional<Employee> findByEmpNo(String empNo);
}