package com.hr_handlers.vacation.repository;

import com.hr_handlers.vacation.dto.ApprovedVacationResponse;
import com.hr_handlers.vacation.dto.PendingVacationResponse;
import com.hr_handlers.vacation.dto.VacationDetailResponse;
import com.hr_handlers.vacation.entity.Vacation;
import com.hr_handlers.vacation.entity.VacationStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hr_handlers.employee.entity.QEmployee.employee;
import static com.hr_handlers.vacation.entity.QVacation.vacation;

@Repository
public class VacationCustomRepositoryImpl implements VacationCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public VacationCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public VacationDetailResponse findVacationDetailById(Long id) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                VacationDetailResponse.class,
                                vacation.id,
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
    public List<PendingVacationResponse> findPendingVacations(Long employeeId) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                PendingVacationResponse.class,
                                vacation.docNum,
                                vacation.title,
                                vacation.updatedAt,
                                vacation.employee.id
                        )
                )
                .from(vacation)
                .where(
                        vacation.employee.id.eq(employeeId)
                            .and(vacation.status.eq(VacationStatus.PENDING))
                )
                .fetch();
    }

    @Override
    public List<ApprovedVacationResponse> findApprovedVacations(Long employeeId) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ApprovedVacationResponse.class,
                                vacation.docNum,
                                vacation.title,
                                vacation.updatedAt,
                                vacation.approvedAt,
                                vacation.approver,
                                vacation.employee.id
                        )
                )
                .from(vacation)
                .where(
                        vacation.employee.id.eq(employeeId)
                            .and(vacation.status.eq(VacationStatus.APPROVED))
                )
                .fetch();
    }

    @Override
    public Double findEmployeeVacationBalanceById(Long employeeId) {
        return jpaQueryFactory
                .select(employee.leaveBalance)
                .from(employee)
                .where(
                        employee.id.eq(employeeId)
                )
                .fetchOne();
    }
}
