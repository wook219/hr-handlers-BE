package com.hr_handlers.vacation.repository;

import com.hr_handlers.vacation.entity.Vacation;
import com.hr_handlers.vacation.entity.VacationStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hr_handlers.vacation.entity.QVacation.vacation;

@Repository
public class VacationCustomRepositoryImpl implements VacationCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public VacationCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Vacation> findPendingVacations(Long employeeId) {
        return jpaQueryFactory
                .selectFrom(vacation)
                .where(
                        vacation.employee.id.eq(employeeId)
                            .and(vacation.status.eq(VacationStatus.PENDING))
                )
                .fetch();
    }

    @Override
    public List<Vacation> findApprovedVacations(Long employeeId) {
        return jpaQueryFactory
                .selectFrom(vacation)
                .where(
                        vacation.employee.id.eq(employeeId)
                            .and(vacation.status.eq(VacationStatus.APPROVED))
                )
                .fetch();
    }
}
