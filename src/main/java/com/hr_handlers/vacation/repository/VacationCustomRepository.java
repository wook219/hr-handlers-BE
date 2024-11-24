package com.hr_handlers.vacation.repository;

import com.hr_handlers.vacation.dto.ApprovedVacationResponse;
import com.hr_handlers.vacation.dto.PendingVacationResponse;
import com.hr_handlers.vacation.dto.VacationDetailResponse;
import com.hr_handlers.vacation.entity.Vacation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface VacationCustomRepository {

    //휴가 상세 조회
    VacationDetailResponse findVacationDetailById(Long id);

    // 휴가 승인 대기 목록 조회
    List<PendingVacationResponse> findPendingVacations(Long employeeId);

    // 휴가 승인 확정 목록 조회
    List<ApprovedVacationResponse> findApprovedVacations(Long employeeId);

    // 사원 잔여 휴가 일수 조회
    Double findEmployeeVacationBalanceById(Long employeeId);
}
