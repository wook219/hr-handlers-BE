package com.hr_handlers.vacation.repository;

import com.hr_handlers.vacation.dto.ApprovedVacationResponseDto;
import com.hr_handlers.vacation.dto.PendingVacationResponseDto;
import com.hr_handlers.vacation.dto.VacationDetailResponseDto;
import com.hr_handlers.vacation.dto.VacationSummaryResponseDto;
import com.hr_handlers.vacation.entity.VacationStatus;
import com.hr_handlers.vacation.entity.VacationType;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hr_handlers.employee.entity.QEmployee.employee;
import static com.hr_handlers.vacation.entity.QVacation.vacation;

@Repository
@Slf4j
public class VacationCustomRepositoryImpl implements VacationCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public VacationCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public VacationDetailResponseDto findVacationDetailById(Long id) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                VacationDetailResponseDto.class,
                                vacation.id,
                                vacation.docNum,
                                vacation.title,
                                vacation.type,
                                vacation.startDate,
                                vacation.endDate,
                                vacation.reason
                        )
                )
                .from(vacation)
                .where(
                        vacation.id.eq(id)
                )
                .fetchOne();
    }

    @Override
    public List<PendingVacationResponseDto> findPendingVacations(String empNo) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                PendingVacationResponseDto.class,
                                vacation.id,
                                vacation.docNum,
                                vacation.title,
                                vacation.type,
                                vacation.updatedAt,
                                vacation.employee.id
                        )
                )
                .from(vacation)
                .where(
                        vacation.employee.empNo.eq(empNo)
                            .and(vacation.status.eq(VacationStatus.PENDING))
                )
                .fetch();
    }

    @Override
    public List<ApprovedVacationResponseDto> findApprovedVacations(String empNo) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ApprovedVacationResponseDto.class,
                                vacation.docNum,
                                vacation.title,
                                vacation.updatedAt,
                                vacation.approvedAt,
                                vacation.status,
                                vacation.approver,
                                vacation.employee.id
                        )
                )
                .from(vacation)
                .where(
                        vacation.employee.empNo.eq(empNo)
                            .and(vacation.status.eq(VacationStatus.APPROVED)
                                    .or(vacation.status.eq(VacationStatus.REJECTED)))
                )
                .fetch();
    }


    @Override
    public VacationSummaryResponseDto findEmployeeVacationBalanceById(String empNo) {
        VacationStatus approvedStatus = VacationStatus.APPROVED;
        VacationStatus pendingStatus = VacationStatus.PENDING;
        VacationType halfType = VacationType.HALF;
        VacationType publicType = VacationType.PUBLIC;

        log.info("Query parameters - empNo: {}, half type: {}, public type: {}",
                empNo, halfType, publicType);

        NumberTemplate<Double> dateDiff = Expressions.numberTemplate(
                Double.class,
                "DATEDIFF({0}, {1})",
                vacation.endDate,
                vacation.startDate
        );

        NumberExpression<Double> vacationDaysExpression = new CaseBuilder()
                .when(vacation.type.eq(VacationType.HALF))
                .then(Expressions.numberTemplate(Double.class, "0.5"))
                .when(vacation.type.eq(VacationType.PUBLIC))
                .then(Expressions.numberTemplate(Double.class, "0.0"))
                .otherwise(dateDiff.add(1.0));

        SubQueryExpression<Double> approvedSum = JPAExpressions
                .select(vacationDaysExpression.sum())
                .from(vacation)
                .where(vacation.employee.empNo.eq(empNo)
                        .and(vacation.status.eq(approvedStatus)));

        SubQueryExpression<Double> pendingSum = JPAExpressions
                .select(vacationDaysExpression.sum())
                .from(vacation)
                .where(vacation.employee.empNo.eq(empNo)
                        .and(vacation.status.eq(pendingStatus)));

        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                VacationSummaryResponseDto.class,
                                employee.leaveBalance,
                                Expressions.numberTemplate(Double.class, "COALESCE({0}, 0.0)", approvedSum),
                                Expressions.numberTemplate(Double.class, "COALESCE({0}, 0.0)", pendingSum)
                        )
                )
                .from(employee)
                .where(employee.empNo.eq(empNo))
                .fetchOne();
    }
}
