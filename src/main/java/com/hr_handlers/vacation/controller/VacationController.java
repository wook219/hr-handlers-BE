package com.hr_handlers.vacation.controller;

import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.vacation.dto.*;
import com.hr_handlers.vacation.service.VacationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
    @GetMapping("/pending")
    public SuccessResponse<List<PendingVacationResponseDto>> getPendingVacations(Authentication authentication){
        return vacationService.getPendingVacations(authentication.getName());
    }

    // 휴가 승인 확정 목록 조회
    @GetMapping("/approved")
    public SuccessResponse<List<ApprovedVacationResponseDto>> getApprovedVacations(Authentication authentication){
        return vacationService.getApprovedVacations(authentication.getName());
    }

    // 휴가 등록
    @PostMapping
    public SuccessResponse<VacationResponseDto> enrollVacation(
            @RequestBody @Valid VacationRequestDto request,
            Authentication authentication
    ){
        return vacationService.enrollVacation(request, authentication.getName());
    }

    // 휴가 수정
    @PutMapping("/{vacationId}")
    public SuccessResponse<VacationResponseDto> modifyVacation(
            @PathVariable("vacationId") Long id,
            @RequestBody VacationRequestDto request
    ){
        return vacationService.modifyVacation(id, request);
    }

    // 휴가 삭제
    @DeleteMapping("/{vacationId}")
    public SuccessResponse<Boolean> deleteVacation(@PathVariable("vacationId") Long id){
        return vacationService.deleteVacation(id);
    }

    // 휴가 일수 정보 조회
    @GetMapping("/summary")
    public SuccessResponse<VacationSummaryResponseDto> getBalance(Authentication authentication){
        return vacationService.getBalance(authentication.getName());
    }
}
