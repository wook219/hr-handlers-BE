package com.hr_handlers.admin.repository;

import com.hr_handlers.salary.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminAdminSalaryRepository extends JpaRepository<Salary, Long>, AdminSalaryRepositoryCustom {
}