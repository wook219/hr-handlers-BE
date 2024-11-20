package com.hr_handlers.vacation.controller;

import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.vacation.dto.ApprovedVacationResponse;
import com.hr_handlers.vacation.dto.PendingVacationResponse;
import com.hr_handlers.vacation.dto.VacationRequest;
import com.hr_handlers.vacation.dto.VacationResponse;
import com.hr_handlers.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacation")
@RequiredArgsConstructor
public class VacationApiController {

    private final VacationService vacationService;

    @GetMapping("/pending/{employeeId}")
    public SuccessResponse<List<PendingVacationResponse>> getPendingVacations(@PathVariable("employeeId") Long employeeId){
        return vacationService.getPendingVacations(employeeId);
    }

    @GetMapping("/approved/{employeeId}")
    public SuccessResponse<List<ApprovedVacationResponse>> getApprovedVacations(@PathVariable ("employeeId") Long employeeId){
        return vacationService.getApprovedVacations(employeeId);
    }

    @PostMapping
    public SuccessResponse<VacationResponse> enrollVacation(@RequestBody VacationRequest request){
        return vacationService.enrollVacation(request);
    }
}
