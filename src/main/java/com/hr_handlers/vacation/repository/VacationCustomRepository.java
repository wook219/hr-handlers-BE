package com.hr_handlers.vacation.repository;

import com.hr_handlers.vacation.dto.ApprovedVacationResponseDto;
import com.hr_handlers.vacation.dto.PendingVacationResponseDto;
import com.hr_handlers.vacation.dto.VacationDetailResponseDto;

import java.util.List;

public interface VacationCustomRepository {

    //휴가 상세 조회
    VacationDetailResponseDto findVacationDetailById(Long id);

    // 휴가 승인 대기 목록 조회
    List<PendingVacationResponseDto> findPendingVacations(Long employeeId);

    // 휴가 승인 확정 목록 조회
    List<ApprovedVacationResponseDto> findApprovedVacations(Long employeeId);

    // 사원 잔여 휴가 일수 조회
    Double findEmployeeVacationBalanceById(Long employeeId);
}
