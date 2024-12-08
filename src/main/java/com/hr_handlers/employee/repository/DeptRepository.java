package com.hr_handlers.employee.repository;

import com.hr_handlers.admin.repository.employee.AdminDeptCustomRepository;
import com.hr_handlers.employee.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeptRepository extends JpaRepository<Department, Long>, AdminDeptCustomRepository {
    Optional<Department> findByDeptName(String deptName);

    boolean existsByDeptName(String deptName);
}
