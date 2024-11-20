package com.hr_handlers.vacation.repository;

import com.hr_handlers.vacation.entity.Vacation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface VacationCustomRepository {

    // 휴가 승인 대기 목록 조회
    List<Vacation> findPendingVacations(Long employeeId);

    // 휴가 승인 확정 목록 조회
    List<Vacation> findApprovedVacations(Long employeeId);
}
