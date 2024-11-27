package com.hr_handlers.vacation.controller;

import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.vacation.dto.*;
import com.hr_handlers.vacation.service.VacationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacation")
@RequiredArgsConstructor
public class VacationController {

    private final VacationService vacationService;

    // 휴가 상세 조회
    @GetMapping("/{vacationId}")
    public SuccessResponse<VacationDetailResponseDto> getVacationDetail(@PathVariable("vacationId") Long id){
        return vacationService.getVacationDetail(id);
    }

    // 휴가 승인 대기 목록 조회
    @GetMapping("/pending/{employeeId}")
    public SuccessResponse<List<PendingVacationResponseDto>> getPendingVacations(@PathVariable("employeeId") Long employeeId){
        return vacationService.getPendingVacations(employeeId);
    }

    // 휴가 승인 확정 목록 조회
    @GetMapping("/approved/{employeeId}")
    public SuccessResponse<List<ApprovedVacationResponseDto>> getApprovedVacations(@PathVariable ("employeeId") Long employeeId){
        return vacationService.getApprovedVacations(employeeId);
    }

    // 휴가 등록
    @PostMapping
    public SuccessResponse<VacationResponseDto> enrollVacation(@RequestBody @Valid VacationRequestDto request){
        return vacationService.enrollVacation(request);
    }

    // 휴가 수정
    @PutMapping("/{vacationId}")
    public SuccessResponse<VacationResponseDto> modifyVacation(@PathVariable("vacationId") Long id,
                                                               @RequestBody VacationModifyRequestDto request){
        return vacationService.modifyVacation(id, request);
    }

    // 휴가 삭제
    @DeleteMapping("/{vacationId}")
    public SuccessResponse<VacationResponseDto> deleteVacation(@PathVariable("vacationId") Long id){
        return vacationService.deleteVacation(id);
    }

    // 잔여 휴가 조회
    @GetMapping("/balance/{employeeId}")
    public SuccessResponse<VacationBalanceResponseDto> getBalance(@PathVariable("employeeId") Long employeeId){
        return vacationService.getBalance(employeeId);
    }
}
