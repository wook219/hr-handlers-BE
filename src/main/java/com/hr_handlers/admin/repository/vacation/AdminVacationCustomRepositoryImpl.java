package com.hr_handlers.admin.repository.vacation;


import com.hr_handlers.admin.dto.vacation.AdminVacationResponseDto;
import com.hr_handlers.admin.dto.vacation.AdminVacationStatusResponseDto;
import com.hr_handlers.vacation.entity.VacationStatus;
import com.hr_handlers.vacation.entity.VacationType;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hr_handlers.employee.entity.QDepartment.department;
import static com.hr_handlers.employee.entity.QEmployee.employee;
import static com.hr_handlers.vacation.entity.QVacation.vacation;

@Repository
public class AdminVacationCustomRepositoryImpl implements AdminVacationCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public AdminVacationCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<AdminVacationResponseDto> findPendingVacations() {
        return jpaQueryFactory
                .select(Projections.constructor(
                        AdminVacationResponseDto.class,
                        vacation.id,
                        employee.position,                        // 직위
                        employee.department.deptName,                // 부서명
                        employee.name,                          // 이름
                        Expressions.stringTemplate(             // 기간 (startDate ~ endDate)
                                "CONCAT(DATE_FORMAT({0}, '%Y-%m-%d'), ' ~ ', DATE_FORMAT({1}, '%Y-%m-%d'))",
                                vacation.startDate,
                                vacation.endDate
                        ),
                        vacation.type,                          // 휴가 종류
                        new CaseBuilder()                       // 사용 일수
                                .when(vacation.type.eq(VacationType.HALF))
                                .then(Expressions.numberTemplate(Double.class, "0.5"))
                                .when(vacation.type.eq(VacationType.PUBLIC))
                                .then(Expressions.numberTemplate(Double.class, "0.0"))
                                .otherwise(Expressions.numberTemplate(
                                        Double.class,
                                        "DATEDIFF({1}, {0}) + 1",
                                        vacation.startDate,
                                        vacation.endDate
                                )),
                        vacation.status                         // 상태
                ))
                .from(vacation)
                .join(vacation.employee, employee)
                .join(employee.department)
                .where(vacation.status.eq(VacationStatus.PENDING))
                .orderBy(vacation.createdAt.desc())
                .fetch();
    }

    @Override
    public List<AdminVacationStatusResponseDto> findVacationStatusForAllEmployees() {
        return jpaQueryFactory
                .select(Projections.constructor(AdminVacationStatusResponseDto.class,
                        employee.position,
                        department.deptName.as("deptName"),
                        employee.name,
                        ExpressionUtils.as(
                                calculateVacationDays(VacationType.ANNUAL),
                                "annualLeave"),
                        ExpressionUtils.as(
                                calculateVacationDays(VacationType.HALF),
                                "halfLeave"),
                        ExpressionUtils.as(
                                calculateVacationDays(VacationType.SICK),
                                "sickLeave"),
                        ExpressionUtils.as(
                                calculateVacationDays(VacationType.PUBLIC),
                                "publicLeave"),
                        ExpressionUtils.as(
                                calculateTotalUsedDays(),
                                "totalUsed"),
                        employee.leaveBalance.as("remainingDays")
                ))
                .from(employee)
                .leftJoin(employee.department, department)
                .orderBy(department.deptName.asc(), employee.position.desc())
                .fetch();
    }

    // 휴가 유형별 사용 일수 계산
    private JPQLQuery<Double> calculateVacationDays(VacationType type) {
        if (type == VacationType.HALF) {
            return JPAExpressions
                    .select(Expressions.numberTemplate(Double.class,
                            "CAST({0} * 0.5 AS DOUBLE)",
                            vacation.count()))
                    .from(vacation)
                    .where(
                            vacation.employee.eq(employee),
                            vacation.type.eq(type),
                            vacation.status.eq(VacationStatus.APPROVED)
                    );
        }

        return JPAExpressions
                .select(Expressions.numberTemplate(
                        Double.class,
                        "SUM(DATEDIFF({0}, {1}) + 1)",
                        vacation.endDate,
                        vacation.startDate
                ))
                .from(vacation)
                .where(
                        vacation.employee.eq(employee),
                        vacation.type.eq(type),
                        vacation.status.eq(VacationStatus.APPROVED)
                )
                .groupBy(employee.id);  // 그룹화 추가
    }

    // 총 사용 일수 계산
    private JPQLQuery<Double> calculateTotalUsedDays() {
        return JPAExpressions
                .select(
                        new CaseBuilder()
                                .when(vacation.type.eq(VacationType.HALF))
                                .then(0.5)
                                .otherwise(
                                        Expressions.numberTemplate(
                                                Double.class,
                                                "DATEDIFF({0}, {1}) + 1",
                                                vacation.endDate,
                                                vacation.startDate
                                        )
                                ).sum()
                )
                .from(vacation)
                .where(vacation.employee.eq(employee)
                        .and(vacation.status.eq(VacationStatus.APPROVED)));
    }
}
