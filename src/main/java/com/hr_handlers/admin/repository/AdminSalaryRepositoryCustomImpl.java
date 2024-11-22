package com.hr_handlers.admin.repository;

import com.hr_handlers.admin.dto.salary.response.AdminSalaryResponse;
import com.hr_handlers.employee.entity.QEmployee;
import com.hr_handlers.salary.entity.QSalary;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AdminSalaryRepositoryCustomImpl implements AdminSalaryRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QSalary salary = QSalary.salary;
    private final QEmployee employee = QEmployee.employee;


    public List<AdminSalaryResponse> findAllSalary() {
        return queryFactory
                .select(Projections.constructor(
                        AdminSalaryResponse.class,
                        salary.id,
                        employee.id,
                        employee.department.deptName,
                        employee.position,
                        employee.name,
                        salary.basicSalary,
                        salary.deduction,
                        salary.netSalary,
                        salary.payDate,
                        salary.createdAt,
                        salary.updatedAt
                        ))
                .from(salary)
                .leftJoin(employee).on(salary.employee.id.eq(employee.id)).fetchJoin()
                .orderBy(salary.payDate.asc())
                .fetch();
    }
}
