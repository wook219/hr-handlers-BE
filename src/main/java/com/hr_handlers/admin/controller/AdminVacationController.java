package com.hr_handlers.admin.controller;

import com.hr_handlers.admin.dto.vacation.AdminVacationResponseDto;
import com.hr_handlers.admin.dto.vacation.AdminVacationStatusResponseDto;
import com.hr_handlers.admin.service.AdminVacationService;
import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.vacation.dto.VacationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/vacation")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminVacationController {

    private final AdminVacationService adminVacationService;

    @GetMapping
    public SuccessResponse<Page<AdminVacationResponseDto>> getPendingVacations(
            @RequestParam(defaultValue = "0", value = ("page")) int page,
            @RequestParam(defaultValue = "15", value = ("size")) int size
    ) {
        return adminVacationService.getPendingVacations(PageRequest.of(page, size));
    }

    @GetMapping("/status")
    public SuccessResponse<Page<AdminVacationStatusResponseDto>> getVacationStatus(
            @RequestParam(defaultValue = "0", value = ("page")) int page,
            @RequestParam(defaultValue = "15", value = ("size")) int size
    ) {
        return adminVacationService.getVacationStatus(PageRequest.of(page, size));
    }

    @PutMapping("/{id}/approve")
    public SuccessResponse<VacationResponseDto> approveVacation(
            @PathVariable("id") Long id,
            Authentication authentication
    ) {
        return adminVacationService.approveVacation(id, authentication.getName());
    }

    @PutMapping("/{id}/reject")
    public SuccessResponse<VacationResponseDto> rejectVacation(
            @PathVariable("id") Long id,
            Authentication authentication
    ) {
        return adminVacationService.rejectVacation(id, authentication.getName());
    }
}
