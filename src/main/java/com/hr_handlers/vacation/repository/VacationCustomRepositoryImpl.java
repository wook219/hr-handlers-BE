package com.hr_handlers.vacation.repository;

import com.hr_handlers.vacation.dto.ApprovedVacationResponseDto;
import com.hr_handlers.vacation.dto.PendingVacationResponseDto;
import com.hr_handlers.vacation.dto.VacationDetailResponseDto;
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
    public VacationDetailResponseDto findVacationDetailById(Long id) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                VacationDetailResponseDto.class,
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
    public List<PendingVacationResponseDto> findPendingVacations(Long employeeId) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                PendingVacationResponseDto.class,
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
    public List<ApprovedVacationResponseDto> findApprovedVacations(Long employeeId) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ApprovedVacationResponseDto.class,
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
