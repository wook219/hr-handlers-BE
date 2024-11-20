package com.hr_handlers.salary.repository;

import com.hr_handlers.employee.entity.QEmployee;
import com.hr_handlers.salary.dto.response.SalaryResponse;
import com.hr_handlers.salary.entity.QSalary;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SalaryRepositoryCustomImpl implements SalaryRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QSalary salary = QSalary.salary;
    private final QEmployee employee = QEmployee.employee;


    public List<SalaryResponse> findSalaryByEmployeeId(Long employeeId) {
        return queryFactory
                .select(Projections.constructor(
                        SalaryResponse.class,
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
                .where(
                        salary.employee.id.eq(employeeId)
                )
                .fetch();
    }
}
